package com.example.waridh_expbook;

import java.util.ArrayList;

public class Expense {
    public enum ConstraintCheck {
        NAME,
        MONTH_STARTED,
        MONTHLY_CHARGE,
        COMMENT,
        PASS
    }
    private String name;
    private String monthStarted;
    private String monthlyCharge;

    /* Comment check. Relying on the Null value might result in bugs later, so I
    * will be using a flag. */
    private boolean commentFlag;
    private Comment comment;

    /**
     * Base constructor.
     */
    public Expense() {
        this.commentFlag = false;   // This value should always be true
    }
    public Expense(String name, String monthStarted, String charge) {
        /**
         * Constructor requires the name, month started, and monthly charge
         */

        this.commentFlag = false;
        this.name = name;
        this.monthStarted = monthStarted;
        this.monthlyCharge = charge;
    }

    public Expense(String name, String monthStarted, String charge, String comment) {
        /**
         * Constructor requires the name, month started, and monthly charge
         */
        this.name = name;
        this.monthStarted = monthStarted;
        this.monthlyCharge = charge;
        this.commentFlag = true;
        this.comment = new Comment(comment);
    }

    public String getMonthStarted() {
        return monthStarted;
    }

    public String getMonthlyCharge() {
        return monthlyCharge;
    }

    /**
     * This method returns the comment that is stored in this expense entry. If it doesn't exist
     * then the method will return null.
     * @return Either the stored comment, or null.
     */
    public Comment getComment() {
        if (this.commentFlag) return comment;
        else return null;   // Going to get rid of this null soon.
    }

    /**
     * This method takes in data and does checks on if the input is of the correct format.
     * @return An ArrayList with the ConstraintCheck enums. So that the program would know which
     * fields are of incorrect format.
     */
    public ArrayList<ConstraintCheck> add() {
        ArrayList<ConstraintCheck> constraintList = new ArrayList<ConstraintCheck>();
        constraintList.add(ConstraintCheck.PASS);
        return constraintList;
    }

    /**
     * This method checks if the user input name follows the constraint.
     * @param name This is the string under test
     * @return a boolean representing if the string is following constraint or not.
     */
    public boolean nameCheck(String name) {
        return name.length() <= 15;
    }

    /**
     * This is the method that will check if the month started input field is following the set
     * constraint.
     * @return a boolean that is true when it is following constraint, and false when not.
     */
    public boolean monthStartedCheck(String monthStarted) {
        if (monthStarted.matches("^[0-9]{4}-[0-9]{2}$")) {
            int year, month;
            String[] tokenized = monthStarted.split("-");
            year = Integer.parseInt(tokenized[0]);
            month = Integer.parseInt(tokenized[1]);
            /* This blocks out invalid months and years. */
            if ((0 < month && month < 13) && (0 < year)) return true;
            else return false;
        }
        else return false;
    }

    public boolean monthlyChargeCheck() {
        return false;
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
