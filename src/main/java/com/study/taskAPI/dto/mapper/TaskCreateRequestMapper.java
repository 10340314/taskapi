package com.study.taskAPI.dto.mapper;

import com.study.taskAPI.dto.TaskCreateRequest;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateRequestMapper extends Mapper<Task, TaskCreateRequest> {

    @Override
    protected Task mapToEntity(TaskCreateRequest taskCreateRequest) {
        return new Task(
                null,
                taskCreateRequest.getTitle(),
                taskCreateRequest.getDescription(),
                taskCreateRequest.getDueDate(),
                taskCreateRequest.getPriority(),
                taskCreateRequest.getStatus() != null ? taskCreateRequest.getStatus() : Status.TO_DO
        );
    }

    @Override
    protected TaskCreateRequest mapToDTO(Task o) {
        throw new UnsupportedOperationException("DTO de criação não é retornado para o cliente.");
    }
}
