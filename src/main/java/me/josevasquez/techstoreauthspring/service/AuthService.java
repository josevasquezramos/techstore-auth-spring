package me.josevasquez.techstoreauthspring.service;

import me.josevasquez.techstoreauthspring.dto.RegisterRequestDTO;
import me.josevasquez.techstoreauthspring.entity.User;
import me.josevasquez.techstoreauthspring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return "Usuario registrado exitosamente";
    }
}