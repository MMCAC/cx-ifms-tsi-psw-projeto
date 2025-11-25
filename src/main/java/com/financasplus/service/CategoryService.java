package com.financasplus.service;

import com.financasplus.model.Category;
import com.financasplus.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service para gerenciar operações de Category
 * Contém a lógica de negócio relacionada a categorias
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Busca todas as categorias
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Busca uma categoria pelo ID
     */
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Busca uma categoria pelo nome
     */
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Cria uma nova categoria
     */
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Categoria com este nome já existe!");
        }
        return categoryRepository.save(category);
    }

    /**
     * Atualiza uma categoria existente
     */
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        if (categoryDetails.getName() != null && !categoryDetails.getName().equals(category.getName())) {
            if (categoryRepository.existsByName(categoryDetails.getName())) {
                throw new IllegalArgumentException("Categoria com este nome já existe!");
            }
            category.setName(categoryDetails.getName());
        }
        if (categoryDetails.getDescription() != null) {
            category.setDescription(categoryDetails.getDescription());
        }
        if (categoryDetails.getColor() != null) {
            category.setColor(categoryDetails.getColor());
        }

        return categoryRepository.save(category);
    }

    /**
     * Deleta uma categoria
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    /**
     * Inicializa as categorias padrão
     */
    public void initializeDefaultCategories() {
        if (categoryRepository.count() == 0) {
            String[][] defaultCategories = {
                {"Alimentação", "Gastos com comida e bebida", "#FF6B6B"},
                {"Transporte", "Gastos com transporte", "#4ECDC4"},
                {"Lazer", "Gastos com lazer e diversão", "#FFE66D"},
                {"Contas Fixas", "Contas fixas e obrigações", "#95E1D3"},
                {"Saúde", "Gastos com saúde", "#C7CEEA"},
                {"Educação", "Gastos com educação", "#B5EAD7"},
                {"Outros", "Outros gastos", "#D4A5A5"}
            };

            for (String[] cat : defaultCategories) {
                Category category = new Category(cat[0], cat[1], cat[2]);
                categoryRepository.save(category);
            }
        }
    }
}
