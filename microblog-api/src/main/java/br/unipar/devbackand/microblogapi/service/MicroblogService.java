package br.unipar.devbackand.microblogapi.service;

import br.unipar.devbackand.microblogapi.model.Curtida;
import br.unipar.devbackand.microblogapi.model.Post;
import br.unipar.devbackand.microblogapi.model.Usuario;
import br.unipar.devbackand.microblogapi.repository.CurtidaRepository;
import br.unipar.devbackand.microblogapi.repository.PostRepository;
import br.unipar.devbackand.microblogapi.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MicroblogService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurtidaRepository curtidaRepository;

    // Métodos para Usuários
    public Usuario criarUsuario(Usuario usuario) {
        // CORREÇÃO: Adicionado .isPresent() para checar o Optional
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username '" + usuario.getUsername() + "' já existe.");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario editarUsuario(Usuario usuario) {
        if (!usuarioRepository.existsById(usuario.getId())) {
            throw new EntityNotFoundException("Usuário com ID " + usuario.getId() + " não encontrado para edição.");
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }


    // Métodos para Posts
    public Post criarPost(Post post, Long autorId) {
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor com ID " + autorId + " não encontrado."));

        post.setAutor(autor);
        post.setCreatedAt(LocalDateTime.now());
        post.setLikesCount(0L);
        return postRepository.save(post);
    }

    public List<Post> listarPostsCronologico() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Post> listarPostsRelevancia() {
        return postRepository.findAllByOrderByLikesCountDesc();
    }


    // Métodos para Curtidas
    @Transactional
    public void curtirPost(Long postId, Long usuarioId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post com ID " + postId + " não encontrado."));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));

        if (curtidaRepository.findByPostAndUsuario(post, usuario).isPresent()) {
            throw new IllegalStateException("Usuário já curtiu este post.");
        }

        Curtida curtida = new Curtida();
        curtida.setPost(post);
        curtida.setUsuario(usuario);
        curtida.setCreatedAt(LocalDateTime.now());
        curtidaRepository.save(curtida);

        post.setLikesCount(post.getLikesCount() + 1);
        postRepository.save(post);
    }

    @Transactional
    public void removerCurtida(Long postId, Long usuarioId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post com ID " + postId + " não encontrado."));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));

        Curtida curtida = curtidaRepository.findByPostAndUsuario(post, usuario)
                .orElseThrow(() -> new IllegalStateException("Este usuário não curtiu o post para remover a curtida."));

        curtidaRepository.delete(curtida);

        post.setLikesCount(Math.max(0, post.getLikesCount() - 1));
        postRepository.save(post);
    }
}