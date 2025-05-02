package com.spring.techpractica.service.session.createSession;

import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.maper.TechnologyMapper;
import com.spring.techpractica.mengmentData.TechnologyManagementData;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.techSkills.Technology;
import com.spring.techpractica.service.techSkills.Technology.TechnologyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SessionTechnologyLinker {


   private final TechnologyMapper technologyMapper;

    public void linkTechnologiesToSession(Session session, List<String> techNames) {
        List<Technology> technologies = technologyMapper.technologiesStringToTechnologyList(techNames);

        session.setSessionTechnologies(technologies);
    }
}
