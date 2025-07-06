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
    }
