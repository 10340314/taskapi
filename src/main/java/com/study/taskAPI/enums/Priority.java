package com.study.taskAPI.enums;

import lombok.Getter;

@Getter
public enum Priority {
    LOW("Baixa"),
    MEDIUM("Média"),
    HIGH("Alta");

    private final String label;

    Priority(String label) {
        this.label = label;
    }
}
