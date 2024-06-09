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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "Todo 생성 API")
    public ResponseEntity<ResponseDTO> createTodo(HttpServletRequest request, @RequestBody TodoDTO todoDTO) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);
        ResponseDTO<TodoDTO> responseDTO = ResponseDTO.<TodoDTO>builder()
                .data(todoService.createTodo(accessToken, todoDTO))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping
    @Operation(summary = "Todo 수정 API")
    public ResponseEntity<String> modifyTodo(HttpServletRequest request, @RequestBody TodoModificationReq todo) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);

        return ResponseEntity.ok(todoService.modifyTodo(accessToken, todo));
    }

    @GetMapping
    @Operation(summary = "Todo 조회 API")
    public ResponseEntity<ResponseDTO> getAllTodos(HttpServletRequest request) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);
        ResponseDTO<List<TodoDTO>> responseDTO = ResponseDTO.<List<TodoDTO>>builder()
                .data(todoService.getAllTodos(accessToken))
                .build();
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping
    @Operation(summary = "Todo 삭제 API")
    public ResponseEntity<ResponseDTO> deleteTodo(HttpServletRequest request, @RequestParam String todoId) {
        String accessToken = jwtAuthenticationFilter.parseBearerToken(request);
        ResponseDTO responseDTO = ResponseDTO.builder().data(todoService.deleteTodo(accessToken, todoId)).build();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/priority")
    @Operation(summary = "Todo 우선순위 정렬 API")
    public ResponseEntity<List<TodoEntity>> filterByPriority(HttpServletRequest request) {
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