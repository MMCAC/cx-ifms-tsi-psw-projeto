package com.financasplus.repository;

import com.financasplus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para a entidade User
 * Fornece métodos para operações CRUD no banco de dados
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo username
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca um usuário pelo email
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se um username já existe
     */
    boolean existsByUsername(String username);

    /**
     * Verifica se um email já existe
     */
    boolean existsByEmail(String email);
}
