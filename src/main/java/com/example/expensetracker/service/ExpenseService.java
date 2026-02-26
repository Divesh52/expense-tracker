package com.example.expensetracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.ExpenseRepository;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }

    // NEW: Get expenses for a specific date
    public List<Expense> getExpensesByDate(LocalDate date) {
        return expenseRepository.findByExpenseDate(date);
    }

    // NEW: Daily summary for charts
    public List<Map<String, Object>> getDailyExpenseSummary() {
        List<Object[]> results = expenseRepository.getDailyExpenseSummary();
        List<Map<String, Object>> summaryList = new ArrayList<>();

        for (Object[] row : results) {
            LocalDate date = (LocalDate) row[0];
            BigDecimal total = (BigDecimal) row[1];

            Map<String, Object> map = new HashMap<>();
            map.put("date", date);
            map.put("total", total);

            summaryList.add(map);
        }

        return summaryList;
    }
}