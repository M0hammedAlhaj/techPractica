package com.spring.techpractica.service.techSkills.Category;

import com.spring.techpractica.dto.techSkills.CategoryResponse;
import com.spring.techpractica.maper.CategoryMapper;
import com.spring.techpractica.mengmentData.CategoryManagementData;
import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.repository.techSkills.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {


    private final CategoryManagementData categoryManagementData;



    public List<CategoryResponse> findAllCategories() {
        return CategoryMapper.categoryToCategoryResponseList(categoryManagementData.getAllCategories());
    }


    public Category getCategoryByName(String name)       {

        return categoryManagementData.getCategoryByName(name);
    }

    public void addCategory(Category category) {
        categoryManagementData.save(category);    }
}
