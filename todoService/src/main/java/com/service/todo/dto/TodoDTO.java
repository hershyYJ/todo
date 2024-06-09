package com.service.todo.dto;

import com.service.todo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

    private String id;

    private String title;

    private String content;

    private Boolean done;

    private int priority;

    private LocalDateTime deadline;

}
