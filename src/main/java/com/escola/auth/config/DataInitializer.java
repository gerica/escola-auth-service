package com.escola.auth.config;

import com.escola.auth.model.entity.Role;
import com.escola.auth.model.entity.User;
import com.escola.auth.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DataInitializer implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    Random random = new Random();

    @Override
    public void run(String... args) {
        log.info("Iniciando a verificação de dados iniciais...");

        criarUsuarios();

        log.info("Verificação de dados iniciais concluída.");
    }

    void criarUsuarios() {
        // Cria um usuário ADMIN se não existir
        if (userRepository.findByUsername("admin").isEmpty()) {
            User adminUser = User.builder()
                    .firstname("Admin")
                    .lastname("User")
                    .username("admin")
                    .password(passwordEncoder.encode("6vkWITTQcIKO2y1PEP6mPM")) // Senha forte para admin
                    .roles(Set.of(Role.ADMIN, Role.USER))
                    .build();
            userRepository.save(adminUser);
            log.info(">>> Usuário 'admin' criado com sucesso. Senha: 'admin123'");
        }

        // Cria um usuário USER se não existir
        if (userRepository.findByUsername("user").isEmpty()) {
            User regularUser = User.builder()
                    .firstname("Regular")
                    .lastname("User")
                    .username("user")
                    .password(passwordEncoder.encode("password")) // Senha simples para usuário de teste
                    .roles(Set.of(Role.USER))
                    .build();
            userRepository.save(regularUser);
            log.info(">>> Usuário 'user' criado com sucesso. Senha: 'password'");
        }
    }

}