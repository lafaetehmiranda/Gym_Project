package org.project.domain.repository;

import org.project.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findDomainById(UUID id);

    List<User> findAllDomain();

    boolean deleteById(UUID id);

    boolean existsById(UUID id);
}
