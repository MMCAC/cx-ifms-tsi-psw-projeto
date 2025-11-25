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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

/**
 * Controller para gerenciar relatórios financeiros
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Página de relatórios
     */
    @GetMapping
    public String reports(HttpSession session, 
                         @RequestParam(required = false) String month,
                         Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // Buscar usuário atualizado
        Optional<User> userOpt = userService.findById(user.getId());
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }

        User currentUser = userOpt.get();
        List<Transaction> transactions = transactionService.findByUser(currentUser);

        // Definir mês padrão como mês atual
        if (month == null || month.isEmpty()) {
            month = YearMonth.now().toString();
        }

        YearMonth yearMonth = YearMonth.parse(month);
        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        // Filtrar transações do mês
        List<Transaction> monthTransactions = transactions.stream()
                .filter(t -> t.getDate().isAfter(startDate) && t.getDate().isBefore(endDate))
                .collect(Collectors.toList());

        // Calcular totais do mês
        BigDecimal monthIncome = transactionService.calculateIncomeByPeriod(currentUser, startDate, endDate);
        BigDecimal monthExpenses = transactionService.calculateExpensesByPeriod(currentUser, startDate, endDate);
        BigDecimal monthBalance = monthIncome.subtract(monthExpenses);

        // Agrupar despesas por categoria
        Map<String, BigDecimal> expensesByCategory = new LinkedHashMap<>();
        List<Category> categories = categoryService.findAll();

        for (Category category : categories) {
            BigDecimal categoryTotal = monthTransactions.stream()
                    .filter(t -> t.getCategory().getId().equals(category.getId()) && 
                               t.getType() == Transaction.TransactionType.DESPESA)
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (categoryTotal.compareTo(BigDecimal.ZERO) > 0) {
                expensesByCategory.put(category.getName(), categoryTotal);
            }
        }

        // Preparar dados para o gráfico
        List<String> categoryNames = new ArrayList<>(expensesByCategory.keySet());
        List<BigDecimal> categoryValues = new ArrayList<>(expensesByCategory.values());

        model.addAttribute("user", currentUser);
        model.addAttribute("currentMonth", month);
        model.addAttribute("monthIncome", monthIncome);
        model.addAttribute("monthExpenses", monthExpenses);
        model.addAttribute("monthBalance", monthBalance);
        model.addAttribute("monthTransactions", monthTransactions);
        model.addAttribute("expensesByCategory", expensesByCategory);
        model.addAttribute("categoryNames", categoryNames);
        model.addAttribute("categoryValues", categoryValues);
        model.addAttribute("monthIncomeAmount", monthIncome.doubleValue());
        model.addAttribute("monthExpenseAmount", monthExpenses.doubleValue());

        return "reports";
    }
}
