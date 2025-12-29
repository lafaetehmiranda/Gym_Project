package org.project.domain.model;

import org.project.domain.enums.UserType;

import java.util.UUID;

public class User {

    private final UUID id;
    private final UserType userType;
    private final String name;
    private final String email;

    public User(UUID id, UserType userType, String name, String email) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.email = email;
    }

    public static User create(UserType userType, String name, String email) {
        return new User(UUID.randomUUID(), userType, name, email);
    }

    public UUID getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
