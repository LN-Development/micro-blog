package br.unipar.devbackand.microblogapi.repository;

import br.unipar.devbackand.microblogapi.model.Curtida;
import br.unipar.devbackand.microblogapi.model.Post;
import br.unipar.devbackand.microblogapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
    // Busca uma curtida específica por post e usuário para validar se já existe
    Optional<Curtida> findByPostAndUsuario(Post post, Usuario usuario);
}