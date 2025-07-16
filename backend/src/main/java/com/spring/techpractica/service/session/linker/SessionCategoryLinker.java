package com.spring.techpractica.service.session.linker;

import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.mengmentData.CategoryManagementData;
import com.spring.techpractica.model.entity.Requirement;
import com.spring.techpractica.model.entity.Session;
import com.spring.techpractica.model.entity.techSkills.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class SessionCategoryLinker {

    private final CategoryManagementData categoryManagementData;

    public void linkCategoriesToSession(Session session, List<String> categoryNames) {
        categoryNames.forEach(name -> {
            Category category = categoryManagementData.getCategoryByCategoryName(name);
            session.getSessionCategories()
                    .add(category);
            Requirement requirement = Requirement.builder()
                    .category(category)
                    .session(session)
                    .build();
            session.getSessionRequirements().add(requirement);
        });
    }

}
