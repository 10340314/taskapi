package com.study.taskAPI.dto;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class TaskResponse {
    private Integer id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Priority priority;
    private Status status;
}
