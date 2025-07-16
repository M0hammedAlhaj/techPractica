package com.spring.techpractica.maper;

import com.spring.techpractica.mengmentData.CategoryManagementData;
import com.spring.techpractica.model.entity.techSkills.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryMapper {

    private final CategoryManagementData categoryManagementData;

    public Category createCategoryFrom(String category) {
        return categoryManagementData.getCategoryByCategoryName(category);
    }

    public List<Category> createCategoryListFrom(List<String> categoryList) {
        return categoryList.stream()
                .map(this::createCategoryFrom)
                .toList();
    }
}
