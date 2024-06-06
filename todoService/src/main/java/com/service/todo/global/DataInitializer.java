package com.service.todo.global;

import com.service.todo.config.WebSecurityConfig;
import com.service.todo.model.TodoEntity;
import com.service.todo.model.UserEntity;
import com.service.todo.persistence.TodoRepository;
import com.service.todo.persistence.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final WebSecurityConfig webSecurityConfig;


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserEntity user1 = userRepository.findByEmail("test1@naver.com")
                .orElseGet(() -> userRepository.save(UserEntity.joinUser(
                        "test1@naver.com",
                        webSecurityConfig.passwordEncoder().encode("test1234!"),
                        "test1")));

        UserEntity user2 = userRepository.findByEmail("test2@naver.com")
                .orElseGet(() -> userRepository.save(UserEntity.joinUser(
                        "test2@naver.com",
                        webSecurityConfig.passwordEncoder().encode("test1234!"),
                        "test2")));

        TodoEntity todo1 = TodoEntity.builder()
                .title("정보처리기사 실기")
                .content("수제비 p.35-70")
                .done(false)
                .priority(1)
                .createdAt(LocalDateTime.of(2024, 6, 8, 13, 00, 00))
                .deadline(LocalDateTime.of(2024, 6, 8, 16, 00, 00))
                .user(user1)
                .build();

        TodoEntity todo2 = TodoEntity.builder()
                .title("토익 단어")
                .content("해커스 토익 보카 p.20-50")
                .done(false)
                .priority(2)
                .createdAt(LocalDateTime.of(2024, 6, 8, 17, 00, 00))
                .deadline(LocalDateTime.of(2024, 6, 8, 19, 00, 00))
                .user(user1)
                .build();


        TodoEntity todo3 = TodoEntity.builder()
                .title("기말고사")
                .content("데이터통신 9-12장")
                .done(false)
                .priority(3)
                .createdAt(LocalDateTime.of(2024, 6, 8, 19, 00, 00))
                .deadline(LocalDateTime.of(2024, 6, 10, 12, 00, 00))
                .user(user1)
                .build();


        TodoEntity todo4 = TodoEntity.builder()
                .title("정보처리기사 실기")
                .content("수제비 p.90-130")
                .done(false)
                .priority(1)
                .createdAt(LocalDateTime.of(2024, 6, 8, 13, 00, 00))
                .deadline(LocalDateTime.of(2024, 6, 8, 16, 00, 00))
                .user(user2)
                .build();


        TodoEntity todo5 = TodoEntity.builder()
                .title("기말고사")
                .content("데이터통신 8-10장")
                .done(false)
                .priority(2)
                .createdAt(LocalDateTime.of(2024, 6, 8, 17, 00, 00))
                .deadline(LocalDateTime.of(2024, 6, 8, 19, 00, 00))
                .user(user2)
                .build();

        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3, todo4, todo5));


    }
}