package com.example.testpractice;

import com.example.testpractice.dto.TodoDTO;
import com.example.testpractice.model.TodoEntity;
import com.example.testpractice.repository.TodoRepository;
import com.example.testpractice.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class MockitoTest {
    @Mock
    TodoService todoService;

    @Test
    public void mockitoStubbingTest(){
        TodoRepository todoRepository = mock(TodoRepository.class);
        TodoService todoService = mock(TodoService.class);
        Mockito.when(todoService.find(1)).thenReturn(TodoDTO.builder().id(1).Todo("HelloWorld").build());
        Mockito.when(todoRepository.findById(1)).thenReturn(TodoEntity.builder().id(1).Todo("HelloWorld").build());

        InOrder inOrder = inOrder(todoService, todoRepository);
        inOrder.verify(todoRepository).findById(1);
        inOrder.verify(todoService).find(1);

    }
}
