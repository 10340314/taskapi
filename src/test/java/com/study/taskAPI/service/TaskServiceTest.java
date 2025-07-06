package com.study.taskAPI.service;

import com.study.taskAPI.dto.*;
import com.study.taskAPI.dto.mapper.TaskCreateRequestMapper;
import com.study.taskAPI.dto.mapper.TaskResponseMapper;
import com.study.taskAPI.dto.mapper.TaskSummaryResponseMapper;
import com.study.taskAPI.dto.mapper.TaskUpdateRequestMapper;
import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import com.study.taskAPI.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskService service;

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskSummaryResponseMapper summaryMapper;

    @Mock
    private TaskCreateRequestMapper createRequestMapper;

    @Mock
    private TaskResponseMapper responseMapper;

    @Mock
    private TaskUpdateRequestMapper updateRequestMapper;

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

    @Test
    void shouldCreateTaskAndReturnResponse() {
        TaskCreateRequest request = new TaskCreateRequest("Título", "Descrição", LocalDate.now(), Priority.LOW, null);
        Task entity = new Task(null, "Título", "Descrição", LocalDate.now(), Priority.LOW, Status.TO_DO);
        Task saved = new Task(1, "Título", "Descrição", LocalDate.now(), Priority.LOW, Status.TO_DO);
        TaskResponse response = new TaskResponse(1, "Título", "Descrição", LocalDate.now(), Priority.LOW, Status.TO_DO);

        when(createRequestMapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(responseMapper.toDTO(saved)).thenReturn(response);

        TaskResponse result = service.createTask(request);

        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getTitle()).isEqualTo("Título");
        assertThat(result.getDescription()).isEqualTo("Descrição");
        assertThat(result.getPriority()).isEqualTo(Priority.LOW);
        assertThat(result.getStatus()).isEqualTo(Status.TO_DO);
    }

    @Test
    void shouldUpdateTaskAndReturnResponse() {
        int id = 1;
        TaskUpdateRequest request = new TaskUpdateRequest("Título novo", "Descrição", LocalDate.of(2025, 6, 9), Priority.LOW, Status.TO_DO);
        Task entity = new Task(null, "Título novo", "Descrição", LocalDate.of(2025, 6, 9), Priority.LOW, Status.TO_DO);
        Task existingEntity = new Task(id, "Título", "Descrição", LocalDate.of(2025, 6, 9), Priority.LOW, Status.TO_DO);
        Task saved = new Task(id, "Título novo", "Descrição", LocalDate.of(2025, 6, 9), Priority.LOW, Status.TO_DO);
        TaskResponse response = new TaskResponse(id, "Título novo", "Descrição", LocalDate.of(2025, 6, 9), Priority.LOW, Status.TO_DO);

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(updateRequestMapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(responseMapper.toDTO(saved)).thenReturn(response);

        TaskResponse result = service.updateTask(id, request);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getTitle()).isEqualTo("Título novo");
        assertThat(result.getDescription()).isEqualTo("Descrição");
        assertThat(result.getDueDate()).isEqualTo(LocalDate.of(2025, 6, 9));
        assertThat(result.getPriority()).isEqualTo(Priority.LOW);
        assertThat(result.getStatus()).isEqualTo(Status.TO_DO);
    }

    @Test
    void shouldNotUpdateTaskAndReturnNotFound() {
        int id = 1;
        TaskUpdateRequest request = new TaskUpdateRequest("Título novo", "Descrição", LocalDate.of(2025, 6, 9), Priority.LOW, Status.TO_DO);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.updateTask(id, request));
    }

    @Test
    void shouldReturnTaskWithSpecifiedID() {
        int id = 1;
        Task task = new Task(id, "Título", "Descrição", LocalDate.of(2025, 6, 26), Priority.LOW, Status.TO_DO);
        TaskResponse taskResponse = new TaskResponse(id, "Título", "Descrição", LocalDate.of(2025, 6, 26), Priority.LOW, Status.TO_DO);

        when(repository.findById(id)).thenReturn(Optional.of(task));
        when(responseMapper.toDTO(task)).thenReturn(taskResponse);

        TaskResponse result = service.getTaskById(id);

        assertThat(result.getId()).isEqualTo(taskResponse.getId());
        assertThat(result.getTitle()).isEqualTo(taskResponse.getTitle());
        assertThat(result.getDescription()).isEqualTo(taskResponse.getDescription());
        assertThat(result.getDueDate()).isEqualTo(taskResponse.getDueDate());
        assertThat(result.getPriority()).isEqualTo(taskResponse.getPriority());
        assertThat(result.getStatus()).isEqualTo(taskResponse.getStatus());
    }

    @Test
    void shouldNotFindTaskAndReturnNotFound() {
        int id = 1;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getTaskById(id));
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.getTaskById(id));
        assertThat(ex.getReason()).contains("Tarefa de id %d não encontrada".formatted(id));
    }

    @Test
    void shouldReturnTasks_whenTitlePriorityAndStatusAreFiltered() {
        Task task1 = new Task(1, "Título teste 1", "Descrição teste 1", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task2 = new Task(2, "Título teste 2", "Descrição teste 2", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        TaskSummaryResponse taskSummary1 = new TaskSummaryResponse(1, "Título teste 1", Priority.LOW, Status.DONE);
        TaskSummaryResponse taskSummary2 = new TaskSummaryResponse(2, "Título teste 2", Priority.LOW, Status.DONE);

        List<TaskSummaryResponse> summaryList = List.of(taskSummary1, taskSummary2);

        when(repository.findAll(any(Specification.class))).thenReturn(List.of(task1, task2));
        when(summaryMapper.toDTOList(List.of(task1, task2))).thenReturn(summaryList);

        List<TaskSummaryResponse> result = service.getFilteredTaskSummaries("Título", Priority.LOW, Status.DONE, null, null);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).getTitle()).contains("Título");
        assertThat(result.get(0).getPriority()).isEqualTo(Priority.LOW);
        assertThat(result.get(0).getStatus()).isEqualTo(Status.DONE);

        assertThat(result.get(1).getTitle()).contains("Título");
        assertThat(result.get(1).getPriority()).isEqualTo(Priority.LOW);
        assertThat(result.get(1).getStatus()).isEqualTo(Status.DONE);
    }
}