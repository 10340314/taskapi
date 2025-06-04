package com.study.taskAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.taskAPI.dto.TaskCreateRequest;
import com.study.taskAPI.dto.TaskResponse;
import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService service;

    @DisplayName("Deve criar uma task e retornar o DTO de resposta com status 201")
    @Test
    void shouldCreateTaskAndReturnResponse() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest(
                "Título", "Descrição", LocalDate.of(2025, 6, 4), Priority.LOW, null);

        TaskResponse response = new TaskResponse(
                1, "Título", "Descrição", LocalDate.of(2025, 6, 4), Priority.LOW, Status.TO_DO);

        when(service.createTask(any(TaskCreateRequest.class))).thenReturn(response);

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Título"))
                .andExpect(jsonPath("$.status").value("TO_DO"))
                .andExpect(jsonPath("$.priority").value("LOW"));
    }

    @DisplayName("Deve tentar criar uma task e retornar status 400")
    @Test
    void shouldNotCreateTaskAndReturnBadRequest() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest(
                null, "Descrição", LocalDate.of(2025,6,4), Priority.LOW, Status.TO_DO
        );

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
}