package com.service.todo.service;

import com.service.todo.dto.TodoDTO;
import com.service.todo.dto.TodoModificationReq;
import com.service.todo.model.TodoEntity;
import com.service.todo.model.UserEntity;
import com.service.todo.persistence.TodoRepository;
import com.service.todo.persistence.UserRepository;
import com.service.todo.security.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public TodoEntity createTodo(String accessToken, TodoDTO todoDTO) {
        String userId = tokenProvider.validateAndGetUserId(accessToken);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        TodoEntity todo = TodoEntity.builder()
                .user(user)
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .done(todoDTO.getDone())
                .priority(todoDTO.getPriority())
                .deadline(todoDTO.getDeadline())
                .build();

        todoRepository.save(todo);

        log.info("todo: {}", todo);

        return todo;
    }

    @Transactional
    public String modifyTodo(String accessToken, TodoModificationReq request) {
        log.info("todoId: {}", request.getTodoId());

        String userId = tokenProvider.validateAndGetUserId(accessToken);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        TodoEntity todo = todoRepository.findByTodoId(request.getTodoId())
                .orElseThrow(() -> new RuntimeException("Not found Todo"));

        int priority = request.getPriority();
        Integer intPriority = priority;

        if(user.getId() != todo.getUser().getId()) {
            throw new RuntimeException("You can only modify todo authored by yourself.");
        } else {
            if(request.getTitle() != null && !request.getTitle().isEmpty()) {
                todo.setTitle(request.getTitle());
            }
            if(request.getContent() != null && !request.getContent().isEmpty()) {
                todo.setContent(request.getContent());
            }
            if(intPriority != null){
                todo.setPriority(request.getPriority());
            }
            if(request.getDone() != null) {
                todo.setDone(request.getDone());
            }
            if(request.getDeadline() != null) {
                todo.setDeadline(request.getDeadline());
            }

            todoRepository.save(todo);
        }

        return "Modification Success";
    }

    @Transactional
    public String deleteTodo(String accessToken, String todoId) {
        String userId = tokenProvider.validateAndGetUserId(accessToken);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        TodoEntity todo = todoRepository.findByTodoId(todoId)
                .orElseThrow(() -> new RuntimeException("Not found Todo"));
        if(user.getId() != todo.getUser().getId()) {
            throw new RuntimeException("You can only modify todo authored by yourself.");
        } else {
            todoRepository.deleteById(todoId);
        }

        return "Deletion Success";
    }

    @Transactional
    public List<TodoEntity> getAllTodos(String accessToken) {
        String userId = tokenProvider.validateAndGetUserId(accessToken);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        return todoRepository.findByUser(user);
    }

    @Transactional
    public Set<TodoEntity> filterByPriority(String accessToken) {
        String userId = tokenProvider.validateAndGetUserId(accessToken);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        return todoRepository.findByUserOrderByPriorityAsc(user);
    }

    @Transactional
    public List<TodoEntity> filterByDeadline(String accessToken) {
        String userId = tokenProvider.validateAndGetUserId(accessToken);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Not found User"));

        return todoRepository.findByUserOrderByDeadlineAsc(user);
    }


}