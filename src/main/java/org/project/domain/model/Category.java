package org.project.domain.model;

import java.util.UUID;

public class Category {

    private final UUID id;
    private final String name;
    private final String value;
    private final String category;
    private final boolean active;

    public Category(UUID id, String name, String value, String category, boolean active) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.category = category;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public boolean isActive() {
        return active;
    }
}
