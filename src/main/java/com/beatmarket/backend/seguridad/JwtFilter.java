// JwtFilter.java
package com.beatmarket.backend.seguridad;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain cadena) throws ServletException, IOException {

        String cabecera = request.getHeader("Authorization");

        if (cabecera != null && cabecera.startsWith("Bearer ")) {
            String token = cabecera.substring(7);

            if (jwtUtil.esValido(token)) {
                Claims claims = jwtUtil.obtenerClaims(token);
                String email = claims.getSubject();
                String rol = claims.get("rol", String.class);

                UsernamePasswordAuthenticationToken autenticacion =
                        new UsernamePasswordAuthenticationToken(
                                email, null, List.of(new SimpleGrantedAuthority("ROLE_" + rol)));

                SecurityContextHolder.getContext().setAuthentication(autenticacion);
            }
        }

        cadena.doFilter(request, response);
    }
}