package com.dpm.dto.owner;

import com.dpm.entities.Owner;

/**
 * @author danielpm.dev
 */
public class FullOwnerDTO {
    private Long id;
    private String username;
    private String password;
    private String email;

    // Constructor vacío para deserialización
    public FullOwnerDTO() {}

    // Constructor para mapear desde la entidad Owner
    public FullOwnerDTO(Owner owner) {
        this.id = owner.getId();
        this.username = owner.getUsername();
        this.password = owner.getPassword(); // Nota: normalmente no se incluye en respuestas
        this.email = owner.getEmail();
    }

    // Getters y Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
