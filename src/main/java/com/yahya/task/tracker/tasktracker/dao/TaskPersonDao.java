package com.yahya.task.tracker.tasktracker.dao;

import com.yahya.task.tracker.tasktracker.model.TaskPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskPersonDao extends JpaRepository<TaskPerson, Integer> {
}
