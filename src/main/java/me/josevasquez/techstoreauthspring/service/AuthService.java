package me.josevasquez.techstoreauthspring.service;

import me.josevasquez.techstoreauthspring.dto.AuthResponseDTO;
import me.josevasquez.techstoreauthspring.dto.LoginRequestDTO;
import me.josevasquez.techstoreauthspring.dto.RegisterRequestDTO;
import me.josevasquez.techstoreauthspring.entity.Role;
import me.josevasquez.techstoreauthspring.entity.User;
import me.josevasquez.techstoreauthspring.repository.UserRepository;
import me.josevasquez.techstoreauthspring.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(Role.ROLE_CUSTOMER);

        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        return "Usuario registrado exitosamente";
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponseDTO(jwtToken);
    }
}