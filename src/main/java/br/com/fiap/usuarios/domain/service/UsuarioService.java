package br.com.fiap.usuarios.domain.service;

import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usuarioRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("could not found user..!!");
        }
        return user;
    }
}
