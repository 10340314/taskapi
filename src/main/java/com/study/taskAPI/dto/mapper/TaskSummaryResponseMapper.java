package com.study.taskAPI.dto.mapper;

import com.study.taskAPI.dto.TaskSummaryResponse;
import com.study.taskAPI.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskSummaryResponseMapper extends Mapper<Task, TaskSummaryResponse> {

    @Override
    protected TaskSummaryResponse mapToDTO(Task task) {
        return new TaskSummaryResponse(
                task.getId(),
                task.getTitle(),
                task.getPriority(),
                task.getStatus()
        );
    }

    @Override
    protected Task mapToEntity(TaskSummaryResponse taskSummaryResponse) {
        throw new UnsupportedOperationException("Não deve mapear para entidade.");
    }
}