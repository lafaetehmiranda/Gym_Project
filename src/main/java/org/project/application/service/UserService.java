package org.project.application.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.project.application.dto.CreateUserRequest;
import org.project.application.dto.UserDTO;
import org.project.application.mapper.UserMapper;
import org.project.domain.model.User;
import org.project.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Inject
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserDTO create(CreateUserRequest request) {
        User user = User.create(request.userType(), request.name(), request.email(), "");
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public Optional<UserDTO> findById(UUID id) {
        return userRepository.findDomainById(id)
                .map(userMapper::toDTO);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAllDomain().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
