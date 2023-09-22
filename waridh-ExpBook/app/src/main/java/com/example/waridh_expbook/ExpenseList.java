package com.example.waridh_expbook;

import java.util.ArrayList;

/**
 * This class is designed to hold the expenses. Also has methods that will help quickly add and edit
 * the fields of the expense.
 */
public class ExpenseList {
    private ArrayList<Expense> expenses;    // This is out internal data structure for all the rows
    public ExpenseList() {
        this.expenses = new ArrayList<Expense>();
    }

    public int size() {
        return expenses.size();
    }

    public void add(Expense row) {
        this.expenses.add(row);
    }

    public Expense get(int i) {
        return expenses.get(i);
    }

    public String getMonthlyCharge(int index) {
        return (expenses.get(index)).getMonthlyCharge();
    }

    public String getMonthStarted(int index) {
        return (expenses.get(index)).getMonthStarted();
    }

    public String getName(int index) {
        return (expenses.get(index)).getName();
    }

    /**
     * The getter for the array list. Since the class is not fully constructed yet, this is needed.
     * @return The ArrayList holding the expenses.
     */
    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }
}
