package br.com.fiap.usuarios.domain.service;

import br.com.fiap.usuarios.api.model.UsuarioDto;
import br.com.fiap.usuarios.config.MessageConfig;
import br.com.fiap.usuarios.domain.exception.UsuarioNaoEncontradoException;
import br.com.fiap.usuarios.domain.model.Usuario;
import br.com.fiap.usuarios.domain.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageConfig messageConfig;

    public List<UsuarioDto> buscarClientePorNome(String nome) {
        var usuarioList = usuarioRepository.findByNomeIgnoreCaseContaining(nome);

        return usuarioList.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .toList();
    }

    public void add(UsuarioDto usuarioDto) {
        var usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }

    public UsuarioDto update(UsuarioDto usuarioDto, Long id) {
        var optionalUsuario = usuarioRepository.findById(id);

        if(optionalUsuario.isPresent()){
            var usuario = optionalUsuario.get();
            modelMapper.map(usuarioDto, usuario);

            usuario = usuarioRepository.save(usuario);

            return modelMapper.map(usuario, UsuarioDto.class);
        } else {
            throw new UsuarioNaoEncontradoException(messageConfig.getClienteNaoEncontrado());
        }
    }

    public void delete(Long id) {
        var optionalCliente = usuarioRepository.findById(id);

        if(optionalCliente.isPresent()){
            usuarioRepository.deleteById(id);
        } else {
            throw new UsuarioNaoEncontradoException(messageConfig.getClienteNaoEncontrado());
        }
    }

    public UsuarioDto getClienteById(Long id) {
        var optionalCliente = usuarioRepository.findById(id);

        if(optionalCliente.isPresent()){
            return modelMapper.map(optionalCliente.get(), UsuarioDto.class);
        } else {
            throw new UsuarioNaoEncontradoException(messageConfig.getClienteNaoEncontrado());
        }
    }

    public List<UsuarioDto> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDto.class))
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = usuarioRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("could not found user..!!");
        }
        return user;
    }
}
