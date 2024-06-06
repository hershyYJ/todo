package com.service.todo.persistence;

import com.service.todo.model.TodoEntity;
import com.service.todo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserIdOrderByDeadlineAsc(String userId);

    List<TodoEntity> findByUser(UserEntity user);

    List<TodoEntity> findByUserIdOrderByPriorityAsc(String userId);

}
