package com.spring.techpractica.controller;

import com.spring.techpractica.dto.SystemResponse;
import com.spring.techpractica.model.entity.techSkills.Category;
import com.spring.techpractica.model.entity.techSkills.Technology;
import com.spring.techpractica.service.Technology.TechnologyService;
import com.spring.techpractica.service.category.CategoryService;
import com.spring.techpractica.service.system.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tech-skills")
@Tag(
        name = "TechSkills Controller",
        description = "Handles The Skills and categories operations including getting all the  tech ," +
                " fields and technologies "
)
public class TechSkillsController {


    private final SystemService systemService;
    private final TechnologyService technologyService;
    private final CategoryService categoryService;

    public TechSkillsController(SystemService systemService, TechnologyService technologyService, CategoryService categoryService) {
        this.systemService = systemService;
        this.technologyService = technologyService;
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Retrieve systems",
            description = "Fetches a list of all available systems that can be used to classify sessions."
    )
    @GetMapping("/systems")
    public ResponseEntity<List<SystemResponse>> getSystems() {
        return ResponseEntity.ok(systemService.findAllSystems());
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
            summary = "Retrieve All categories",
            description = "Fetches a list of all categories related to the session context, useful for filtering or tagging."
    )
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
