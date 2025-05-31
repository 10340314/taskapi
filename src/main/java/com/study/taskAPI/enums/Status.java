package com.study.taskAPI.enums;

import lombok.Getter;

@Getter
public enum Status {
    IN_PROGRESS("Em andamento"),
    DONE("Concluído"),
    TO_DO("A fazer"),
    CANCELLED("Cancelado");

    private final String label;

    Status(String label) {
        this.label = label;
    }
}
