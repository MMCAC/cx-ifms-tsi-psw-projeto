package com.financasplus.repository;

import com.financasplus.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para a entidade Category
 * Fornece métodos para operações CRUD no banco de dados
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Busca uma categoria pelo nome
     */
    Optional<Category> findByName(String name);

    /**
     * Verifica se uma categoria com o nome já existe
     */
    boolean existsByName(String name);
}
