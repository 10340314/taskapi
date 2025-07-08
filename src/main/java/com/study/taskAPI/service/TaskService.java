package com.study.taskAPI.service;

import com.study.taskAPI.dto.*;
import com.study.taskAPI.dto.mapper.TaskCreateRequestMapper;
import com.study.taskAPI.dto.mapper.TaskResponseMapper;
import com.study.taskAPI.dto.mapper.TaskSummaryResponseMapper;
import com.study.taskAPI.dto.mapper.TaskUpdateRequestMapper;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import com.study.taskAPI.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.study.taskAPI.utils.TaskSpecification.*;

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
    @Autowired
    private TaskUpdateRequestMapper taskUpdateRequestMapper;

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

    public TaskResponse updateTask(Integer id, TaskUpdateRequest request) {
        Task existingTask = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa de id %d não encontrada.".formatted(id)));
        Task updateTask = taskUpdateRequestMapper.toEntity(request);
        updateTask.setId(id);
        return taskResponseMapper.toDTO(repository.save(updateTask));
    }

    public TaskResponse getTaskById(int id) {
        Task task = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa de id %d não encontrada".formatted(id)));
        return taskResponseMapper.toDTO(task);
    }

    public Page<TaskSummaryResponse> getFilteredTaskSummaries(TaskFilterRequest taskFilter, Pageable pageable) {
        Specification<Task> spec = Specification.where(withTitleLike(taskFilter.getTitle()))
                                                .and(withPriority(taskFilter.getPriority()))
                                                .and(withStatus(taskFilter.getStatus()))
                                                .and(withDueDateBetween(taskFilter.getDueDateBefore(), taskFilter.getDueDateAfter()));
//                                                .and(withDueDateBefore(taskFilter.getDueDateBefore()))
//                                                .and(withDueDateAfter(taskFilter.getDueDateAfter()));
        Page<Task> tasks = repository.findAll(spec, pageable);
        return tasks.map(taskSummaryResponseMapper::toDTO);
    }
}