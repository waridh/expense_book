package com.example.waridh_expbook;

import java.io.Serializable;
import java.util.ArrayList;

public class Expense implements Serializable {
    private String name;
    private String monthStarted;
    private String monthlyCharge;

    /* Comment check. Relying on the Null value might result in bugs later, so I
    * will be using a flag. */
    private boolean commentFlag;
    private String comment;

    /**
     * Base constructor.
     */
    public Expense() {
        this.commentFlag = false;   // This value should always be true
    }

    /**
     * Constructor requires the name, month started, and monthly charge
     */
    public Expense(String name, String monthStarted, String charge) {
        this.commentFlag = false;
        this.name = name;
        this.monthStarted = monthStarted;
        this.monthlyCharge = charge;
    }

    /**
     * Constructor requires the name, month started, and monthly charge
     */
    public Expense(String name, String monthStarted, String charge, String comment) {
        this.name = name;
        this.monthStarted = monthStarted;
        this.monthlyCharge = charge;
        this.commentFlag = true;
        this.comment = comment;
    }

    /**
     * Getter for the month started. Since string, a new object does not need to be created due to
     * immutability.
     * @return the string representing the month started.
     */
    public String getMonthStarted() { return monthStarted; }

    public String getMonthlyCharge() { return monthlyCharge; }

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
     * This is the character length check for the comments.
     * @param comment The comment under check
     * @return boolean that says if the string is under the length constraint given.
     */
    public static boolean commentCheck(String comment) {
        return (comment.length() <= 20 && comment.length() > 0);
    }

    /**
     * This is the method that will check if the month started input field is following the set
     * constraint.
     * @return a boolean that is true when it is following constraint, and false when not.
     */
    public static boolean monthStartedCheck(String date) {
        if (date.matches("^[0-9]{4}-[0-9]{2}$")) {
            int year, month;
            String[] tokenized = date.split("-");
            year = Integer.parseInt(tokenized[0]);
            month = Integer.parseInt(tokenized[1]);
            /* This blocks out invalid months and years. */
            if ((0 < month && month < 13) && (0 < year)) return true;
            else return false;
        }
        else return false;
    }

    /**
     * This method will check if the monthly expense is correct
     * @return
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
            if (0.0 <= value) return true;
            else return false;
        }
        else return false;
    }

    public String getName() {
        return this.name;
    }

    public void setComment(String s) {
        this.commentFlag = true;    // The comment flag is not true since we are getting new comment
    }

    /**
     * This method will delete the comment that is attached to the expense.
     */
    public void deleteComment() {
        this.commentFlag = false;
        this.comment = null;
    }
}
