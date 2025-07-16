package com.spring.techpractica.service.Technology;

import com.spring.techpractica.mengmentData.TechnologyManagementData;
import com.spring.techpractica.model.entity.techSkills.Technology;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {

    private final TechnologyManagementData technologyManagementData;

    public TechnologyService(TechnologyManagementData technologyManagementData) {
        this.technologyManagementData = technologyManagementData;
    }


    public List<Technology> getAllTechnologies() {
        return technologyManagementData.getAllTechnologies();
    }


}
