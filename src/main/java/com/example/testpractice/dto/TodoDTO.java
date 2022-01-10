package com.example.testpractice.dto;

import com.example.testpractice.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

    private Integer id;

    private String Todo;

    public TodoEntity toEntity() {
        TodoEntity todoEntity;
        if(id == null && Todo != null){
            todoEntity = TodoEntity.builder().Todo(this.Todo).build();

        }
        else if(id != null && Todo == null){
            todoEntity = TodoEntity.builder().id(this.id).build();
        }
        else{
            todoEntity = TodoEntity.builder().id(this.id).Todo(this.Todo).build();
        }
        return todoEntity;
    }
}