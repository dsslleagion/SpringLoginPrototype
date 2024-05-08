package com.fatech.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatech.entity.Usuario;
import com.fatech.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criarUsuario(Usuario usuario) {
        if (usuario == null ||
                usuario.getEmail() == null ||
                usuario.getEmail().isBlank() ||
                usuario.getMatricula_empresa() == null ||
                usuario.getMatricula_empresa().isBlank() ||
                usuario.getNome_usuario() == null ||
                usuario.getNome_usuario().isBlank() ||
                // usuario.getSenha() == null ||
                // usuario.getSenha().isBlank() ||
                usuario.getTipo_usuario() == null ||
                usuario.getTipo_usuario().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados invalidos");
        }
        return usuarioRepository.save(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> buscarTodosUsuarios() {
        List<Usuario> todosUsuario = usuarioRepository.findAll();
        if (todosUsuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário encontrado");
        }
        List<Usuario> usuariosOrdenados = todosUsuario.stream()
        .sorted(Comparator.comparing(Usuario::getId_usuario))
        .collect(Collectors.toList());
        return usuariosOrdenados;
    }

    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if (usuarioOp.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }

        return usuarioOp.get();

    }

    public Usuario atualizarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void desativarUsuario(long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.desativar(); // Desativa o usuário
            usuarioRepository.save(usuario); // Salva as alterações
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao desativar usuário.");
        }
    }
     @Autowired
    private EmailService emailService;

    // Função para enviar código de verificação por email
    public void enviarCodigoVerificacaoPorEmail(String email) {
        // Verificar se o email está registrado no sistema
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            String codigoVerificacao = gerarCodigoVerificacao();
            usuario.setCodigoVerificacao(codigoVerificacao);
            usuarioRepository.save(usuario);
            try {
                emailService.enviarEmail(email, "template_a5flxiu", "64skWYeEq_nk8m4PE", "{\"email\":\"" + email + "\", \"codigoVerificacao\":\"" + codigoVerificacao + "\"}");
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao enviar email: " + e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }

    // Função para verificar código de verificação
    public boolean verificarCodigoVerificacao(String email, String codigo) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmailAndCodigoVerificacao(email, codigo);
        return optionalUsuario.isPresent();
    }

    // Função para alterar senha
    public void alterarSenha(String email, String novaSenha) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setSenha(novaSenha);
            usuarioRepository.save(usuario);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
    }

    // Método para gerar um código de verificação
    private String gerarCodigoVerificacao() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000); // Gera um número aleatório entre 100000 e 999999
        return String.valueOf(codigo);
    }

}
