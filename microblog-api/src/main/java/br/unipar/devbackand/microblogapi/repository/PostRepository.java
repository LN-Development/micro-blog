package br.unipar.devbackand.microblogapi.repository;

import br.unipar.devbackand.microblogapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Busca todos os posts ordenados pela data de criação decrescente
    List<Post> findAllByOrderByCreatedAtDesc();

    // Busca todos os posts ordenados pela contagem de curtidas decrescente
    List<Post> findAllByOrderByLikesCountDesc();
}