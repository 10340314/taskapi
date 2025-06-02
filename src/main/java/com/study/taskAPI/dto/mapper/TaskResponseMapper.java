package com.study.taskAPI.dto.mapper;

import com.study.taskAPI.dto.TaskResponse;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskResponseMapper extends Mapper<Task, TaskResponse> {

    @Override
    protected TaskResponse mapToDTO(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getPriority(),
                task.getStatus()
        );
    }

    @Override
    protected Task mapToEntity(TaskResponse taskResponse) {
        return new Task(
                taskResponse.getId(),
                taskResponse.getTitle(),
                taskResponse.getDescription(),
                taskResponse.getDueDate(),
                taskResponse.getPriority(),
                taskResponse.getStatus()
        );
    }
}
