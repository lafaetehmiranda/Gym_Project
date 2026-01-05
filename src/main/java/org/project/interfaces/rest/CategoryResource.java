package org.project.interfaces.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.project.application.dto.CategoryDTO;
import org.project.application.mapper.CategoryMapper;
import org.project.domain.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST resource for training categories.
 */
@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Categories", description = "Training categories and specialties")
public class CategoryResource {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Inject
    public CategoryResource(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @GET
    @Operation(summary = "List all training categories", description = "Returns a list of all active training categories.")
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAllActive().stream()
                .map(categoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
