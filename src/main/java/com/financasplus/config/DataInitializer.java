package com.financasplus.config;

import com.financasplus.model.Category;
import com.financasplus.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por inicializar dados padrão na aplicação
 * Executa automaticamente quando a aplicação inicia
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Método executado automaticamente na inicialização da aplicação
     */
    @Override
    public void run(String... args) throws Exception {
        // Verifica se já existem categorias no banco
        if (categoryRepository.count() == 0) {
            initializeDefaultCategories();
        }
    }

    /**
     * Inicializa as categorias padrão
     */
    private void initializeDefaultCategories() {
        // Array com as categorias padrão
        String[][] categoriesData = {
            {"Alimentação", "Gastos com comida e bebida", "#FF6B6B"},
            {"Transporte", "Gastos com transporte", "#4ECDC4"},
            {"Lazer", "Gastos com lazer e diversão", "#FFE66D"},
            {"Contas Fixas", "Contas fixas e obrigações", "#95E1D3"},
            {"Saúde", "Gastos com saúde", "#C7CEEA"},
            {"Educação", "Gastos com educação", "#B5EAD7"},
            {"Outros", "Outros gastos", "#D4A5A5"}
        };

        // Criar e salvar cada categoria
        for (String[] catData : categoriesData) {
            Category category = new Category(catData[0], catData[1], catData[2]);
            categoryRepository.save(category);
            System.out.println("✓ Categoria criada: " + catData[0]);
        }

        System.out.println("\n✓ Categorias padrão inicializadas com sucesso!");
    }
}
