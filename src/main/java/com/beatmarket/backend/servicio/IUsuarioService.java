// IUsuarioService.java
package com.beatmarket.backend.servicio;

import com.beatmarket.backend.modelo.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> listarTodos();
    Usuario buscarPorId(Integer id);
    Usuario crear(Usuario usuario);
    Usuario actualizar(Integer id, Usuario usuario);
    void eliminar(Integer id);
    Usuario login(Usuario credenciales);
}