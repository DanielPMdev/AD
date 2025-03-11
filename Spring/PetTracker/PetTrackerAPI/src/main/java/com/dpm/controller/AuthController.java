package com.dpm.controller;

import com.dpm.dto.security.LoginRequest;
import com.dpm.dto.security.LoginResponse;
import com.dpm.security.JwtTokenProvider;
import com.dpm.dto.owner.FullOwnerDTO;
import com.dpm.entities.Owner;
import com.dpm.security.TokenBlackList;
import com.dpm.services.owner.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author danielpm.dev
 */
@RestController()
public class AuthController {

    private final OwnerService ownerService;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlackList tokenBlackList;

    public AuthController(OwnerService ownerService, AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider, TokenBlackList tokenBlackList) {
        this.ownerService = ownerService;
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenBlackList = tokenBlackList;
    }

    /*
        POST http://localhost:8080/auth/register
     */
    @PostMapping("/auth/register")
    public Owner save(@RequestBody FullOwnerDTO ownerDTO) {

        Owner ownerCreated = new Owner();
        ownerCreated.setId(ownerDTO.getId());
        ownerCreated.setUsername(ownerDTO.getUsername());
        ownerCreated.setPassword(ownerDTO.getPassword());
        ownerCreated.setEmail(ownerDTO.getEmail());

        return ownerService.createOrUpdateOwner(ownerCreated);
    }


    /*
        POST http://localhost:8080/auth/login
     */
    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest loginDTO){
        Authentication authDTO = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());

        Authentication authentication = this.authManager.authenticate(authDTO);
        Owner owner = (Owner) authentication.getPrincipal();

        String token = this.jwtTokenProvider.generateToken(authentication);

        return new LoginResponse(owner.getUsername(),
                owner.getRole(),
                token);
    }

    /*
        POST http://localhost:8080/auth/logout
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            tokenBlackList.blacklistToken(token);
            return ResponseEntity.ok("Logout exitoso");
        } else {
            return ResponseEntity.badRequest().body("Token no proporcionado o inválido");
        }
    }
}

