// AuthController.java
package com.beatmarket.backend.controlador;

import com.beatmarket.backend.modelo.Usuario;
import com.beatmarket.backend.seguridad.JwtUtil;
import com.beatmarket.backend.servicio.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario credenciales) {
        Usuario usuario = usuarioService.login(credenciales);
        String token = jwtUtil.generarToken(usuario);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("token", token);
        respuesta.put("tipo", "Bearer");
        respuesta.put("usuario", usuario);

        return ResponseEntity.ok(respuesta);
    }
}