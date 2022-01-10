package com.example.testpractice.repository;

import com.example.testpractice.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    TodoEntity findById(Integer Id);
}
