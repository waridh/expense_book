package com.example.waridh_expbook;

public class Comment {
    /**
     * This is the comment class. Decomposed for better modularity.
     */
    private String content;
    public Comment(String userInput) {
        /**
         * Constructor for the comment
         */
        this.content = userInput;
    }

    public String getComment() {
        /**
         * This is the getter for the 20 character content in the thing
          */
        return this.content;
    }
}
