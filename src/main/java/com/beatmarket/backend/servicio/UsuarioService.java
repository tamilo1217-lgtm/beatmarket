// UsuarioService.java
package com.beatmarket.backend.servicio;

import com.beatmarket.backend.excepcion.RecursoDuplicadoException;
import com.beatmarket.backend.excepcion.CredencialesInvalidasException;
import com.beatmarket.backend.excepcion.RecursoNoEncontradoException;
import com.beatmarket.backend.modelo.Usuario;
import com.beatmarket.backend.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id " + id));
    }

    @Override
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese nombre de usuario");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Integer id, Usuario datosNuevos) {
        Usuario existente = buscarPorId(id);

        if (!existente.getEmail().equals(datosNuevos.getEmail())
                && usuarioRepository.existsByEmail(datosNuevos.getEmail())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese email");
        }
        if (!existente.getNombreUsuario().equals(datosNuevos.getNombreUsuario())
                && usuarioRepository.existsByNombreUsuario(datosNuevos.getNombreUsuario())) {
            throw new RecursoDuplicadoException("Ya existe un usuario con ese nombre de usuario");
        }

        existente.setNombre(datosNuevos.getNombre());
        existente.setNombreUsuario(datosNuevos.getNombreUsuario());
        existente.setEmail(datosNuevos.getEmail());
        existente.setRol(datosNuevos.getRol());
        existente.setEstado(datosNuevos.getEstado());

        if (datosNuevos.getPassword() != null && !datosNuevos.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(datosNuevos.getPassword()));
        }

        return usuarioRepository.save(existente);
    }

    @Override
    public void eliminar(Integer id) {
        usuarioRepository.delete(buscarPorId(id));
    }

    @Override
    public Usuario login(Usuario credenciales) {
        if (credenciales.getEmail() == null || credenciales.getEmail().isBlank()
                || credenciales.getPassword() == null || credenciales.getPassword().isBlank()) {
            throw new CredencialesInvalidasException("El email y la contraseña son obligatorios");
        }

        Usuario usuario = usuarioRepository.findByEmail(credenciales.getEmail())
                .orElseThrow(() -> new CredencialesInvalidasException("Email o contraseña incorrectos"));

        if (!passwordEncoder.matches(credenciales.getPassword(), usuario.getPassword())) {
            throw new CredencialesInvalidasException("Email o contraseña incorrectos");
        }
        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new CredencialesInvalidasException("El usuario está inactivo");
        }

        return usuario;
    }
}