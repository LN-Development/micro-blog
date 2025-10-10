package br.unipar.devbackand.microblogapi.controller;

import br.unipar.devbackand.microblogapi.model.Post;
import br.unipar.devbackand.microblogapi.service.MicroblogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final MicroblogService service;

    public PostController(MicroblogService service) {
        this.service = service;
    }

    // Endpoint para criar um post, precisa do ID do autor
    @PostMapping("/{autorId}")
    public ResponseEntity<Post> criarPost(@RequestBody Post post, @PathVariable Long autorId) {
        Post novoPost = service.criarPost(post, autorId);
        return ResponseEntity.status(201).body(novoPost);
    }

    @GetMapping("/cronologico")
    public ResponseEntity<List<Post>> listarPostsCronologico() {
        List<Post> posts = service.listarPostsCronologico();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/relevancia")
    public ResponseEntity<List<Post>> listarPostsRelevancia() {
        List<Post> posts = service.listarPostsRelevancia();
        return ResponseEntity.ok(posts);
    }
}