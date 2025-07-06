package com.study.taskAPI.dto;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
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
    private String title;
    private String description;
    private LocalDate dueDate;
    private Status status;
    private Priority priority;

}
