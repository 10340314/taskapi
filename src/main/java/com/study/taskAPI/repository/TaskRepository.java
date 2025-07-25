package com.study.taskAPI.repository;

import com.study.taskAPI.enums.Status;
import com.study.taskAPI.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task>, PagingAndSortingRepository<Task, Integer> {
    List<Task> findByStatus(Status status);
}