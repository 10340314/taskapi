package com.study.taskAPI.controller;

import com.study.taskAPI.dto.*;
import com.study.taskAPI.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping("/")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getTaskById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<TaskSummaryResponse>> getFilteredTaskSummaries(@Valid @ModelAttribute TaskFilterRequest taskFilter,
                                                                              @PageableDefault(size = 10, page = 0, sort = "dueDate", direction = Sort.Direction.ASC) Pageable page) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getFilteredTaskSummaries(taskFilter, page));
    }

    @PostMapping("/")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest task) {
        TaskResponse createdTask = service.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Integer id, @RequestBody @Valid TaskUpdateRequest task) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateTask(id, task));
    }
}