package com.spring.techpractica.repository;


import com.spring.techpractica.model.entity.techSkills.System;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemRepository extends JpaRepository<System, String> {

    List<System> getCategoriesBySystemName(String categoryName);
}
