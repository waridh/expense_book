/*
 * BaseActivity
 *
 * 2023/09/29
 *
 *
 */
package com.example.waridh_expbook;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

/**
 * This is the super class to all the activities in the project. Needed so that all activities
 * inherits the ability to display a fragment. Also includes methods that lets expenses be wrapped
 * and unwrapped for communication with other activities and fragments along with a check for
 * user input and the constraint.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final ExpBookActivityResult<Intent, ActivityResult> activityLauncher
            = ExpBookActivityResult.registerActivityForResult(this);

    /* Setting the commands for unpacking intents */
    public static final String ARG_DETAILED_EXPENSE = "entry";
    public static final String ARG_RETURNED_EXPENSE = "entry2";
    public static final String ARG_EXPENSE_LIST_COMMAND = "expense_command";
    public static final byte LIFE_CODE = (byte) 1;
    public static final byte DEAD_CODE = (byte) -1;

    /**
     * This method is for bundling an expense into a bundle item, then returning it
     * @param expense is the Expense object that is being bundled
     * @param key is the string key being used to hold the object in the bundle
     * @return A new Bundle object that now holds the expense
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
     * @param key is the string key being used to hold the object in the bundle
     * @return The Expense object that is stored in the intent.
     */
    public static Expense extractExpense(Intent intent, String key) {
        return (Expense) Objects.requireNonNull(intent.getExtras()).getSerializable(key);
    }

    /**
     * This method extracts the Expense object out of a bundle
     *
     * @param bundle The bundle that holds the object of desire
     * @param key is the string key being used to hold the object in the bundle
     * @return The Expense object that is required
     */
    public static Expense extractExpense(Bundle bundle, String key) {
        return (Expense) Objects.requireNonNull(bundle).getSerializable(key);
    }

    /**
     * This method checks if an edit text is blank or not. Blank means either empty, null, or only
     * white space.
     * @param et This is the edit text box that is being checked.
     * @return A boolean confirm if et is blank.
     */
    public static boolean isBlank(EditText et) {return et.getText().toString().trim().isEmpty();}

    /**
     * This method will only return true when all the required user input constraints are met.
     * The nameEt must be 15 characters or less
     * The month started must be in the format of yyyy-mm, we check for valid month, but not year
     * The monthly charge has to be a positive canadian dollar value. Enforces that the field is not
     * empty.
     * @param nameEt The name field edit text box. Just need to check character length limit.
     * @param monthStartedEt The month started has length check and valid month check.
     * @param monthlyChargeEt The monthly charge edit text being tested
     * @return True if all the edit texts follow the constraint, false otherwise
     */
    public static boolean checkFields(
            EditText nameEt, EditText monthStartedEt, EditText monthlyChargeEt) {
        boolean returnValue = true;
        String nameS, monthStartedS;
        /* This is the check to make sure that the name field is not empty */
        nameS = nameEt.getText().toString().trim();
        if (isBlank(nameEt)) {
            nameEt.setError("This field is required");
            returnValue = false;
        } else if (nameS.length() > 15) {
            nameEt.setError("Over the character limit of 15");
            returnValue = false;
        }

        /* Checking the month started formatting */
        monthStartedS = monthStartedEt.getText().toString().trim();
        if (monthStartedS.isEmpty()) {
            monthStartedEt.setError("This field is required");
            returnValue = false;
        }
        else if (
                !Expense.monthStartedCheck(
                        monthStartedS
                )) {
            monthStartedEt.setError(
                    "The input date is not valid. Please enter a real date.");
            returnValue = false;
        }

        /* Dealing with the money input value. No need to digit test since that is UI locked */
        if (isBlank(monthlyChargeEt)) {
            monthlyChargeEt.setError("This field is required");
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * This method displays the fragment. This overload will also send an expense to the dialog box
     * @param entry is the Expense object that will be displayed on the fragment.
     * @param operationMode is the enum that will tell the fragment if it is in add or edit mode.
     * @param tag The tag
     */
    protected void displayFragment(
            Expense entry, EditEntryFragment.OpMode operationMode, String tag) {
        EditEntryFragment eeFragment = EditEntryFragment.newInstance(
                entry, operationMode
        );
        /* Beginning fragment transaction */
        startFragmentTransaction(eeFragment, tag);
    }

    /**
     * This method displays the fragment. This overload will just show empty edit texts
     * @param operationMode is an enum that will tell the fragment if it is in add or edit mode.
     * @param tag the tag
     */
    protected void displayFragment(EditEntryFragment.OpMode operationMode, String tag) {
        EditEntryFragment eeFragment = EditEntryFragment.newInstance(operationMode);
        /* Beginning fragment transaction */
        startFragmentTransaction(eeFragment, tag);
    }

    /**
     * This method starts the dialog fragment with a tag. The dialog fragment class has to be
     * instantiated already.
     * @param fragment The dialog fragment that will be rendered
     * @param tag The tag
     */
    private void startFragmentTransaction(DialogFragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment.show(fragmentManager, tag);
    }
}
