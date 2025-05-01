package com.spring.techpractica.controller;

import com.spring.techpractica.dto.techSkills.CategoryResponse;
import com.spring.techpractica.dto.techSkills.FieldTransfer;
import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.model.entity.techSkills.Field;
import com.spring.techpractica.model.entity.techSkills.Technology;
import com.spring.techpractica.service.techSkills.Category.CategoryService;
import com.spring.techpractica.service.techSkills.Field.FieldService;
import com.spring.techpractica.service.techSkills.Technology.TechnologyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tech-skills")
@Tag(
        name = "TechSkills Controller",
        description = "Handles The Skills and categories operations including getting all the  tech , fields and technologies "
)
public class TechSkillsController {


    private final CategoryService categoryService;
    private final TechnologyService technologyService;
    private final FieldService fieldService;

    public TechSkillsController(CategoryService categoryService, TechnologyService technologyService, FieldService fieldService) {
        this.categoryService = categoryService;
        this.technologyService = technologyService;
        this.fieldService = fieldService;
    }

    @Operation(
            summary = "Retrieve All Categories",
            description = "Fetches a list of all available categories that can be used to classify sessions."
    )
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @Operation(
            summary = "Retrieve All Technologies",
            description = "Fetches a list of all available technologies that can be associated with sessions."
    )
    @GetMapping("/technologies")
    public ResponseEntity<List<Technology>> getTechnologies() {
        return ResponseEntity.ok(technologyService.getAllTechnologies());
    }

    @Operation(
            summary = "Retrieve All Fields",
            description = "Fetches a list of all fields related to the session context, useful for filtering or tagging."
    )
    @GetMapping("/fields")
    public ResponseEntity<List<Field>> getFields() {
        return ResponseEntity.ok(fieldService.getAllFields());
    }


    @Operation(
            summary = "Add a new category",
            description = "Creates a new category based on the provided Category object in the request body."
    )
    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok("Category added successfully");
    }
}
