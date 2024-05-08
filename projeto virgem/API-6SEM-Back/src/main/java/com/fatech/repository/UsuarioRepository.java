package com.fatech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fatech.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> getByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndCodigoVerificacao(String email, String codigo);

}
