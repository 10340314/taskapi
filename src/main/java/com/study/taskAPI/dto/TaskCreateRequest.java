package com.study.taskAPI.dto;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskCreateRequest {
    @NotBlank(message = "Títlo não pode estar vazio")
    @Size(min = 1, max = 25, message = "O título deve ter entre 1 e 25 caracteres")
    private String title;

    @NotBlank(message = "Descrição não pode estar vazio")
    @Size(min = 1, max = 50, message = "A descrição deve ter entre 1 e 50 caracteres")
    private String description;

    @NotNull(message = "Data limite não pode estar vazio")
    @FutureOrPresent(message = "A data limite deve ser válida")
    private LocalDate dueDate;

    @NotNull(message = "Prioridade não pode estar vazio")
    private Priority priority;

    private Status status;
}
