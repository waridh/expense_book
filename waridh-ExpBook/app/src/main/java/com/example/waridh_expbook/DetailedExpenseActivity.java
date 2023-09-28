package com.example.waridh_expbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

public class DetailedExpenseActivity extends SubActivity {

    /* Expense details */
    private Expense theExpense;
    private String monthlyChargeS, monthStartedS, nameS, commentS;
    private boolean commentState;

    /* Need to be able to set the text */
    private TextView monthlyChargeDtv, monthStartedDtv, nameDtv, commentDtv;
    private LinearLayout commentDv;

    /* Fragment related attributes */
    private boolean fragmentState;

    /* State controlling flags */
    private boolean editedState;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        /* Activity State control */
        this.editedState = false;

        /* Getting the contents of the intent */
        this.theExpense = extractExpense(this.getIntent(), ARG_DETAILED_EXPENSE);
        fragmentState = false;  // The fragment should not be showing immediately when you open

        loadDetails(theExpense);  // Loading the data from the Expense object

        /* Linking the UI elements to objects */
        monthlyChargeDtv = findViewById(R.id.monthly_charge_dtv);
        monthStartedDtv = findViewById(R.id.month_started_dtv);
        nameDtv = findViewById(R.id.name_dtv);
        commentDtv = findViewById(R.id.comment_dtv);
        commentDv = findViewById(R.id.comment_view);

        setDetailedTextViews();

        /* Setting up a fragment result listener */
        setupFragmentResultListener();
    }

    /**
     * This method installs the fragment result listener, meaning that when the fragment sends
     * anything, this activity will process it immediately. Updated the current expense and package
     * it for the main location.
     */
    private void setupFragmentResultListener() {
        getSupportFragmentManager().setFragmentResultListener(
                EditEntryFragment.ARG_REQUEST_KEY,
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(
                            @NonNull String requestKey, @NonNull Bundle bundle
                    ) {
                        // Going to mostly be unwrapping a bundle
                        theExpense = extractExpense(
                                bundle, EditEntryFragment.ARG_FRAG_BUNDLE_KEY);

                        /* Loading the expense object into the activity */
                        loadDetails(theExpense);
                        setDetailedTextViews();

                        /* Updating the expense in the main activity */
                        sendExpenseToMainAux(theExpense, LIFE_CODE);
                        fragmentState = false;  // Flipping the state off.
                    }
                });
    }

    /**
     * This method sends the notification that the row has been deleted to the main activity.
     */
    private void deleteExpense() {
        /* Sending the data back into the main method */
        sendExpenseToMainAux(new Expense(), DEAD_CODE);
    }

    /**
     * More general function for sending the expense back to the main
     * @param expense The expense object that is replacing the old object
     * @param liveStatus If this object is deleted or not
     */
    private void sendExpenseToMainAux(Expense expense, byte liveStatus) {
        Bundle bundle = bundleExpense(expense, ARG_RETURNED_EXPENSE);
        bundle.putByte(ARG_EXPENSE_LIST_COMMAND, liveStatus);
        sendResultToMain(bundle);
        editedState = true;
    }

    /**
     * This method loads the details from the Expense object into strings
     */
    private void loadDetails(Expense expense) {
        /* Setting the string value in case that the user will edit it */
        nameS = expense.getName();
        monthStartedS = expense.getMonthStarted();
        monthlyChargeS = expense.getMonthlyCharge();
        commentState = expense.getCommentFlag();
        /* Loads the comment only if there is a comment */
        if (expense.getCommentFlag()) commentS = expense.getComment();
        else commentS = null;
    }

    private void setDetailedTextViews() {
        monthlyChargeDtv.setText(theExpense.getMonthlyChargeNice());
        monthStartedDtv.setText(monthStartedS);
        nameDtv.setText(nameS);
        /* Need to check if there are comments or not */
        setCommentDetailedTextView(commentS);
    }

    /**
     * This method is just flow control for the comment. Hides the comment if it doesn't exist else
     * it will set the text.
     */
    private void setCommentDetailedTextView(String comment) {
        if (Expense.commentCheck(comment)) {
            commentDv.setVisibility(View.VISIBLE);
            commentDtv.setText(comment);
        }
        else commentDv.setVisibility(View.GONE);    // The view does not need to show up
    }

    private EditEntryFragment newEntryFragment() {
        if (commentState) return EditEntryFragment.newInstance(
                new Expense(
                        nameS, monthStartedS, monthlyChargeS, commentS),
                EditEntryFragment.OpMode.EDIT);
        else return EditEntryFragment.newInstance(
                new Expense(nameS, monthStartedS, monthlyChargeS),
                EditEntryFragment.OpMode.EDIT);
    }

    /**
     * This method opens the edit value fragment.
     * @param view
     */
    public void editButtonFc(View view) {
        displayFragment();
    }

    public void deleteButtonFc(View view) {
        deleteExpense();
        finish();
    }

    /**
     * This method will display the fragment
     */
    public void displayFragment() {
        EditEntryFragment eeFragment = newEntryFragment();

        /* Beginning fragment transaction */
        FragmentManager fragmentManager = getSupportFragmentManager();
        eeFragment.show(fragmentManager, "edit_details");

        /* Artifact from this operation */
        this.fragmentState = true;
    }

    @Override
    public void returnToMain(View view) {
        if (editedState) {
            finish();
        } else {    // User did not make a change
            setResult(Activity.RESULT_CANCELED); finish();
        }
    }
}
