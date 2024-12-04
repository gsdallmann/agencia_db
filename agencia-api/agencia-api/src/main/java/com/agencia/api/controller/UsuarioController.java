package com.agencia.api.controller;

import com.agencia.api.dto.UsuarioDTO;
import com.agencia.api.model.Usuario;
import com.agencia.api.security.JwtTokenProvider;
import com.agencia.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO.getNome(), usuarioDTO.getEmail(), usuarioDTO.getSenha());
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(usuarioDTO.getEmail());

            if (usuario != null && passwordEncoder.matches(usuarioDTO.getSenha(), usuario.getSenha())) {
                String token = jwtTokenProvider.createToken(usuario.getEmail());

                return ResponseEntity.ok().body("Bearer " + token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar login");
        }
    }
}
