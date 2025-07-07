package com.study.taskAPI.dto;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
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
public class TaskFilterRequest {
    @Size(min = 2, max = 30)
    private String title;
    private String description;
    private LocalDate dueDateBefore;
    private LocalDate dueDateAfter;
    private Status status;
    private Priority priority;
}
