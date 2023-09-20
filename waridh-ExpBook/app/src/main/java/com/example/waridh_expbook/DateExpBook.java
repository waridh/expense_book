package com.example.waridh_expbook;

public class DateExpBook {
    private String date;

    public DateExpBook(String userInput) {
        /**
         * Constructor for a date
         */
        this.date = userInput;
    }

    public String getDate() {
        /**
         * Returns the date stored in the object
         */
        return this.date;
    }

}
