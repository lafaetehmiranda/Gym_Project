package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.UserDTO;
import org.project.domain.model.User;

/**
 * Mapper for converting between User domain model and DTO.
 */
@ApplicationScoped
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUserType(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber());
    }
}
