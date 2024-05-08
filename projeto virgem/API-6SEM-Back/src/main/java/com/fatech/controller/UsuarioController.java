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

    // @PostMapping("/enviar-email")
    // public ResponseEntity<?> enviarEmail(@RequestParam String email) {
    //     try {
    //         emailService.enviarEmail(email, "template_a5flxiu", "64skWYeEq_nk8m4PE", "{\"email\":\"" + email + "\"}");
    //         return ResponseEntity.ok("Email enviado com sucesso.");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Erro ao enviar email: " + e.getMessage());
    //     }
    // }

    @PostMapping("/enviar-email")
public ResponseEntity<?> enviarEmail(@RequestParam(required = false) String email, @RequestBody(required = false) Map<String, Object> requestBody) {
    try {
        // Verifica se o email está presente na URL ou no corpo da solicitação
        if (email == null && requestBody != null && requestBody.containsKey("email")) {
            // Obtém o email do corpo da solicitação
            email = (String) requestBody.get("email");
        }

        // Verifica se o email foi encontrado em algum lugar
        if (email == null) {
            return ResponseEntity.badRequest().body("O endereço de email não foi fornecido.");
        }

        // Chama o serviço de envio de email
        emailService.enviarEmail(email, "template_a5flxiu", "64skWYeEq_nk8m4PE", "{\"email\":\"" + email + "\"}");
        
        return ResponseEntity.ok("Email enviado com sucesso.");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao enviar email: " + e.getMessage());
    }
}


}