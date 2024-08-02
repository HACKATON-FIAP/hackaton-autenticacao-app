package br.com.fiap.usuarios.api.controller;

import br.com.fiap.usuarios.api.model.AutenticarDto;
import br.com.fiap.usuarios.api.model.TokenDto;
import br.com.fiap.usuarios.api.model.UsuarioDto;
import br.com.fiap.usuarios.domain.exception.SenhasNaoCombinamException;
import br.com.fiap.usuarios.domain.service.JwtService;
import br.com.fiap.usuarios.domain.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/autenticacao")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @PostMapping("/autenticar")
    public TokenDto autenticar(@RequestBody AutenticarDto autenticarDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(autenticarDto.getUsername(), autenticarDto.getPassword()));
        if(authentication.isAuthenticated()){
            return TokenDto.builder()
                    .token(jwtService.generateToken(autenticarDto.getUsername(), authentication.getAuthorities()))
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

}
