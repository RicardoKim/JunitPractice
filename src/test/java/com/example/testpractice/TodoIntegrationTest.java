package com.example.testpractice.controller;

import com.example.testpractice.model.TodoEntity;
import com.example.testpractice.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoRepository todoRepository;

    private String TEST_URL = "/todo";

    @BeforeEach
    public void reset() throws Exception{
        IntStream.range(0, 20).forEach(i -> {
            TodoEntity dummyEntity = TodoEntity.builder()//
                    .id(i)
                    .Todo("Todo" + i)
                    .build();
            todoRepository.save(dummyEntity);
        });
    }

    @Test
    @Transactional
    @DisplayName("Post API Test")
    public void PostTest() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("todo", "Hello World");
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post(TEST_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("todo").value(request.get("todo")));
    }

    @Test
    @Transactional
    @DisplayName("Get API Test")
    public void GetTest() throws Exception{
        List<TodoEntity> dataList = todoRepository.findAll();
        Integer targetIndex = dataList.get(0).getId();
        mvc.perform(get(TEST_URL + "/" +targetIndex))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("todo").exists());

    }

    @Test
    @Transactional
    @DisplayName("Get API Test with Wrong ID")
    public void GetTestWithWrongID() throws Exception{
        List<TodoEntity> dataList = todoRepository.findAll();
        Integer targetIndex = dataList.get(0).getId();
        mvc.perform(get(TEST_URL + "/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("Put API Test")
    public void PutTest() throws Exception{
        Map<String, Object> request = new HashMap<>();
        List<TodoEntity> dataList = todoRepository.findAll();
        Integer targetIndex = dataList.get(0).getId();
        request.put("id", targetIndex);
        request.put("todo", "Hello World");
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("todo").value(request.get("todo")));
    }

    @Test
    @Transactional
    @DisplayName("Put API Test with Wrong ID")
    public void PutTestWithWrongID() throws Exception{
        Map<String, Object> request = new HashMap<>();
        request.put("id", 0);
        request.put("todo", "Hello World");
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @DisplayName("Delete API Test")
    public void DeleteTest() throws Exception{
        Map<String, Object> request = new HashMap<>();
        List<TodoEntity> dataList = todoRepository.findAll();
        Integer targetIndex = dataList.get(0).getId();
        request.put("id", targetIndex);
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(delete(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isOk());


        TodoEntity deletedEntity = todoRepository.findById(targetIndex);
        if(deletedEntity != null){
            fail();
        }
    }

    @Test
    @Transactional
    @DisplayName("Delete API Test with Wrong ID")
    public void DeleteTestWithWrongID() throws Exception{
        Map<String, Object> request = new HashMap<>();
        request.put("id", 0);
        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(delete(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(json))
                .andExpect(status().isBadRequest());
    }
}