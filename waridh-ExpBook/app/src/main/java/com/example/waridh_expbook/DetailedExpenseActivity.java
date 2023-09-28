package com.example.waridh_expbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailedExpenseActivity extends SubActivity {

    /* Expense details (Data) */
    private Expense theExpense;

    /* Text views that need to be changed */
    private TextView monthlyChargeDtv, monthStartedDtv, nameDtv, commentDtv;
    private LinearLayout commentDv;

    /* State controlling flags */
    private boolean editedState;

    /**
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        /* Activity State control */
        this.editedState = false;

        /* Getting the contents of the intent */
        this.theExpense = extractExpense(this.getIntent(), ARG_DETAILED_EXPENSE);

        /* Linking the UI elements to objects */
        monthlyChargeDtv = findViewById(R.id.monthly_charge_dtv);
        monthStartedDtv = findViewById(R.id.month_started_dtv);
        nameDtv = findViewById(R.id.name_dtv);
        commentDtv = findViewById(R.id.comment_dtv);
        commentDv = findViewById(R.id.comment_view);

        setDetailedTextViews();

        /* Setting up a fragment result listener */
        setupFragmentResultListener();}

    /**
     * This method installs the fragment result listener, meaning that when the fragment sends
     * anything, this activity will process it immediately. Updated the current expense and package
     * it for the main location.
     */
    private void setupFragmentResultListener() {
        getSupportFragmentManager().setFragmentResultListener(
                EditEntryFragment.ARG_DETAIL_REQUEST_KEY,
                this,
                (requestKey, bundle) -> {
                    // Going to mostly be unwrapping a bundle
                    theExpense = extractExpense(
                            bundle, EditEntryFragment.ARG_FRAG_BUNDLE_KEY);

                    /* Loading the expense object into the activity */
                    setDetailedTextViews();

                    /* Updating the expense in the main activity */
                    sendExpenseToMainAux(theExpense, LIFE_CODE);
                    });}

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
     * This method will set the text views on the detailed page to have the current data that is
     * held in the instance Expense object.
     */
    private void setDetailedTextViews() {
        monthlyChargeDtv.setText(theExpense.getMonthlyChargeNice());
        monthStartedDtv.setText(theExpense.getMonthStarted());
        nameDtv.setText(theExpense.getName());
        /* Need to check if there are comments or not */
        setCommentDetailedTextView(theExpense.getComment());
    }

    /**
     * This method is just flow control for the comment. Hides the comment if it doesn't exist else
     * it will set the text.
     * @param comment The comment string under check
     */
    private void setCommentDetailedTextView(String comment) {
        if (Expense.commentCheck(comment)) {        // The comment exists
            commentDv.setVisibility(View.VISIBLE);
            commentDtv.setText(comment);}
        else commentDv.setVisibility(View.GONE);    // The view does not need to show up
    }

    /**
     * This method opens the edit value fragment. It's designed to be linked to a button
     * @param view is a requirement for linking a button to a method through xml
     */
    public void editButtonFc(View view) {
        displayFragment(theExpense, EditEntryFragment.OpMode.EDIT, "edit_details");
    }

    /**
     * The delete button link method. This method will send a message back to the activity that the
     * expense is being deleted.
     * @param view this parameter is required to link the button with the method.
     */
    public void deleteButtonFc(View view) {
        deleteExpense();
        finish();
    }

    /**
     * Method for when the activity exits. If there are no changes, then the main activity is
     * notified, else just finishes because the payload would have already been installed.
     * @param view Needed for the android API.
     */
    @Override
    public void returnToMain(View view) {
        if (editedState) {
            finish();
        } else {    // User did not make a change
            setResult(Activity.RESULT_CANCELED); finish();
        }
    }
}
