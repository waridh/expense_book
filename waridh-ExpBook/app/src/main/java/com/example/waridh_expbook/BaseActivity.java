package com.example.waridh_expbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity {
    protected final ExpBookActivityResult<Intent, ActivityResult> activityLauncher
            = ExpBookActivityResult.registerActivityForResult(this);

    /* Setting the commands for unpacking intents */
    public static final String ARG_DETAILED_EXPENSE = "entry";
    public static final String ARG_RETURNED_EXPENSE = "entry";
    public static final String ARG_EXPENSE_LIST_COMMAND = "expense_command";

    /**
     * This method is for bundling an expense into a bundle item, then returning it
     * @param expense
     * @return
     */
    public static Bundle bundleExpense(Expense expense, String key) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, expense);
        return bundle;
    }

    /**
     * This method gets the singular expense object out of the intent. We don't plan on moving
     * around more than one object at a time at this moment.
     * @param intent Any intent object that has an Expense object stored within.
     * @return The Expense object that is stored in the intent.
     */
    public static Expense extractExpense(Intent intent, String key) {
        return (Expense) Objects.requireNonNull(intent.getExtras()).getSerializable(key);
    }

    public static Expense extractExpense(Bundle bundle, String key) {
        return (Expense) Objects.requireNonNull(bundle).getSerializable(key);
    }

}
