package org.project.domain.repository;

import org.project.domain.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findDomainById(UUID id);

    List<Category> findAllDomain();

    List<Category> findAllActive();
}
