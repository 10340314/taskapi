package com.study.taskAPI.service;

import com.study.taskAPI.dto.TaskCreateRequest;
import com.study.taskAPI.dto.TaskResponse;
import com.study.taskAPI.dto.TaskSummaryResponse;
import com.study.taskAPI.dto.mapper.TaskCreateRequestMapper;
import com.study.taskAPI.dto.mapper.TaskResponseMapper;
import com.study.taskAPI.dto.mapper.TaskSummaryResponseMapper;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import com.study.taskAPI.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;
    @Autowired
    private TaskResponseMapper taskResponseMapper;
    @Autowired
    private TaskCreateRequestMapper taskCreateRequestMapper;
    @Autowired
    private TaskSummaryResponseMapper taskSummaryResponseMapper;
    public List<TaskResponse> getAllTasks() {
        return taskResponseMapper.toDTOList(repository.findAll());
    }

    public TaskResponse createTask(TaskCreateRequest request) {
        Task task = taskCreateRequestMapper.toEntity(request);
        return taskResponseMapper.toDTO(repository.save(task));
    }

    public List<TaskSummaryResponse> getTaskSummaries() {
        return taskSummaryResponseMapper.toDTOList(repository.findAll());
    }

    public List<TaskSummaryResponse> getTaskSummariesByStatus(Status status) {
        return taskSummaryResponseMapper.toDTOList(repository.findByStatus(status));
    }
}