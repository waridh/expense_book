package com.example.waridh_expbook;

import android.app.AppComponentFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NewEntryActivity extends AppCompatActivity {
    /**
     * Default constructor for AppCompatActivity. All Activities must have a default constructor
     * for API 27 and lower devices or when using the default
     * {@link AppComponentFactory}.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * The android onCreate.
         */
        super.onCreate(savedInstanceState); // The super constructor
        setContentView(R.layout.activity_new_entry);
    }

    public void returnToMain() {
        /**
         * This method should be sending the user back to the main page.
         */
        finish();   // This is all it takes to remove the activity from the stack. No persistence.
    }



}
