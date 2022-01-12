package com.example.testpractice.repository;

import com.example.testpractice.dto.TodoDTO;
import com.example.testpractice.model.TodoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    void save(){
        //given
        TodoEntity dummyEntity = TodoEntity.builder().Todo("Hello World").build();

        TodoEntity savedEntity = todoRepository.save(dummyEntity);
        System.out.println(savedEntity);
        assertNotNull(savedEntity.getId());
        assertEquals(savedEntity.getTodo(), dummyEntity.getTodo());
    }
}