package com.spring.techpractica.factory;

import com.spring.techpractica.model.entity.techSkills.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryFactory {

    public CategoryFactory() {}

    public static Category createCategory(String categoryName) {
        return Category.builder()
                .categoryName(categoryName)
                .build();
    }
}
