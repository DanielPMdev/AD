package com.dpm.dto.security;

import com.dpm.entities.Role;

/**
 * @author danielpm.dev
 */
public record LoginResponse(String username, Role role, String token) {
}
