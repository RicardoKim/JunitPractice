package com.example.testpractice.controller;

import com.example.testpractice.dto.TodoDTO;
import com.example.testpractice.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
public class ControllerSliceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void PostTest() throws Exception {
        //given
        TodoDTO todoDTO = TodoDTO.builder().id(1).Todo("Todo").build();
        given(todoService.find(1)).willReturn(todoDTO);

        //when
        ResultActions actions = mvc.perform(get("http://localhost:8080/todo/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("todo").value("Todo"));
    }
}
