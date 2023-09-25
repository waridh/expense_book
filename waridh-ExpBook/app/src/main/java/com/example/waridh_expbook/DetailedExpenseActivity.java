package com.example.waridh_expbook;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DetailedExpenseActivity extends SubActivity {

    private Intent inIntent;

    /* Expense details */
    private Expense inExpense;
    private String monthlyChargeS, monthStartedS, nameS, commentS;
    private boolean commentState;

    /* Need to be able to set the text */
    private TextView monthlyChargeDtv, monthStartedDtv, nameDtv, commentDtv;
    private LinearLayout commentDv;

    /* Fragment related attributes */
    private boolean fragmentState;


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

        /* Getting the contents of the intent */
        this.inIntent = this.getIntent();
        this.inExpense = (Expense) this.inIntent.getExtras().getSerializable(MainActivity.ARG_DETAILED_EXPENSE);
        fragmentState = false;  // The fragment should not be showing immediately when you open

        loadDetails();  // Loading the data from the Expense object

        /* Linking the UI elements to objects */
        monthlyChargeDtv = findViewById(R.id.monthly_charge_dtv);
        monthStartedDtv = findViewById(R.id.month_started_dtv);
        nameDtv = findViewById(R.id.name_dtv);
        commentDtv = findViewById(R.id.comment_dtv);
        commentDv = findViewById(R.id.comment_view);

        setDetailedTextViews();
    }

    /**
     * This method loads the details from the Expense object into strings
     */
    private void loadDetails() {
        /* Setting the string value in case that the user will edit it */
        nameS = inExpense.getName();
        monthStartedS = inExpense.getMonthStarted();
        monthlyChargeS = inExpense.getMonthlyCharge();
        commentState = inExpense.getCommentFlag();
        loadComment();
    }

    private void setDetailedTextViews() {
        monthlyChargeDtv.setText(monthlyChargeS);
        monthStartedDtv.setText(monthStartedS);
        nameDtv.setText(nameS);
        /* Need to check if there are comments or not */
        setCommentDetailedTextView();
    }

    /**
     * This method is just flow control for the comment. Hides the comment if it doesn't exist else
     * it will set the text.
     */
    private void setCommentDetailedTextView() {
        if (commentState) commentDtv.setText(commentS);
        else commentDv.setVisibility(View.GONE);    // The view does not need to show up
    }

    /**
     * This method loads the comment if it exists, else it will be set to null
     */
    private void loadComment() {
        if (commentState) commentS = inExpense.getComment();
        else commentS = null;
    }

    private EditEntryFragment newEntryFragment() {
        if (commentState) return EditEntryFragment.newInstance(nameS, monthStartedS, monthlyChargeS, commentS);
        else return EditEntryFragment.newInstance(nameS, monthStartedS, monthlyChargeS);
    }

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
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /* Adding the fragment class into the transaction */
//        fragmentTransaction.add(
//                R.id.edit_detail_fc, eeFragment
//        ).addToBackStack(null).commit();

        /* Artifact from this operation */
        this.fragmentState = true;
    }
}
