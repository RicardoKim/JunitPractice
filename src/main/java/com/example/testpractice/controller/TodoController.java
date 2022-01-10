package com.example.testpractice.controller;

import com.example.testpractice.dto.TodoDTO;
import com.example.testpractice.model.TodoEntity;
import com.example.testpractice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TodoDTO dto){
        TodoEntity entity = dto.toEntity();
        todoService.save(entity);
        return ResponseEntity.ok().body("succeed");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") int id){
        try{
            TodoDTO dto = todoService.find(id);
            return ResponseEntity.ok().body(dto);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Wrong Id");
        }


    }

    @PutMapping()
    public ResponseEntity<?> fix(@RequestBody TodoDTO dto){
        TodoEntity entity = dto.toEntity();
        try{
            todoService.fix(entity);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Wrong Id");
        }
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@RequestBody TodoDTO dto){
        TodoEntity entity = dto.toEntity();
        try{
            todoService.delete(entity);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(dto.getId().toString() + "is Deleted");
        }
        return ResponseEntity.ok().body(dto);
    }
}
