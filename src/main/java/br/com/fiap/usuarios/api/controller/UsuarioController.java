package br.com.fiap.usuarios.api.controller;

import br.com.fiap.usuarios.api.exception.UsuarioNaoAutorizadoException;
import br.com.fiap.usuarios.api.model.AutenticarDto;
import br.com.fiap.usuarios.api.model.TokenDto;
import br.com.fiap.usuarios.domain.service.JwtService;
import br.com.fiap.usuarios.domain.service.UsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autenticacao")
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService service;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UsuarioController(UsuarioService service, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping
    public TokenDto autenticar(@RequestBody AutenticarDto autenticarDto){
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(autenticarDto.getUsername(), autenticarDto.getPassword()));
            if (authentication.isAuthenticated()) {
                return TokenDto.builder()
                        .token(jwtService.generateToken(autenticarDto.getUsername(), authentication.getAuthorities()))
                        .build();
            } else {
                throw new UsuarioNaoAutorizadoException("Usuário não foi autorizado na base de dados.");
            }
        } catch (Exception e) {
            throw new UsuarioNaoAutorizadoException("Usuário não foi autorizado na base de dados.");
        }
    }

}
