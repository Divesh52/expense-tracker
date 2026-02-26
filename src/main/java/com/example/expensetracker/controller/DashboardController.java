package com.example.expensetracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.repository.ExpenseRepository;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @GetMapping("/category-summary")
    public List<Object[]> getCategorySummary() {
        return expenseRepository.getTotalAmountByCategory();
    }

    @GetMapping("/monthly-summary")
    public List<Object[]> getMonthlySummary() {
        return expenseRepository.getMonthlyExpenses();
    }
}