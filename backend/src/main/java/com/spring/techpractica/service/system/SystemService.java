package com.spring.techpractica.service.system;

import com.spring.techpractica.dto.SystemResponse;
import com.spring.techpractica.maper.SystemMapper;
import com.spring.techpractica.mengmentData.SystemManagementData;
import com.spring.techpractica.model.entity.techSkills.System;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SystemService {


    private final SystemManagementData systemManagementData;


    public List<SystemResponse> findAllSystems() {
        return SystemMapper.
                systemsToSystemResponseList(systemManagementData.getAllSystems());
    }


    public System getSystemByName(String name) {

        return systemManagementData.getSystemByName(name);
    }

}
