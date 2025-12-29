package org.project.domain.model;

import org.project.domain.enums.UserType;

import java.util.UUID;

public class User {

    private final UUID id;
    private final UserType userType;
    private final String name;
    private final String email;
    private final String password;

    public User(UUID id, UserType userType, String name, String email, String password) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User create(UserType userType, String name, String email, String password) {
        return new User(UUID.randomUUID(), userType, name, email, password);
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

    public String getPassword() {
        return password;
    }
}
