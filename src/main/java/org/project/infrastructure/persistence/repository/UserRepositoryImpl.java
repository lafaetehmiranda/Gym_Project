package org.project.infrastructure.persistence.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.project.domain.model.User;
import org.project.domain.repository.UserRepository;
import org.project.infrastructure.persistence.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepositoryImpl implements PanacheRepositoryBase<UserEntity, UUID>, UserRepository {

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity(user.getId(), user.getUserType(), user.getName(), user.getEmail(),
                user.getPassword());
        persist(entity);
        return toDomain(entity);
    }

    @Override
    public Optional<User> findDomainById(UUID id) {
        UserEntity entity = find("id", id).firstResult();
        return entity != null ? Optional.of(toDomain(entity)) : Optional.empty();
    }

    @Override
    public List<User> findAllDomain() {
        return streamAll()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        UserEntity entity = find("email", email).firstResult();
        return entity != null ? Optional.of(toDomain(entity)) : Optional.empty();
    }

    @Override
    public boolean deleteById(UUID id) {
        return delete("id = ?1", id) > 0;
    }

    @Override
    public boolean existsById(UUID id) {
        return count("id", id) > 0;
    }

    private User toDomain(UserEntity entity) {
        return new User(entity.getId(), entity.getUserType(), entity.getName(), entity.getEmail(),
                entity.getPassword());
    }
}
