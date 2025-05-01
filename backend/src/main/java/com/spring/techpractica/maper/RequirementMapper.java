package com.spring.techpractica.maper;

import com.spring.techpractica.dto.session.SessionRequest;
import com.spring.techpractica.factory.RequirementFactory;
import com.spring.techpractica.mengmentData.FieldManagementData;
import com.spring.techpractica.model.entity.Requirement;
import com.spring.techpractica.model.entity.Session;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RequirementMapper {

    private final FieldManagementData fieldManagementData;

    public List<Requirement> fieldToRequirement (Session session, SessionRequest request){

        return request.getFields().stream()
                .map(field -> RequirementFactory.createRequirement(
                        session,
                        fieldManagementData.getFieldByFieldName(field)
                ))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
