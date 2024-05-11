package com.fatech.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fatech.entity.Usuario;
import com.fatech.service.EmailService;
import com.fatech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/usuarios")
@CrossOrigin
@Tag(name = "Usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Criar um usuário", description = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar o usuário")
    })
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @Operation(summary = "Buscar todos os usuários", description = "Retorna todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os usuários"),
            @ApiResponse(responseCode = "400", description = "Não há usuários cadastrados")
    })
    @GetMapping
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioService.buscarTodosUsuarios();
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
    })
    @GetMapping(value = "/{id}")
    public Usuario buscarUsuarioPorId(@PathVariable("id") Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }

    @Operation(summary = "Atualizar um usuário existente", description = "Atualiza um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar o usuário")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable long id, @RequestBody Usuario usuario) {
        usuario.setId_usuario(id);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.ok().body(usuarioAtualizado);
    }

    @Operation(summary = "Desativar usuário", description = "Desativar Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário Desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarUsuario(@PathVariable long id) {
        usuarioService.desativarUsuario(id);
        return ResponseEntity.noContent().build();
    }




@PostMapping("/enviar-codigo-verificacao")
public ResponseEntity<?> enviarCodigoVerificacaoPorEmail(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");
    if (email == null || email.isEmpty()) {
        return ResponseEntity.badRequest().body("O email não foi fornecido no corpo da solicitação.");
    }

    try {
        usuarioService.enviarCodigoVerificacaoPorEmail(email);
        return ResponseEntity.ok("Código de verificação enviado com sucesso.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao enviar código de verificação: " + e.getMessage());
    }
}

@PostMapping("/verificar-codigo-verificacao")
public ResponseEntity<?> verificarCodigoVerificacao(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");
    String codigo = requestBody.get("codigo");

    if (email == null || email.isEmpty() || codigo == null || codigo.isEmpty()) {
        return ResponseEntity.badRequest().body("O email e o código de verificação devem ser fornecidos no corpo da solicitação.");
    }

    try {
        boolean codigoValido = usuarioService.verificarCodigoVerificacao(email, codigo);
        if (codigoValido) {
            return ResponseEntity.ok("Código de verificação válido.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Código de verificação inválido.");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao verificar código de verificação: " + e.getMessage());
    }
}

@PutMapping("/alterar-senha")
public ResponseEntity<?> alterarSenha(@RequestBody Map<String, String> requestBody) {
    String email = requestBody.get("email");
    String codigo = requestBody.get("codigo");
    String novaSenha = requestBody.get("novaSenha");

    if (email == null || email.isEmpty() || codigo == null || codigo.isEmpty() || novaSenha == null || novaSenha.isEmpty()) {
        return ResponseEntity.badRequest().body("O email, código de verificação e nova senha devem ser fornecidos no corpo da solicitação.");
    }

    try {
        usuarioService.alterarSenha(email, codigo, novaSenha);
        return ResponseEntity.ok("Senha alterada com sucesso.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao alterar senha: " + e.getMessage());
    }
}



}