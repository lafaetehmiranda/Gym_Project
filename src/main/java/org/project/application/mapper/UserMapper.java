package org.project.application.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.project.application.dto.UserDTO;
import org.project.domain.model.User;

@ApplicationScoped
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUserType());
    }
}
