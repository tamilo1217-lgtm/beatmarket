// AuthController.java
package com.beatmarket.backend.controlador;

import com.beatmarket.backend.modelo.Usuario;
import com.beatmarket.backend.servicio.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario credenciales) {
        return ResponseEntity.ok(usuarioService.login(credenciales));
    }
}