package com.example.waridh_expbook;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

public class DetailedExpenseActivity extends SubActivity {

    private Intent inIntent;

    /* Expense details */
    private Expense inExpense;
    private Expense editedExpense;
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
        this.inIntent = this.getIntent();
        this.inExpense = extractExpense(this.inIntent, ARG_DETAILED_EXPENSE);
        fragmentState = false;  // The fragment should not be showing immediately when you open

        loadDetails(inExpense);  // Loading the data from the Expense object

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
                        editedExpense = extractExpense(
                                bundle, EditEntryFragment.ARG_FRAG_BUNDLE_KEY);
                        updateExpenseDetails(editedExpense);
                        // TODO: Need to send the data back to the main activity
                        fragmentState = false;  // Flipping the state off.
                    }
                });
    }

    private void updateExpenseDetails(Expense expense) {
        loadDetails(expense);
        setDetailedTextViews();
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
        loadComment(expense);
    }

    private void setDetailedTextViews() {
        monthlyChargeDtv.setText(monthlyChargeS);
        monthStartedDtv.setText(monthStartedS);
        nameDtv.setText(nameS);
        /* Need to check if there are comments or not */
        setCommentDetailedTextView(commentS);
    }

    private void setDetailedTextViews(
            String name, String monthlyCharge, String monthStarted, String comment) {
        monthlyChargeDtv.setText(monthlyCharge);
        monthStartedDtv.setText(monthStarted);
        nameDtv.setText(name);
        /* Need to check if there are comments or not */
        setCommentDetailedTextView(comment);
    }

    /**
     * This method is just flow control for the comment. Hides the comment if it doesn't exist else
     * it will set the text.
     */
    private void setCommentDetailedTextView(String comment) {
        if (Expense.commentCheck(comment)) commentDtv.setText(comment);
        else commentDv.setVisibility(View.GONE);    // The view does not need to show up
    }

    /**
     * This method loads the comment if it exists, else it will be set to null
     */
    private void loadComment(Expense expense) {
        if (expense.getCommentFlag()) commentS = expense.getComment();
        else commentS = null;
    }

    private EditEntryFragment newEntryFragment() {
        if (commentState) return EditEntryFragment.newInstance(nameS, monthStartedS, monthlyChargeS, commentS);
        else return EditEntryFragment.newInstance(nameS, monthStartedS, monthlyChargeS);
    }

    /**
     * This method opens the edit value fragment.
     * @param view
     */
    public void editButtonFc(View view) {
        displayFragment();
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

    // TODO: Need to have different returns for when there is and isn't an edit made
    @Override
    public void returnToMain(View view) {
        if (editedState) {
            // TODO: Finalize how the edited expense is stored.
        } else {    // User did not make a change
            setResult(Activity.RESULT_CANCELED); finish();
        }
    }
}
