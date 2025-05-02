package com.spring.techpractica.mengmentData;

import com.spring.techpractica.dto.techSkills.TechnologyTransfer;
import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.model.entity.techSkills.Technology;
import com.spring.techpractica.repository.techSkills.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologyManagementData {

    private final TechnologyRepository technologyRepository;

    public TechnologyManagementData(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }


    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    public Optional<Technology> findTechnologyByName(String technologyName) {
        return technologyRepository.findById(technologyName);
    }

    public Technology getTechnologyByName(String technologyName) {
        return technologyRepository.findById(technologyName).
                orElseThrow(() -> new ResourcesNotFoundException("Technology not found"));
    }

    public List<Technology> getTechnologiesByTechnologiesName(List<String> technologiesName) {
        return technologyRepository.findAllById(technologiesName);
    }

    public void save(Technology technology) {
        technologyRepository.save(technology);
    }
}
