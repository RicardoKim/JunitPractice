package com.example.testpractice.service;

import com.example.testpractice.dto.TodoDTO;
import com.example.testpractice.model.TodoEntity;
import com.example.testpractice.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public void save(TodoEntity entity){
        todoRepository.save(entity);
    }

    public TodoDTO find(int id) {
        TodoEntity entity = todoRepository.findById(id);
        if(entity == null){
            throw new RuntimeException("Wrong id");
        }
        return TodoDTO.builder().id(entity.getId()).Todo(entity.getTodo()).build();
    }

    public void fix(TodoEntity entity) {
        TodoEntity tempentity = todoRepository.findById(entity.getId());
        if (tempentity != null){
            todoRepository.save(entity);
        }
        else{
            throw new RuntimeException("Wrong id");
        }
    }

    public void delete(TodoEntity entity) {
        TodoEntity tempentity = todoRepository.findById(entity.getId());
        if (tempentity != null){
            todoRepository.delete(entity);
        }
        else{
            throw new RuntimeException("Wrong id");
        }
    }
}
