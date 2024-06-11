package com.service.todo.controller;

import com.service.todo.dto.ResponseDTO;
import com.service.todo.dto.TodoDTO;
import com.service.todo.dto.TodoModificationReq;
import com.service.todo.model.TodoEntity;
import com.service.todo.security.JwtAuthenticationFilter;
import com.service.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "Todo 생성 API")
    public ResponseEntity<TodoEntity> createTodo(HttpServletRequest request, @RequestBody TodoDTO todoDTO) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

        return ResponseEntity.ok(todoService.createTodo(accessToken, todoDTO));
    }

    @PatchMapping
    @Operation(summary = "Todo 수정 API")
    public ResponseEntity<String> modifyTodo(HttpServletRequest request, @RequestBody TodoModificationReq todo) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

        return ResponseEntity.ok(todoService.modifyTodo(accessToken, todo));
    }

    @GetMapping
    @Operation(summary = "Todo 조회 API")
    public ResponseEntity<List<TodoEntity>> getAllTodos(HttpServletRequest request) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

        return ResponseEntity.ok(todoService.getAllTodos(accessToken));
    }

    @DeleteMapping
    @Operation(summary = "Todo 삭제 API")
    public ResponseEntity<String> deleteTodo(HttpServletRequest request, @RequestParam String todoId) {
        log.info("todoId: {}", todoId);
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

        return ResponseEntity.ok(todoService.deleteTodo(accessToken, todoId));
    }

    @GetMapping("/priority")
    @Operation(summary = "Todo 우선순위 정렬 API")
    public ResponseEntity<Set<TodoEntity>> filterByPriority(HttpServletRequest request) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);
        return ResponseEntity.ok(todoService.filterByPriority(accessToken));
    }

    @GetMapping("/deadline")
    @Operation(summary = "Todo 마감 기한 정렬 API")
    public ResponseEntity<List<TodoEntity>> filterByDeadline(HttpServletRequest request) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);
        return ResponseEntity.ok(todoService.filterByDeadline(accessToken));
    }

}