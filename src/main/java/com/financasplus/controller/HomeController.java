package com.financasplus.controller;

import com.financasplus.model.User;
import com.financasplus.service.CategoryService;
import com.financasplus.service.TransactionService;
import com.financasplus.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Controller para gerenciar as páginas iniciais (Home, Login, Cadastro)
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Página inicial (redireciona para login se não autenticado)
     */
    @GetMapping("/")
    public String index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "redirect:/dashboard";
        }
        return "redirect:/login";
    }

    /**
     * Página de login
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Processa o login do usuário
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, 
                              HttpSession session, Model model) {
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Validação simples de senha (em produção, usar bcrypt)
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                // Inicializar categorias padrão após login
                categoryService.initializeDefaultCategories();
                return "redirect:/dashboard";
            }
        }

        model.addAttribute("error", "Username ou senha inválidos!");
        return "login";
    }

    /**
     * Página de cadastro
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Processa o cadastro de novo usuário
     */
    @PostMapping("/register")
    public String processRegister(@RequestParam String username, @RequestParam String email,
                                 @RequestParam String password, @RequestParam String fullName,
                                 Model model) {
        try {
            // Validações básicas
            if (username == null || username.trim().isEmpty()) {
                model.addAttribute("error", "Username é obrigatório!");
                return "register";
            }
            if (email == null || email.trim().isEmpty()) {
                model.addAttribute("error", "Email é obrigatório!");
                return "register";
            }
            if (password == null || password.trim().isEmpty()) {
                model.addAttribute("error", "Senha é obrigatória!");
                return "register";
            }

            // Criar novo usuário
            User newUser = new User(username, password, email, fullName);
            userService.createUser(newUser);

            // Inicializar categorias padrão após registro
            categoryService.initializeDefaultCategories();

            model.addAttribute("success", "Cadastro realizado com sucesso! Faça login para continuar.");
            return "register";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    /**
     * Página do Dashboard
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Inicializar categorias padrão se não existirem
        categoryService.initializeDefaultCategories();

        // Buscar dados do usuário do banco de dados
        Optional<User> userOpt = userService.findById(user.getId());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User currentUser = userOpt.get();
        session.setAttribute("user", currentUser);

        // Calcular totais
        BigDecimal totalIncome = transactionService.calculateTotalIncome(currentUser);
        BigDecimal totalExpenses = transactionService.calculateTotalExpenses(currentUser);
        BigDecimal balance = transactionService.calculateBalance(currentUser);

        // Calcular gastos por categoria
        java.util.Map<String, BigDecimal> expensesByCategory = transactionService.calculateExpensesByCategory(currentUser);
        java.util.List<String> categoryNames = new java.util.ArrayList<>(expensesByCategory.keySet());
        java.util.List<BigDecimal> categoryValues = new java.util.ArrayList<>(expensesByCategory.values());

        // Adicionar dados ao modelo
        model.addAttribute("user", currentUser);
        model.addAttribute("totalIncome", totalIncome);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("balance", balance);
        model.addAttribute("transactions", transactionService.findByUser(currentUser));
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("categoryValues", categoryValues);
        model.addAttribute("incomeAmount", totalIncome.doubleValue());
        model.addAttribute("expenseAmount", totalExpenses.doubleValue());

        return "dashboard";
    }

    /**
     * Logout do usuário
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
