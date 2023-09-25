package com.example.waridh_expbook;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * All sub activity of the main activity has to be able to navigate back. This is a nice abstraction
 * to let that happen.
 */
public abstract class SubActivity extends AppCompatActivity {
    public void returnToMain(View view) {
        finish();
    }
}
