package com.dpm.dto.owner;

/**
 * @author danielpm.dev
 */
public class OwnerDTO {
    private Long id;
    private String username;

    // Default constructor
    public OwnerDTO() {}

    // Parameterized constructor
    public OwnerDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
