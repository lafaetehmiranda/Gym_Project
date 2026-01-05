package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.CategoryDTO;
import org.project.domain.model.Category;

/**
 * Mapper for converting between Category domain model and DTO.
 */
@ApplicationScoped
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getValue(),
                category.getCategory(),
                category.isActive());
    }
}
