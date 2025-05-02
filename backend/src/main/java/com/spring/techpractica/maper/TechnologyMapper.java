package com.spring.techpractica.maper;

import com.spring.techpractica.mengmentData.TechnologyManagementData;
import com.spring.techpractica.model.entity.techSkills.Technology;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TechnologyMapper {

    private final TechnologyManagementData technologyManagementData;

    public static List<String> technologiesToListString(List<Technology> technologies) {

        return technologies.stream().map((Technology::getTechnologyName))
                .toList();
    }

    public List<Technology> technologiesStringToTechnologyList(List<String> technologies) {
        return technologies.stream()
                .map(technologyManagementData::getTechnologyByName)
                .collect(Collectors.toList());
    }
}
