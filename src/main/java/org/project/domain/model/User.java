package org.project.domain.model;

import org.project.domain.enums.UserType;

import java.util.UUID;

/**
 * Domain model representing a user in the system.
 */
public class User {

    private final UUID id;
    private final UserType userType;
    private final String name;
    private final String email;
    private final String password;
    private final String phoneNumber;

    public User(UUID id, UserType userType, String name, String email, String password, String phoneNumber) {
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static User create(UserType userType, String name, String email, String password, String phoneNumber) {
        return new User(UUID.randomUUID(), userType, name, email, password, phoneNumber);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
