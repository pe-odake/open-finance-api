package com.pedroodake.openfinanceapi.application.port.out;

import com.pedroodake.openfinanceapi.application.core.domain.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository {

    UserDetails findByLogin(String username);

    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    Usuario getReferenceById(Long id);

    boolean existsById(Long id);
}