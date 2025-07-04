package com.casacafemonteria.security.auth.service.sign_in;

import com.casacafemonteria.security.auth.controller.dto.AuthResponse;
import com.casacafemonteria.security.auth.controller.payload.AuthCreateUserRequest;
import com.casacafemonteria.security.auth.factory.AuthUserMapper;
import com.casacafemonteria.security.auth.persistence.model.rol.RoleEntity;
import com.casacafemonteria.security.auth.persistence.repositories.RoleRepository;
import com.casacafemonteria.security.utils.jwt.JwtTokenProvider;
import com.casacafemonteria.user.persistence.entities.UserEntity;
import com.casacafemonteria.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthRegisterService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUserMapper authUserMapper;

    public AuthResponse register(AuthCreateUserRequest request) {
        // Obtener los roles del usuario
        Set<RoleEntity> roleEntities = new HashSet<>(roleRepository
                .findRoleEntitiesByRoleEnumIn(request.roleRequest().roleListName()));

        if (roleEntities.isEmpty()) throw new IllegalArgumentException("Roles not found");

        // Mapear y guardar el usuario
        UserEntity user = authUserMapper.toUserEntity(request, roleEntities, passwordEncoder);
        userRepository.save(user);

        // Crear autenticación con roles
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, authUserMapper.mapRoles(user.getRoles()));

        // Generar Access Token
        String accessToken = jwtTokenProvider.createAccessToken(authentication);

        return new AuthResponse(user.getUsername(), "User created successfully", accessToken);
    }
}
