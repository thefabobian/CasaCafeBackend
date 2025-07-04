package com.casacafemonteria.security.auth.service.login;

import com.casacafemonteria.security.auth.factory.AuthUserMapper;
import com.casacafemonteria.user.persistence.entities.UserEntity;
import com.casacafemonteria.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomsDetailServices implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthUserMapper authUserMapper;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return authUserMapper.toUserDetails(userEntity);
    }
}
