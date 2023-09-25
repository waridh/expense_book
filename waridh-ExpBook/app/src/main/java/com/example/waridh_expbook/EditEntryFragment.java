package com.example.waridh_expbook;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditEntryFragment extends DialogFragment {

    // the fragment initialization parameters
    private static final String ARG_NAME = "param1";
    private static final String ARG_MONTH_STARTED = "param2";
    private static final String ARG_MONTHLY_CHARGE = "param3";
    private static final String ARG_COMMENT = "param4";
    private static final String ARG_COMMENT_CHECK = "param5";

    /* Fragment edit */
    private int margin;

    // These are the instance variables
    private String fName,fMonthStarted,fMonthlyExpense,fComment;
    private boolean fCommentFlag;

    /* These are UI elements */
    private EditText fNameEt, fMonthStartedEt, fMonthlyExpenseEt, fCommentEt;
    private Button fCancelB, fSubmitB;

    public EditEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name The name of the expense in string form
     * @param monthStarted The month started with the format of yyyy-mm in string form.
     * @param monthlyCharge The monthly charge in string form
     * @return A new instance of fragment EditEntryFragment.
     */
    public static EditEntryFragment newInstance(
            String name,
            String monthStarted,
            String monthlyCharge
            ) {
        EditEntryFragment fragment = new EditEntryFragment();
        Bundle args = new Bundle();

        /* Bundling the arguments that is required to open up the fragment */
        args.putString(ARG_NAME, name);
        args.putString(ARG_MONTH_STARTED, monthStarted);
        args.putString(ARG_MONTHLY_CHARGE, monthlyCharge);

        /* In this case, there is no comment */
        args.putByte(ARG_COMMENT_CHECK, (byte)0);   // Using byte like a boolean. 0 if no comment

        fragment.setArguments(args);
        return fragment;
    }

    public static EditEntryFragment newInstance(
            String name,
            String monthStarted,
            String monthlyCharge,
            String comment
    ) {
        EditEntryFragment fragment = new EditEntryFragment();
        Bundle args = new Bundle();

        /* Bundling the arguments that is required to open up the fragment */
        args.putString(ARG_NAME, name);
        args.putString(ARG_MONTH_STARTED, monthStarted);
        args.putString(ARG_MONTHLY_CHARGE, monthlyCharge);

        /* In this case, there is comment */
        args.putByte(ARG_COMMENT_CHECK, (byte)1);   // Using byte like a boolean. 0 if no comment
        args.putString(ARG_COMMENT, comment);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fName = getArguments().getString(ARG_NAME);
            fMonthStarted = getArguments().getString(ARG_MONTH_STARTED);
            fMonthlyExpense = getArguments().getString(ARG_MONTHLY_CHARGE);
            /* Comment existence check. */
            if (getArguments().getByte(ARG_COMMENT_CHECK) == 0) fCommentFlag = false;
            else {
                fCommentFlag = true;
                fComment = getArguments().getString(ARG_COMMENT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fView = inflater.inflate(R.layout.fragment_edit_entry, container, false);

        /* This is the time to link the ui element with the instance variables */
        this.fNameEt = fView.findViewById(R.id.edit_name_et);
        this.fMonthlyExpenseEt = fView.findViewById(R.id.edit_monthly_expense_et);
        this.fMonthStartedEt = fView.findViewById(R.id.edit_month_started_et);
        this.fCommentEt = fView.findViewById(R.id.edit_comment_et);
        this.fSubmitB = fView.findViewById(R.id.edit_submit_b);
        this.fCancelB = fView.findViewById(R.id.edit_cancel_b);

        /* Setting up the listeners */
        this.fCancelB.setOnClickListener(cancelButtonListener);

        /* Order matters here. We are setting the values in the edit text box */
        setTextBoxes();

        return fView;
    }

    View.OnClickListener cancelButtonListener = new View.OnClickListener() {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            dismiss();  // This is the best way to close a dialog fragment.
        }
    };

    /**
     * This method sets the texts for the EditText text boxes. This method has to be called after
     * both the strings from argument has been extracted, and the EditText text boxes have been
     * linked.
     */
    private void setTextBoxes() {
        fNameEt.setText(fName);
        fMonthlyExpenseEt.setText(fMonthlyExpense);
        fMonthStartedEt.setText(fMonthStarted);
        if (fCommentFlag) fCommentEt.setText(fComment);
    }

    // TODO: Send the data back to the activity
}