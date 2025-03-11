package com.dpm.dto.owner;

/**
 * @author danielpm.dev
 */
public class PasswordOwnerDTO {

    private Long id;
    private String password;

    public PasswordOwnerDTO() {
    }

    public PasswordOwnerDTO(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
