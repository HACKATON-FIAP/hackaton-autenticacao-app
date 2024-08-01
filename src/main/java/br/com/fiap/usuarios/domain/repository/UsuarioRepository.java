package br.com.fiap.usuarios.domain.repository;

import br.com.fiap.usuarios.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNomeIgnoreCaseContaining(String nome);

    public Usuario findByUsername(String username);

    Usuario findFirstById(Long id);
}
