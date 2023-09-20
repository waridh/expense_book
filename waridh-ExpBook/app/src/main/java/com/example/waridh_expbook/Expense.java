package com.example.waridh_expbook;

public class Expense {
    private String name;
    private DateExpBook monthStarted;
    private CanadianDollar monthlyCharge;

    /* Comment check. Relying on the Null value might result in bugs later, so I
    * will be using a flag. */
    private boolean commentFlag;
    private Comment comment;

    public Expense(String name, String monthStarted, String charge) {
        /**
         * Constructor requires the name, month started, and monthly charge
         */

        this.commentFlag = false;
        this.name = name;
        this.monthStarted = new DateExpBook(monthStarted);
        this.monthlyCharge = new CanadianDollar(charge);
    }

    public Expense(String name, String monthStarted, String charge, String comment) {
        /**
         * Constructor requires the name, month started, and monthly charge
         */
        this.name = name;
        this.monthStarted = new DateExpBook(monthStarted);
        this.monthlyCharge = new CanadianDollar(charge);
        this.commentFlag = true;
        this.comment = new Comment(comment);
    }

    public String getMonthStarted() {
        return monthStarted.getDate();
    }

    public String getMonthlyCharge() {
        return monthlyCharge.getValue();
    }

    public Comment getComment() {
        return comment;
    }

    public String getName() {
        return this.name;
    }
}
