package com.financasplus.controller;

import com.financasplus.model.Category;
import com.financasplus.model.Transaction;
import com.financasplus.model.User;
import com.financasplus.service.CategoryService;
import com.financasplus.service.TransactionService;
import com.financasplus.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller para gerenciar transações (receitas e despesas)
 */
@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Lista todas as transações do usuário
     */
    @GetMapping
    public String listTransactions(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Inicializar categorias padrão se não existirem
        categoryService.initializeDefaultCategories();

        // Buscar usuário atualizado do banco
        Optional<User> userOpt = userService.findById(user.getId());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User currentUser = userOpt.get();
        List<Transaction> transactions = transactionService.findByUser(currentUser);
        List<Category> categories = categoryService.findAll();

        model.addAttribute("user", currentUser);
        model.addAttribute("transactions", transactions);
        model.addAttribute("categories", categories);

        return "transactions";
    }

    /**
     * Página para adicionar nova transação
     */
    @GetMapping("/add")
    public String addTransactionPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Inicializar categorias padrão se não existirem
        categoryService.initializeDefaultCategories();

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", Transaction.TransactionType.values());

        return "add-transaction";
    }

    /**
     * Processa a criação de uma nova transação
     */
    @PostMapping("/add")
    public String createTransaction(
            @RequestParam String description,
            @RequestParam BigDecimal amount,
            @RequestParam Long categoryId,
            @RequestParam String type,
            @RequestParam String date,
            HttpSession session,
            Model model) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            // Buscar usuário atualizado
            Optional<User> userOpt = userService.findById(user.getId());
            if (userOpt.isEmpty()) {
                return "redirect:/login";
            }

            // Buscar categoria
            Optional<Category> categoryOpt = categoryService.findById(categoryId);
            if (categoryOpt.isEmpty()) {
                model.addAttribute("error", "Categoria não encontrada!");
                return "add-transaction";
            }

            // Criar transação
            Transaction transaction = new Transaction();
            transaction.setUser(userOpt.get());
            transaction.setCategory(categoryOpt.get());
            transaction.setDescription(description);
            transaction.setAmount(amount);
            transaction.setType(Transaction.TransactionType.valueOf(type));
            transaction.setDate(LocalDateTime.parse(date + "T00:00:00"));

            transactionService.createTransaction(transaction);

            return "redirect:/transactions";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar transação: " + e.getMessage());
            return "add-transaction";
        }
    }

    /**
     * Página para editar uma transação
     */
    @GetMapping("/edit/{id}")
    public String editTransactionPage(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Inicializar categorias padrão se não existirem
        categoryService.initializeDefaultCategories();

        Optional<Transaction> transactionOpt = transactionService.findById(id);
        if (transactionOpt.isEmpty()) {
            return "redirect:/transactions";
        }

        List<Category> categories = categoryService.findAll();
        model.addAttribute("transaction", transactionOpt.get());
        model.addAttribute("categories", categories);
        model.addAttribute("transactionTypes", Transaction.TransactionType.values());

        return "edit-transaction";
    }

    /**
     * Processa a atualização de uma transação
     */
    @PostMapping("/edit/{id}")
    public String updateTransaction(
            @PathVariable Long id,
            @RequestParam String description,
            @RequestParam BigDecimal amount,
            @RequestParam Long categoryId,
            @RequestParam String type,
            @RequestParam String date,
            HttpSession session,
            Model model) {

        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return "redirect:/login";
            }

            Optional<Category> categoryOpt = categoryService.findById(categoryId);
            if (categoryOpt.isEmpty()) {
                model.addAttribute("error", "Categoria não encontrada!");
                return "edit-transaction";
            }

            Transaction transactionDetails = new Transaction();
            transactionDetails.setDescription(description);
            transactionDetails.setAmount(amount);
            transactionDetails.setCategory(categoryOpt.get());
            transactionDetails.setType(Transaction.TransactionType.valueOf(type));
            transactionDetails.setDate(LocalDateTime.parse(date + "T00:00:00"));

            transactionService.updateTransaction(id, transactionDetails);

            return "redirect:/transactions";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao atualizar transação: " + e.getMessage());
            return "edit-transaction";
        }
    }

    /**
     * Deleta uma transação
     */
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        transactionService.deleteTransaction(id);
        return "redirect:/transactions";
    }
}
