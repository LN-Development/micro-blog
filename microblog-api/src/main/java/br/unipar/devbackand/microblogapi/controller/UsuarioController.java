package br.unipar.devbackand.microblogapi.controller;

import br.unipar.devbackand.microblogapi.model.Usuario;
import br.unipar.devbackand.microblogapi.service.MicroblogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final MicroblogService service;

    // Injeção de dependência via construtor (boa prática)
    public UsuarioController(MicroblogService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        // Retorna o usuário criado e o status 201 Created
        return ResponseEntity.status(201).body(service.criarUsuario(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Garante que o ID da URL seja o usado na atualização
        usuario.setId(id);
        Usuario usuarioAtualizado = service.editarUsuario(usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        // Nome do método corrigido para "listarTodosUsuarios"
        List<Usuario> usuarios = service.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }
}