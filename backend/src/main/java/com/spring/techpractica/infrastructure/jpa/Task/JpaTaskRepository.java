package com.spring.techpractica.infrastructure.jpa.Task;

import com.spring.techpractica.core.task.TaskRepository;
import com.spring.techpractica.core.task.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class JpaTaskRepository implements TaskRepository {

    private final JpaTask jpaTask;

    @Override
    public Task save(Task task) {
        return jpaTask.save(task);
    }

    @Override
    public Task update(Task task) {
        return null;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jpaTask.findById(id);
    }
}