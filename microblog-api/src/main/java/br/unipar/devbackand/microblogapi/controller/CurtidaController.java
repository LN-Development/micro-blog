package br.unipar.devbackand.microblogapi.controller;

import br.unipar.devbackand.microblogapi.service.MicroblogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts") // As ações de curtir estão relacionadas a um post
public class CurtidaController {

    private final MicroblogService service;

    public CurtidaController(MicroblogService service) {
        this.service = service;
    }

    /**
     * Endpoint para um usuário curtir um post.
     * Ex: POST /posts/1/curtir/2  (Usuário 2 curtindo o Post 1)
     */
    @PostMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<Void> curtirPost(@PathVariable Long postId, @PathVariable Long usuarioId) {
        service.curtirPost(postId, usuarioId);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }

    /**
     * Endpoint para um usuário remover a curtida de um post.
     * Ex: DELETE /posts/1/curtir/2 (Usuário 2 removendo a curtida do Post 1)
     */
    @DeleteMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<Void> removerCurtida(@PathVariable Long postId, @PathVariable Long usuarioId) {
        service.removerCurtida(postId, usuarioId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}