package com.financasplus.repository;

import com.financasplus.model.Transaction;
import com.financasplus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository para a entidade Transaction
 * Fornece métodos para operações CRUD e consultas personalizadas
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Busca todas as transações de um usuário
     */
    List<Transaction> findByUserOrderByDateDesc(User user);

    /**
     * Busca transações de um usuário em um período específico
     */
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.date BETWEEN :startDate AND :endDate ORDER BY t.date DESC")
    List<Transaction> findByUserAndDateRange(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Busca transações de um usuário por tipo (receita ou despesa)
     */
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.type = :type ORDER BY t.date DESC")
    List<Transaction> findByUserAndType(
            @Param("user") User user,
            @Param("type") Transaction.TransactionType type
    );

    /**
     * Busca transações de um usuário por categoria
     */
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.category.id = :categoryId ORDER BY t.date DESC")
    List<Transaction> findByUserAndCategory(
            @Param("user") User user,
            @Param("categoryId") Long categoryId
    );

    /**
     * Calcula o total de receitas de um usuário
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'RECEITA'")
    BigDecimal calculateTotalIncome(@Param("user") User user);

    /**
     * Calcula o total de despesas de um usuário
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'DESPESA'")
    BigDecimal calculateTotalExpenses(@Param("user") User user);

    /**
     * Calcula o total de receitas em um período
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'RECEITA' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateIncomeByPeriod(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Calcula o total de despesas em um período
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = 'DESPESA' AND t.date BETWEEN :startDate AND :endDate")
    BigDecimal calculateExpensesByPeriod(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
