package com.study.taskAPI.controller;

import com.study.taskAPI.dto.TaskCreateRequest;
import com.study.taskAPI.dto.TaskResponse;
import com.study.taskAPI.dto.TaskSummaryResponse;
import com.study.taskAPI.dto.TaskUpdateRequest;
import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<TaskSummaryResponse>> getFilteredTaskSummaries(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) LocalDate dueDateBefore,
            @RequestParam(required = false) LocalDate dueDateAfter) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getFilteredTaskSummaries(title, priority, status, dueDateBefore, dueDateAfter));
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