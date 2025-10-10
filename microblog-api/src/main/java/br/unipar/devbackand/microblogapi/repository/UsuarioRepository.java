package br.unipar.devbackand.microblogapi.repository;

import br.unipar.devbackand.microblogapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // CORRIJA PARA RETORNAR Optional<Usuario>
    Optional<Usuario> findByUsername(String username);
}