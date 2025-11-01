package com.spring.techpractica.core.task;

import com.spring.techpractica.core.shared.BaseRepository;
import com.spring.techpractica.core.task.entity.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends BaseRepository<Task> {
}