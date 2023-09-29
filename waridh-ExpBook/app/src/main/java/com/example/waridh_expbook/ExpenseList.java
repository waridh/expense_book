package com.example.waridh_expbook;

import java.util.ArrayList;

/**
 * This class is designed to hold the expenses. Also has methods that will help quickly add and edit
 * the fields of the expense.
 */
public class ExpenseList {
    private final ArrayList<Expense> expenses;    // This is out internal data structure for all the rows

    /**
     * The constructor for the object. Initializes the ArrayList holding the data.
     */
    public ExpenseList() {this.expenses = new ArrayList<>();}

    /**
     * Returns the amount of elements being currently stored in ExpenseList.
     * @return The amount of elements.
     */
    public int size() {return expenses.size();}

    /**
     * The addition of a new row
     * @param row is an Expense object that is being appended to the list.
     */
    public void add(Expense row) { this.expenses.add(row);}

    /**
     * This method retrieves the object from the i (index) parameter that is stored.
     * @param i is the index that the object is being retrieved from.
     * @return the Expense object at the index. null is returned if it is not there.
     */
    public Expense get(int i) {
        if (i < expenses.size()) return expenses.get(i);
        else return null;}

    public String getSum() {
        float sum = 0;
        for (Expense e : expenses) sum += e.getMonthlyChargeFloat();
        return String.format("$%6.2f", sum);
    }

    /**
     * This method returns a string that represents the monthly charge of the expense. Has a dollar
     * sign ($).
     * @param index The index of the expense being retrieved.
     * @return The string that represents the value of the monthly charge. In this format $ XX.XX
     */
    public String getMonthlyChargeNice(int index) {
        return (expenses.get(index)).getMonthlyChargeNice();}

    /**
     * This method returns the month started in string format of the entry at the index location
     * @param index is the index of the entry that is being requested
     * @return The string of the month started in the yyyy-mm format
     */
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
