package me.josevasquez.techstoreauthspring.bootstrap;

import me.josevasquez.techstoreauthspring.entity.Role;
import me.josevasquez.techstoreauthspring.entity.User;
import me.josevasquez.techstoreauthspring.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        if (userRepository.findByEmail("admin@techstore.com").isEmpty()) {

            User admin = new User();
            admin.setName("Super Admin");
            admin.setEmail("admin@techstore.com");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(Role.ROLE_ADMIN);

            userRepository.save(admin);
            System.out.println("Cuenta de Super Administrador creada automáticamente.");
        }
    }
}