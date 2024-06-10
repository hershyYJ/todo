package com.service.todo.alert;

import com.service.todo.dto.TodoAlertDTO;
import com.service.todo.model.TodoEntity;
import com.service.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AlertService {

    private final TodoRepository todoRepository;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public AlertService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void checkTodos() {
        List<TodoAlertDTO> todoAlertDTOs = findTodosDueInNextHour();
        if (!todoAlertDTOs.isEmpty()) {
            log.info("Found {} todos due in the next hour", todoAlertDTOs.size());

            synchronized (emitters) {
                for (SseEmitter emitter : emitters) {
                    try {
                        emitter.send(SseEmitter.event().name("알림").data(todoAlertDTOs));
                    } catch (IOException e) {
                        log.error("Error sending SSE event", e);
                        emitter.completeWithError(e);
                        emitters.remove(emitter);
                    }
                }
            }
        }
    }

    private List<TodoAlertDTO> findTodosDueInNextHour() {
        LocalDateTime oneHourStart = LocalDateTime.now().plusMinutes(59);
        LocalDateTime oneHourEnd = LocalDateTime.now().plusHours(1);

        log.info("Checking todos between {} and {}", oneHourStart, oneHourEnd);
        List<TodoEntity> todos = todoRepository.findTodosDueInNextHour(oneHourStart, oneHourEnd);

        return todos.stream().map(todo ->
                        TodoAlertDTO.builder()
                                .id(todo.getId())
                                .title(todo.getTitle())
                                .content(todo.getContent())
                                .username(todo.getUser().getUsername())
                                .build())
                .collect(Collectors.toList());
    }

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter();

        synchronized (emitters) {
            emitters.add(emitter);
        }
        emitter.onCompletion(() -> {
            synchronized (emitters) {
                emitters.remove(emitter);
            }
            log.info("Emitter completed");
        });
        emitter.onTimeout(() -> {
            synchronized (emitters) {
                emitters.remove(emitter);
            }
            log.info("Emitter timed out");
        });
        emitter.onError((e) -> {
            synchronized (emitters) {
                emitters.remove(emitter);
            }
            log.error("Emitter error", e);
        });

        return emitter;
    }
}