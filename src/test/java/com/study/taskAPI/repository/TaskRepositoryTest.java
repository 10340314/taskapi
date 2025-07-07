package com.study.taskAPI.repository;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static com.study.taskAPI.utils.TaskSpecification.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository repository;

    @Test
    public void shouldReturnTasks_whenTitlePriorityAndStatusAreFiltered() {
        Task task1 = new Task(null, "Título teste 1", "Descrição teste 1", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task2 = new Task(null, "Título teste 2", "Descrição teste 2", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task3 = new Task(null, "Título teste 3", "Descrição teste 3", LocalDate.of(2025,7,6), Priority.LOW, Status.TO_DO);

        repository.save(task1);
        repository.save(task2);
        repository.save(task3);

        Specification<Task> spec = Specification.where(withTitleLike("Título")
                                                .and(withPriority(Priority.LOW))
                                                .and(withStatus(Status.DONE)));

        List<Task> results = repository.findAll(spec);

        assertThat(results).hasSize(2);

        assertThat(results)
                .extracting("title", "priority", "status")
                .containsExactlyInAnyOrder(
                        tuple("Título teste 1", Priority.LOW, Status.DONE),
                        tuple("Título teste 2", Priority.LOW, Status.DONE)
                );
    }

    @Test
    void shouldReturnEmptyList_whenTitlePriorityAndStatusDoNotMatchAnyTask() {
        Task task1 = new Task(null, "Título teste 1", "Descrição teste 1", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task2 = new Task(null, "Título teste 2", "Descrição teste 2", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);

        repository.save(task1);
        repository.save(task2);

        Specification<Task> spec = Specification.where(withTitleLike("task"))
                                                .and(withPriority(Priority.LOW))
                                                .and(withStatus(Status.DONE));

        List<Task> results = repository.findAll(spec);

        assertThat(results).isEmpty();
    }

    @Test
    void shouldReturnAllTasks_whenFilterIsNotPresent() {
        Task task1 = new Task(null, "Título teste 1", "Descrição teste 1", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task2 = new Task(null, "Título teste 2", "Descrição teste 2", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);

        repository.save(task1);
        repository.save(task2);

        Specification<Task> spec = Specification.where(withTitleLike(null))
                                                .and(withPriority(null))
                                                .and(withStatus(null))
                                                .and(withDueDateBefore(null))
                                                .and(withDueDateAfter(null));

        List<Task> results = repository.findAll(spec);

        assertThat(results).hasSize(2);
    }

    @Test
    void shouldReturnTasks_whenNotAllFiltersArePresent() {
        Task task1 = new Task(null, "Título teste 1", "Descrição teste 1", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task2 = new Task(null, "Título teste 2", "Descrição teste 2", LocalDate.of(2025,7,9), Priority.LOW, Status.DONE);
        Task task3 = new Task(null, "Título teste 3", "Descrição teste 3", LocalDate.of(2025,7,6), Priority.LOW, Status.TO_DO);

        repository.save(task1);
        repository.save(task2);
        repository.save(task3);

        Specification<Task> spec = Specification.where(withTitleLike("Título"))
                                                .and(withPriority(Priority.LOW))
                                                .and(withStatus(null))
                                                .and(withDueDateBefore(LocalDate.of(2025,7,8)))
                                                .and(withDueDateAfter(null));

        List<Task> results = repository.findAll(spec);

        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting("title", "priority", "dueDate")
                .containsExactlyInAnyOrder(
                        tuple("Título teste 1", Priority.LOW, LocalDate.of(2025, 7, 6)),
                        tuple("Título teste 3", Priority.LOW, LocalDate.of(2025, 7, 6))
                );
    }

    @Test
    void shouldReturnTasks_whenDueDateBeforeAndAfterAreFiltered() {
        Task task1 = new Task(null, "Título teste 1", "Descrição teste 1", LocalDate.of(2025,7,6), Priority.LOW, Status.DONE);
        Task task2 = new Task(null, "Título teste 2", "Descrição teste 2", LocalDate.of(2025,7,7), Priority.LOW, Status.DONE);
        Task task3 = new Task(null, "Título teste 3", "Descrição teste 3", LocalDate.of(2025,7,8), Priority.LOW, Status.TO_DO);

        repository.save(task1);
        repository.save(task2);
        repository.save(task3);

        LocalDate date1 = LocalDate.of(2025, 7, 6);
        LocalDate date2 = LocalDate.of(2025, 7, 7);


        Specification<Task> spec = Specification.where(withDueDateBetween(date1, date2));

        List<Task> results = repository.findAll(spec);

        assertThat(results).hasSize(2);
        assertThat(results).extracting("dueDate")
                .containsExactlyInAnyOrder(
                        LocalDate.of(2025, 7, 6),
                        LocalDate.of(2025, 7, 7)
                );
    }
}