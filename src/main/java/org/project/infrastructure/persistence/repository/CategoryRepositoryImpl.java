package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.model.Category;
import org.project.domain.repository.CategoryRepository;
import org.project.infrastructure.persistence.entity.CategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CategoryRepositoryImpl implements PanacheRepositoryBase<CategoryEntity, UUID>, CategoryRepository {

    @Override
    public Category save(Category category) {
        CategoryEntity entity = new CategoryEntity(
                category.getId(),
                category.getName(),
                category.getValue(),
                category.getCategory(),
                category.isActive());
        persist(entity);
        return toDomain(entity);
    }

    @Override
    public Optional<Category> findDomainById(UUID id) {
        CategoryEntity entity = findById(id);
        return entity != null ? Optional.of(toDomain(entity)) : Optional.empty();
    }

    @Override
    public List<Category> findAllDomain() {
        return listAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> findAllActive() {
        return list("active", true).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Category toDomain(CategoryEntity entity) {
        return new Category(
                entity.getId(),
                entity.getName(),
                entity.getValue(),
                entity.getCategory(),
                entity.isActive());
    }
}
