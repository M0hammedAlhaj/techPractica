package com.spring.techpractica.service.category;

import com.spring.techpractica.mengmentData.CategoryManagementData;
import com.spring.techpractica.model.entity.techSkills.Category;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {


    private final CategoryManagementData categoryManagementData;

    public CategoryService(CategoryManagementData categoryManagementData) {


        this.categoryManagementData = categoryManagementData;


    }

    public List<Category> getAllCategories() {
        return categoryManagementData.getAllCategories();
    }

}
