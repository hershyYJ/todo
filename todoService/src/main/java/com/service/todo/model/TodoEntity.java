package com.service.todo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Todo")
public class TodoEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String todoId;

    private String title;

    private String content;

    private Boolean done;

    private int priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

}
