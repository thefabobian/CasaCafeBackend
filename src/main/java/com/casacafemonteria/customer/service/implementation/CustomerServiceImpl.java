package com.casacafemonteria.customer.service.implementation;

import com.casacafemonteria.cart.persistence.entities.CartEntity;
import com.casacafemonteria.cart.persistence.repositories.CartRepository;
import com.casacafemonteria.customer.factory.CustomerFactory;
import com.casacafemonteria.customer.persistence.entities.CustomerEntity;
import com.casacafemonteria.customer.persistence.repositories.CustomerRepository;
import com.casacafemonteria.customer.presentation.dto.CustomerDto;
import com.casacafemonteria.customer.presentation.payload.CustomerPayload;
import com.casacafemonteria.customer.service.interfaces.ICustomerService;
import com.casacafemonteria.helpers.exception.exceptions.ConflictException;
import com.casacafemonteria.helpers.exception.exceptions.ResourceNotFoundException;
import com.casacafemonteria.security.auth.persistence.model.rol.RoleEntity;
import com.casacafemonteria.security.auth.persistence.model.rol.RoleEnum;
import com.casacafemonteria.security.auth.persistence.repositories.RoleRepository;
import com.casacafemonteria.user.persistence.entities.UserEntity;
import com.casacafemonteria.user.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final ModelMapper modelMapper;
    private final CustomerFactory customerFactory;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional
    public void save(CustomerPayload customerPayload) {
        // Validaciones de username y email
        if (userRepository.existsByUsername(customerPayload.getUsername())) {
            throw new ConflictException("El username ya existe");
        }
        if (userRepository.existsByEmail(customerPayload.getEmail())) {
            throw new ConflictException("El email ya existe");
        }

        UUID newUuid = UUID.randomUUID();

        RoleEntity customerRole = roleRepository.findByRoleEnum(RoleEnum.CUSTOMER)
                .orElseThrow(() -> new RuntimeException("El rol de cliente no existe"));

        UserEntity userEntity = UserEntity.builder()
                .uuid(newUuid)
                .username(customerPayload.getUsername())
                .email(customerPayload.getEmail())
                .password(passwordEncoder.encode(customerPayload.getPassword()))
                .roles(Set.of(customerRole))
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .isEnabled(true)
                .build();

        userEntity = userRepository.save(userEntity);

        CustomerEntity customerEntity = CustomerEntity.builder()
                .userEntity(userEntity)
                .uuid(newUuid)
                .dni(customerPayload.getDni())
                .phone(customerPayload.getPhone())
                .address(customerPayload.getAddress())
                .birth(customerPayload.getBirth())
                .build();

        customerRepository.save(customerEntity);

        // Crear carrito para el cliente
        if (!cartRepository.findByCustomerEntity(customerEntity).isPresent()) {
            cartRepository.save(CartEntity.builder()
                    .customerEntity(customerEntity)
                    .build());
        }
    }

    @Override
    @Transactional
    public void update(CustomerPayload customerPayload, UUID uuid) {
        UserEntity userEntity = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con el uuid " + uuid + " no existe"));

        CustomerEntity customerEntity = customerRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente con el uuid " + uuid + " no existe"));

        // Actualizar UserEntity
        userEntity.setUsername(customerPayload.getUsername());
        userEntity.setEmail(customerPayload.getEmail());
        userEntity.setPassword(passwordEncoder.encode(customerPayload.getPassword()));

        // Actualizar CustomerEntity
        customerEntity.setDni(customerPayload.getDni());
        customerEntity.setPhone(customerPayload.getPhone());
        customerEntity.setAddress(customerPayload.getAddress());
        customerEntity.setBirth(customerPayload.getBirth());

        userRepository.save(userEntity);
        customerRepository.save(customerEntity);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        // 1. Buscar el usuario por UUID
        UserEntity userEntity = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con UUID: " + uuid));

        // 2. Buscar el cliente asociado a ese usuario
        CustomerEntity customerEntity = customerRepository.findByUserEntity(userEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado para el usuario con UUID: " + uuid));

        // 3. Eliminar al cliente
        customerRepository.delete(customerEntity);

        // 4. Marcar el usuario como inactivo en lugar de eliminarlo
        userEntity.setEnabled(false); // Si tu campo se llama diferente, ajústalo
        userEntity.setAccountNoLocked(false);
        userEntity.setAccountNoExpired(false);
        userEntity.setCredentialNoExpired(false);

        userRepository.save(userEntity);
    }

    @Override
    public Page<CustomerDto> findByDni(String dni, Pageable pageable) {
        return customerRepository.findByDni(dni, pageable)
                .map(customerFactory::customerDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDto> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerFactory::customerDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findByUuid(UUID uuid) {
        return customerRepository.findByUuid(uuid)
                .map(customerFactory::customerDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con UUID: " + uuid));
    }
}
