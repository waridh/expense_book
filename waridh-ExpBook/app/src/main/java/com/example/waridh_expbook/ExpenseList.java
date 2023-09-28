package com.example.waridh_expbook;

import java.util.ArrayList;

/**
 * This class is designed to hold the expenses. Also has methods that will help quickly add and edit
 * the fields of the expense.
 */
public class ExpenseList {
    private ArrayList<Expense> expenses;    // This is out internal data structure for all the rows

    /**
     * The constructor for the object. Initializes the ArrayList.
     */
    public ExpenseList() {this.expenses = new ArrayList<Expense>();}

    /**
     * Returns the amount of elements being currently stored in ExpenseList.
     * @return The amount of elements.
     */
    public int size() {return expenses.size();}

    /**
     * The addition of a new row
     * @param row
     */
    public void add(Expense row) { this.expenses.add(row);}

    public Expense get(int i) {
        return expenses.get(i);
    }

    public String getMonthlyCharge(int index) {
        return (expenses.get(index)).getMonthlyCharge();
    }

    public String getMonthlyChargeNice(int index) {
        return (expenses.get(index)).getMonthlyChargeNice();
    }

    public String getMonthStarted(int index) {
        return (expenses.get(index)).getMonthStarted();
    }

    /**
     * This method returns the name of the expense stored at the input index
     * @param index The index that is being read
     * @return the name in String of the expense
     */
    public String getName(int index) {return (expenses.get(index)).getName();}

    /**
     * Setter. Swaps the value at the specified index with the new Expense object.
     * @param index The index in the list that the value is being changed.
     * @param newExpense The new Expense object that is replacing the object at the index.
     */
    public void set(int index, Expense newExpense) {expenses.set(index, newExpense);}

    /**
     * This is the remove method. Needed for removing entries.
     * @param index The index of the expense that is being removed.
     */
    public void remove(int index) {expenses.remove(index);}

}
