package com.study.taskAPI.utils;

import com.study.taskAPI.enums.Priority;
import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TaskSpecification {
    public static Specification<Task> withTitleLike(String title) {
        return (root, query, builder) ->
            (title == null || title.isBlank() ?
            builder.conjunction() :
            builder.like(builder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
    }
    public static Specification<Task> withStatus(Status status) {
        return (root, query, builder) ->
            (status == null ?
            builder.conjunction() :
            builder.equal(root.get("status"), status));
    }

    public static Specification<Task> withPriority(Priority priority) {
        return (root, query, builder) ->
            (priority == null ?
            builder.conjunction() :
            builder.equal(root.get("priority"), priority));
    }

    public static Specification<Task> withDueDateBefore(LocalDate dueDateBefore) {
        return (root, query, builder) ->
            (dueDateBefore == null ?
            builder.conjunction() :
            builder.lessThan(root.get("dueDate"), dueDateBefore));
    }

    public static Specification<Task> withDueDateAfter(LocalDate dueDateAfter) {
        return (root, query, builder) ->
            (dueDateAfter == null ?
            builder.conjunction() :
            builder.greaterThan(root.get("dueDate"), dueDateAfter));
    }

    public static Specification<Task> withDueDateBetween(LocalDate dueDateAfter, LocalDate dueDateBefore) {
        return (root, query, builder) ->
            {
                if (dueDateBefore == null && dueDateAfter == null) return builder.conjunction();
                if (dueDateAfter == null) return builder.lessThanOrEqualTo(root.get("dueDate"), dueDateBefore);
                if (dueDateBefore == null) return builder.greaterThanOrEqualTo(root.get("dueDate"), dueDateAfter);
                return builder.between(root.get("dueDate"), dueDateAfter, dueDateBefore);
            };
    }
}