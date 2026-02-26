package com.example.expensetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.expensetracker.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e.expenseDate, SUM(e.amount) " +
           "FROM Expense e " +
           "GROUP BY e.expenseDate " +
           "ORDER BY e.expenseDate")
    List<Object[]> getDailyExpenseSummary();

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> getTotalAmountByCategory();

    @Query("SELECT MONTH(e.expenseDate), SUM(e.amount) FROM Expense e GROUP BY MONTH(e.expenseDate)")
    List<Object[]> getMonthlyExpenses();
    List<Expense> findByExpenseDate(LocalDate expenseDate);
}