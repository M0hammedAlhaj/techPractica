package com.spring.techpractica.service.techSkills.Technology;

import com.spring.techpractica.mengmentData.TechnologyManagementData;
import com.spring.techpractica.model.entity.techSkills.Technology;
import com.spring.techpractica.repository.techSkills.TechnologyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TechnologyService {

    private final TechnologyManagementData technologyManagementData;

    public List<Technology> getAllTechnologies() {
        return technologyManagementData.getAllTechnologies();
    }


    public void addTechnology(Technology technology) { technologyManagementData.save(technology); }
}
