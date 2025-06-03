package com.study.taskAPI.dto;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskSummaryResponse {
    private Integer id;
    private String title;
    private Priority priority;
    private Status status;
}