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
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @GetMapping("/todos")
    public List<UsuarioDto> buscarClientePorNome(@RequestParam(required = false) String nome) {
        if(Objects.isNull(nome)){
            return service.findAll();
        }else{
            return service.buscarClientePorNome(nome);
        }
    }

    @GetMapping("/{id}")
    public UsuarioDto getClienteById(@PathVariable Long id) {
        return service.getClienteById(id);
    }

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody @Valid UsuarioDto usuarioDto) {
        if(!usuarioDto.getPassword().equals(usuarioDto.getPasswordConfirmation())) {
            throw new SenhasNaoCombinamException("Senhas enviadas não são iguais");
        }

        service.add(usuarioDto);
    }

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

    @PutMapping("/{id}")
    public UsuarioDto update(@RequestBody @Valid UsuarioDto usuarioDto, @PathVariable("id") Long id){
        return service.update(usuarioDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable("id") Long id){
        service.delete(id);
    }
}
