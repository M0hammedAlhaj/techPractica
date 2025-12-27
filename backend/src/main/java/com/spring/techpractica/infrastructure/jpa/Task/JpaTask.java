package com.spring.techpractica.infrastructure.jpa.Task;

import com.spring.techpractica.core.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaTask extends JpaRepository<Task, UUID> {

}
