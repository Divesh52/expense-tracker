package com.example.expensetracker.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;

@Controller
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;

    // Home Page
    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Expense> expenses = expenseService.getAllExpenses();

        BigDecimal totalAmount = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);

        return "index";
    }

    // Add Expense Page
    @GetMapping("/addExpense")
    public String showAddExpensePage(Model model) {
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "add-expense";
    }

    // Save Expense
    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense) {
        expenseService.saveExpense(expense);
        return "redirect:/";
    }

    // Edit Expense Page
    @GetMapping("/editExpense/{id}")
    public String showUpdateExpensePage(@PathVariable("id") Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "update-expense";
    }

    // Update Expense
    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable("id") Long id,
                                @ModelAttribute("expense") Expense expense) {

        Expense existingExpense = expenseService.getExpenseById(id);
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setExpenseDate(expense.getExpenseDate());

        expenseService.saveExpense(existingExpense);
        return "redirect:/";
    }

    // Delete Expense
    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpenseById(id);
        return "redirect:/";
    }
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    

    // NEW: Daily Summary API (for charts)
    @GetMapping("/expenses/daily-summary")
    @ResponseBody
    public List<Map<String, Object>> getDailyExpenseSummary() {
        return expenseService.getDailyExpenseSummary();
    }

    // NEW: Filter by Date API
    @GetMapping("/expenses/by-date")
    @ResponseBody
    public List<Expense> getExpensesByDate(@RequestParam("date") String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return expenseService.getExpensesByDate(parsedDate);
    }
}