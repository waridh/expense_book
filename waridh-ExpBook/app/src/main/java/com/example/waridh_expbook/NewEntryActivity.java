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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
    }

}
