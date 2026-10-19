// DataSeeder.java
package com.beatmarket.backend.config;

import com.beatmarket.backend.modelo.Rol;
import com.beatmarket.backend.modelo.Usuario;
import com.beatmarket.backend.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador BeatMarket", "admin",
                    "admin@beatmarket.com", passwordEncoder.encode("admin123"), Rol.ADMIN));

            usuarioRepository.save(new Usuario("Granuja Beats", "granujabeats",
                    "productor@beatmarket.com", passwordEncoder.encode("productor123"), Rol.PRODUCTOR));

            usuarioRepository.save(new Usuario("Juan Pérez", "jperez",
                    "comprador@beatmarket.com", passwordEncoder.encode("comprador123"), Rol.COMPRADOR));

            System.out.println("Usuarios de prueba creados correctamente.");
        }
    }
}