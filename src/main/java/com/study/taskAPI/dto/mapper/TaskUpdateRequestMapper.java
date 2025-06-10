package com.study.taskAPI.dto.mapper;

import com.study.taskAPI.dto.TaskUpdateRequest;
import com.study.taskAPI.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdateRequestMapper extends Mapper<Task, TaskUpdateRequest> {
    @Override
    protected TaskUpdateRequest mapToDTO(Task task) {
        throw new UnsupportedOperationException("DTO de atualização não é retornado para o cliente.");
    }

    @Override
    protected Task mapToEntity(TaskUpdateRequest taskUpdateRequest) {
        return new Task(
                null,
                taskUpdateRequest.getTitle(),
                taskUpdateRequest.getDescription(),
                taskUpdateRequest.getDueDate(),
                taskUpdateRequest.getPriority(),
                taskUpdateRequest.getStatus()
        );
    }
}
