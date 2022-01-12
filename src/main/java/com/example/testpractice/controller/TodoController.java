package com.example.testpractice.controller;

import com.example.testpractice.dto.TodoDTO;
import com.example.testpractice.model.TodoEntity;
import com.example.testpractice.service.TodoService;
import com.sun.xml.bind.v2.TODO;
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
        TodoEntity returnEntity = todoService.save(entity);
        TodoDTO returnDTO = TodoDTO.builder().id(returnEntity.getId()).Todo(returnEntity.getTodo()).build();
        return ResponseEntity.ok().body(returnDTO);
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
            return ResponseEntity.badRequest().body("Wrong Id");
        }
        return ResponseEntity.ok().body(dto.getId().toString() + "is Deleted");
    }
}
