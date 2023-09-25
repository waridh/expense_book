package com.example.waridh_expbook;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailedExpenseActivity extends SubActivity {

    private Intent inIntent;
    private Expense inExpense;
    private TextView monthlyChargeDtv, monthStartedDtv, nameDtv, commentDtv;
    private LinearLayout commentDv;

    /* Need to be able to set the text */


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
        this.inExpense = (Expense) this.inIntent.getExtras().getSerializable("entry");

        /* Linking the UI elements to objects */
        monthlyChargeDtv = findViewById(R.id.monthly_charge_dtv);
        monthStartedDtv = findViewById(R.id.month_started_dtv);
        nameDtv = findViewById(R.id.name_dtv);
        commentDtv = findViewById(R.id.comment_dtv);
        commentDv = findViewById(R.id.comment_view);

        setDetailedTextViews();
    }

    private void setDetailedTextViews() {
        monthlyChargeDtv.setText(inExpense.getMonthlyCharge());
        monthStartedDtv.setText(inExpense.getMonthStarted());
        nameDtv.setText(inExpense.getName());
        /* Need to check if there are comments or not */
        setCommentDetailedTextView();
    }

    private void setCommentDetailedTextView() {
        if (inExpense.getCommentFlag()) commentDtv.setText(inExpense.getComment());
        else commentDv.setVisibility(View.GONE);
    }
}
