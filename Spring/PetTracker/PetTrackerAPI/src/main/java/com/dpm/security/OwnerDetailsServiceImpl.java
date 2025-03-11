package com.dpm.security;

import com.dpm.services.owner.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author danielpm.dev
 */
@Service
public class OwnerDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(OwnerDetailsServiceImpl.class);

    private final OwnerService ownerService;

    public OwnerDetailsServiceImpl(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Cargando usuario por username: {}", username);

        return ownerService.getOwnerByName(username)
                .map(owner -> (UserDetails) owner)
                .orElseThrow(() -> {
                    log.warn("Usuario '{}' no encontrado en la base de datos", username);
                    return new UsernameNotFoundException("El usuario '" + username + "' no existe.");
                });
    }
}
