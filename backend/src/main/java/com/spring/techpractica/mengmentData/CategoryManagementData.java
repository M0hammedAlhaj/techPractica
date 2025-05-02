package com.spring.techpractica.mengmentData;

import com.spring.techpractica.dto.techSkills.CategoryResponse;
import com.spring.techpractica.exception.ResourcesNotFoundException;
import com.spring.techpractica.factory.CategoryFactory;
import com.spring.techpractica.maper.CategoryMapper;
import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.repository.techSkills.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryManagementData {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findById(name)
                .orElseThrow(()
                        -> new ResourcesNotFoundException("categorise no concentrate"));
    }

    public void save(String category) {
        getCategoryByName(category);
        categoryRepository.save(CategoryFactory.createCategory(category));
    }
}
