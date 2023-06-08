package com.magnilsonti.treinamentoudemy.config;

import com.magnilsonti.treinamentoudemy.domain.User;
import com.magnilsonti.treinamentoudemy.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
@AllArgsConstructor
public class LocalConfig {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner startDb(){
        return args -> {
            var user = new User(null, "user", "magnilson@ti.com.br", "2123");
            var user2 = new User(null, "user2", "magnilson123@ti.com.br", "2123");

            userRepository.saveAll(List.of(user, user2));
        };
    }

}
