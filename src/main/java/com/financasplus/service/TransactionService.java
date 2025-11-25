package com.financasplus.service;

import com.financasplus.model.Transaction;
import com.financasplus.model.User;
import com.financasplus.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.LinkedHashMap;
import com.financasplus.model.Category;

/**
 * Service para gerenciar operações de Transaction
 * Contém a lógica de negócio relacionada a transações
 */
@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Busca todas as transações de um usuário
     */
    public List<Transaction> findByUser(User user) {
        return transactionRepository.findByUserOrderByDateDesc(user);
    }

    /**
     * Busca uma transação pelo ID
     */
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    /**
     * Busca transações de um usuário em um período específico
     */
    public List<Transaction> findByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByUserAndDateRange(user, startDate, endDate);
    }

    /**
     * Busca transações de um usuário por tipo
     */
    public List<Transaction> findByUserAndType(User user, Transaction.TransactionType type) {
        return transactionRepository.findByUserAndType(user, type);
    }

    /**
     * Busca transações de um usuário por categoria
     */
    public List<Transaction> findByUserAndCategory(User user, Long categoryId) {
        return transactionRepository.findByUserAndCategory(user, categoryId);
    }

    /**
     * Cria uma nova transação
     */
    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser maior que zero!");
        }
        return transactionRepository.save(transaction);
    }

    /**
     * Atualiza uma transação existente
     */
    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada!"));

        if (transactionDetails.getDescription() != null) {
            transaction.setDescription(transactionDetails.getDescription());
        }
        if (transactionDetails.getAmount() != null) {
            if (transactionDetails.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O valor da transação deve ser maior que zero!");
            }
            transaction.setAmount(transactionDetails.getAmount());
        }
        if (transactionDetails.getDate() != null) {
            transaction.setDate(transactionDetails.getDate());
        }
        if (transactionDetails.getCategory() != null) {
            transaction.setCategory(transactionDetails.getCategory());
        }
        if (transactionDetails.getType() != null) {
            transaction.setType(transactionDetails.getType());
        }

        return transactionRepository.save(transaction);
    }

    /**
     * Deleta uma transação
     */
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    /**
     * Calcula o total de receitas de um usuário
     */
    public BigDecimal calculateTotalIncome(User user) {
        return transactionRepository.calculateTotalIncome(user);
    }

    /**
     * Calcula o total de despesas de um usuário
     */
    public BigDecimal calculateTotalExpenses(User user) {
        return transactionRepository.calculateTotalExpenses(user);
    }

    /**
     * Calcula o saldo (receitas - despesas) de um usuário
     */
    public BigDecimal calculateBalance(User user) {
        BigDecimal income = calculateTotalIncome(user);
        BigDecimal expenses = calculateTotalExpenses(user);
        return income.subtract(expenses);
    }

    /**
     * Calcula o total de receitas em um período
     */
    public BigDecimal calculateIncomeByPeriod(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.calculateIncomeByPeriod(user, startDate, endDate);
    }

    /**
     * Calcula o total de despesas em um período
     */
    public BigDecimal calculateExpensesByPeriod(User user, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.calculateExpensesByPeriod(user, startDate, endDate);
    }

    /**
     * Calcula o total de despesas por categoria de um usuário
     */
    public Map<String, BigDecimal> calculateExpensesByCategory(User user) {
        Map<String, BigDecimal> expensesByCategory = new LinkedHashMap<>();
        List<Transaction> transactions = findByUser(user);
        
        for (Transaction transaction : transactions) {
            // Apenas contar despesas
            if (transaction.getType() == Transaction.TransactionType.DESPESA) {
                String categoryName = transaction.getCategory().getName();
                BigDecimal currentAmount = expensesByCategory.getOrDefault(categoryName, BigDecimal.ZERO);
                expensesByCategory.put(categoryName, currentAmount.add(transaction.getAmount()));
            }
        }
        
        return expensesByCategory;
    }
}
