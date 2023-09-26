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

    public static final String ARG_DETAILED_EXPENSE = "entry";

    /**
     * This method is for bundling an expense into a bundle item, then returning it
     * @param expense
     * @return
     */
    public static Bundle bundleExpense(Expense expense) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DETAILED_EXPENSE, expense);
        return bundle;
    }

    /**
     * This is an overload that will allow the method to also add on to a bundle
     * @param expense The Expense object that is being added to the transfer.
     * @param bundle The bundle that Expense object is being added to.
     * @return The bundle with the Expense objected added to it.
     */
    public static Bundle bundleExpense(Expense expense, Bundle bundle) {
        bundle.putSerializable(ARG_DETAILED_EXPENSE, expense);
        return bundle;
    }

    /**
     * This method gets the singular expense object out of the intent. We don't plan on moving
     * around more than one object at a time at this moment.
     * @param intent Any intent object that has an Expense object stored within.
     * @return The Expense object that is stored in the intent.
     */
    public static Expense extractExpense(Intent intent) {
        return (Expense) Objects.requireNonNull(intent.getExtras()).getSerializable(ARG_DETAILED_EXPENSE);
    }

}
