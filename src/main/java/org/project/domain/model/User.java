package org.project.domain.model;

import org.project.domain.enums.UserType;

import java.util.UUID;

public class User {

    private final UUID id;
    private final UserType userType;

    public User(UUID id, UserType userType) {
        this.id = id;
        this.userType = userType;
    }

    public static User create(UserType userType) {
        return new User(UUID.randomUUID(), userType);
    }

    public UUID getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }
}
