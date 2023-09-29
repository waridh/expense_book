package com.example.waridh_expbook;

import java.io.Serializable;

public class Expense implements Serializable {
    private String name;
    private int month, year;
    private float monthlyCharge;

    /* Comment check. Relying on the Null value might result in bugs later, so I
    * will be using a flag. */
    private final boolean commentFlag;
    private String comment;

    /**
     * Base constructor.
     */
    public Expense() {this.commentFlag = false;}

    /**
     * Constructor requires the name, month started, and monthly charge
     */
    public Expense(String name, String monthStarted, String charge) {
        this.commentFlag = false;
        this.name = name;
        setMonthStarted(monthStarted);
        setMonthlyCharge(charge);
    }

    /**
     * Constructor requires the name, month started, and monthly charge
     */
    public Expense(String name, String monthStarted, String charge, String comment) {
        this.name = name;
        setMonthStarted(monthStarted);
        setMonthlyCharge(charge);
        this.commentFlag = true;
        this.comment = comment;
    }

    /**
     * This method generates the proper new expense based on if the user has given
     * a comment. The assumption is that this function gets called after the input check has
     * completed.
     * @return An Expense object constructed with or without the comment field.
     */
    public static Expense newInstance(
            String nameText,
            String monthStartedText,
            String chargeText,
            String commentText
            ) {
        Expense retExpense; // Declaration of the output
        if (commentCheck(commentText)) {
            retExpense = new Expense(nameText, monthStartedText, chargeText, commentText);
        } else {
            retExpense = new Expense(nameText, monthStartedText, chargeText);   // no comment
        }
        return retExpense;
    }

    /**
     * Getter for the month started. Since string, a new object does not need to be created due to
     * immutability.
     * @return the string representing the month started.
     */
    public String getMonthStarted() { return String.format("%04d-%02d", year, month); }

    /**
     * Returns the monthly charge
     * @return Returns the monthly charge in String.
     */
    public String getMonthlyCharge() {
        return String.format("%.2f", monthlyCharge); }

    public String getMonthlyChargeNice() {
        return String.format("$%8.2f", monthlyCharge); }

    /**
     * This method returns the dollar amount of the monthly expense as a float
     * @return the dollar value as a float
     */
    public Float getMonthlyChargeFloat() {
        return monthlyCharge;
    }

    /**
     * This method returns the comment that is stored in this expense entry. If it doesn't exist
     * then the method will return null.
     * @return Either the stored comment, or null.
     */
    public String getComment() {
        if (this.commentFlag) return comment;
        else return null;   // Going to get rid of this null soon.
    }

    /**
     * A more elegant way to let other classes know if this entry has a comment or not.
     * @return A boolean representing if the expense class has a comment or not. True if there is
     * a comment, and false if there is no comment.
     */
    public boolean getCommentFlag() {
        return commentFlag;
    }

    /**
     * This method checks if the user input name follows the constraint.
     * @param name This is the string under test
     * @return a boolean representing if the string is following constraint or not.
     */
    public static boolean nameCheck(String name) {
        if (name == null) return false;
        return (name.length() <= 15 && name.length() > 0);
    }

    /**
     * This method takes in the monthly charge in String and then converts it to float, as that is
     * how the value is being stored. Also places it in the instance variable.
     * @param money The string representing the value of the monthly expense.
     */
    private void setMonthlyCharge(String money) {
        this.monthlyCharge = Float.parseFloat(money);
    }

    /**
     * Stores the month started in the instance variable. Since we are storing both year and month
     * separately so that we could take in months of just single digits (both 6 and 06), we need to
     * convert the input string into integers.
     * @param s The "yyyy-mm" format month started input.
     */
    private void setMonthStarted(String s) {
        String[] intermediate = s.split("-");
        year = Integer.parseInt(intermediate[0]);
        month = Integer.parseInt(intermediate[1]);
    }

    /**
     * This is the character length check for the comments.
     * @param comment The comment under check
     * @return boolean that says if the string is under the length constraint given.
     */
    public static boolean commentCheck(String comment) {
        if (comment == null) return false;
        else return comment.length() <= 20 && !(comment.trim().isEmpty());
    }

    /**
     * This is the method that will check if the month started input field is following the set
     * constraint.
     * @return a boolean that is true when it is following constraint, and false when not.
     */
    public static boolean monthStartedCheck(String date) {
        if (date.matches("^[0-9]{4}-[0-9]{1,2}$")) {
            int year, month;
            String[] tokenized = date.split("-");
            year = Integer.parseInt(tokenized[0]);
            month = Integer.parseInt(tokenized[1]);
            /* This blocks out invalid months and years. */
            return (0 < month && month < 13) && (0 < year);
        }
        else return false;
    }

    /**
     * This method will check if the monthly expense is correct
     * @return returns true when the monthlyChargeInput matches the constraint
     */
    public static boolean monthlyChargeCheck(String charge) {
        /* regex for checking if the user input is a number. */
        if (charge.matches(
                        "^[0-9]+\\.[0-9]{2}$"
                ) || charge.matches(
                        "^[0-9]+$"
                )) {
            float value;
            value = Float.parseFloat(charge);
            /* This blocks out invalid months and years. */
            return 0.0 <= value;
        }
        else return false;
    }

    /**
     * This method returns the name
     * @return the name of the expense as a string
     */
    public String getName() {
        return this.name;
    }
}
