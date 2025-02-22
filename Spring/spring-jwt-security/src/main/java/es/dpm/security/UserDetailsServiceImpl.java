package es.dpm.security;

import es.dpm.services.UserEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
Permite que Spring Security sepa cómo extraer el usuario de base de datos
para realizar la autenticación
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserEntityService userService;

    public UserDetailsServiceImpl(UserEntityService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Cargando usuario por username: {}", username);

        return userService.findByUsername(username)
                .map(user -> (UserDetails) user)
                .orElseThrow(() -> {
                    log.warn("Usuario '{}' no encontrado en la base de datos", username);
                    return new UsernameNotFoundException("El usuario '" + username + "' no existe.");
                });
    }
}