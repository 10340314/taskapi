package com.study.taskAPI.service;

import com.study.taskAPI.dto.TaskSummaryResponse;
import com.study.taskAPI.dto.mapper.TaskSummaryResponseMapper;
import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import com.study.taskAPI.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskService service;

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskSummaryResponseMapper summaryMapper;

    @Test
    void shouldReturnListOfTaskSummaries() {
        Task task1 = new Task(1, "Título teste 1", "Descrição", LocalDate.now(), Priority.MEDIUM, Status.TO_DO);
        Task task2 = new Task(2, "Título teste 2", "Descrição", LocalDate.now(), Priority.MEDIUM, Status.TO_DO);
        List<Task> taskList = List.of(task1, task2);
        TaskSummaryResponse summary1 = new TaskSummaryResponse(1, "Título teste 1", Priority.MEDIUM, Status.TO_DO);
        TaskSummaryResponse summary2 = new TaskSummaryResponse(2, "Título teste 2", Priority.MEDIUM, Status.TO_DO);

        when(repository.findAll()).thenReturn(taskList);
        when(summaryMapper.toDTOList(taskList)).thenReturn(List.of(summary1, summary2));

        List<TaskSummaryResponse> result = service.getTaskSummaries();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Título teste 1");
        assertThat(result.get(1).getTitle()).isEqualTo("Título teste 2");
    }

    @Test
    void shouldReturnTasksSummariesWithStatusTO_DO() {
        Task task1 = new Task(1, "Título 1", "Descrição 1", LocalDate.now(), Priority.LOW, Status.TO_DO);
        Task task2 = new Task(2, "Título 2", "Descrição 2", LocalDate.now(), Priority.LOW, Status.TO_DO);
        List<Task> taskList = List.of(task1, task2);

        TaskSummaryResponse summary1 = new TaskSummaryResponse(1, "Título 1", Priority.LOW, Status.TO_DO);
        TaskSummaryResponse summary2 = new TaskSummaryResponse(2, "Título 2", Priority.LOW, Status.TO_DO);

        when(repository.findByStatus(Status.TO_DO)).thenReturn(taskList);
        when(summaryMapper.toDTOList(taskList)).thenReturn(List.of(summary1, summary2));

        List<TaskSummaryResponse> result = service.getTaskSummariesByStatus(Status.TO_DO);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStatus()).isEqualTo(Status.TO_DO);
        assertThat(result.get(1).getStatus()).isEqualTo(Status.TO_DO);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksMatchGivenStatus() {
        when(repository.findByStatus(Status.TO_DO)).thenReturn(List.of());
        when(summaryMapper.toDTOList(List.of())).thenReturn(List.of());

        List<TaskSummaryResponse> result = service.getTaskSummariesByStatus(Status.TO_DO);

        assertThat(result).isEmpty();
    }

}
