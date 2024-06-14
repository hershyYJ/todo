package com.service.todo.persistence;

import com.service.todo.model.TodoEntity;
import com.service.todo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity>findByUser(UserEntity user);

    Set<TodoEntity> findByUserOrderByPriorityAsc(UserEntity user);

    List<TodoEntity> findByUserOrderByDeadlineAsc(UserEntity user);

    Optional<TodoEntity> findByTodoId(String todoId);

    @Query("SELECT t FROM TodoEntity t JOIN FETCH t.user WHERE t.deadline BETWEEN :now AND :oneHourLater")
    List<TodoEntity> findTodosDueInNextHour(LocalDateTime now, LocalDateTime oneHourLater);

}
