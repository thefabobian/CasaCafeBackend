package com.casacafemonteria.user.service.implementation;

import com.casacafemonteria.user.factory.UserFactory;
import com.casacafemonteria.user.persistence.repositories.UserRepository;
import com.casacafemonteria.user.service.interfaces.IUserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements IUserServices {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
}
