package com.example.waridh_expbook;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the class for the user adding a new activity. The goal is to send a new object of the
 * Expense class back to the main activity.
 */
public class NewEntryActivity extends AppCompatActivity {
    /**
     * Default constructor for AppCompatActivity. All Activities must have a default constructor
     * for API 27 and lower devices or when using the default
     * {@link AppComponentFactory}.
     */

    /* Keeping track of all fields so that we can take them in when the user decides to submit */
    private Button addEntryButton;

    /**
     * The entry method for the new entry page.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * The android onCreate.
         */
        super.onCreate(savedInstanceState); // The super constructor
        setContentView(R.layout.activity_new_entry);

        /* Binding the UI elements to objects */
        this.addEntryButton = findViewById(R.id.add_entry_button);
    }

    /**
     * This method gets called when the user clicks on the add entry button. It has to do field
     * checks, and after doing so, determines if the data can be sent back to the main activity.
     */
    public void addEntry() {

    }
}
