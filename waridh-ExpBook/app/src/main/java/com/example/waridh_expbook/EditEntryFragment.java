package com.example.waridh_expbook;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class EditEntryFragment extends DialogFragment {
    enum OpMode {
        EDIT,
        ADD
    }

    enum EEFOptions {
        SOME,
        NONE
    }

    // the fragment initialization parameters
    private static final String ARG_EXPENSE = "lfdksfs&@j@!(*&";
    private static final String ARG_OP_MODE = "ihu212 kjh1401 =@!#!U)";
    private static final String ARG_EXISTANCE_CHECK = "ej2e1e 90( @E09)E@Q) (";

    /* Key for accessing the bundle from the fragment */
    public static final String ARG_FRAG_BUNDLE_KEY = "j312i&da";
    public static final String ARG_DETAIL_REQUEST_KEY = "j21k3j0s*A213A(*";
    public static final String ARG_MAIN_REQUEST_KEY = "oi ji2dasi 0fdal0@!( ))ODSOQK) ";

    // These are the instance variables
    private String fName,fMonthStarted,fMonthlyExpense,fComment, fragmentHeaderS;

    /* These are UI elements */
    private EditText fNameEt, fMonthStartedEt, fMonthlyExpenseEt, fCommentEt;
    private TextView fragmentHeaderTv;

    /* Storage of the mode of operation for the fragment */
    private OpMode operationMode;

    public EditEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param argExpense This is the expense that is being sent into the fragment
     * @return A new instance of fragment EditEntryFragment.
     */
    public static EditEntryFragment newInstance(
            Expense argExpense,
            OpMode operationMode
    ) {
        EditEntryFragment fragment = new EditEntryFragment();
        Bundle args = new Bundle();

        /* Bundling the arguments that is required to open up the fragment */
        args.putSerializable(ARG_EXPENSE, argExpense);
        args.putSerializable(ARG_OP_MODE, operationMode);
        args.putSerializable(ARG_EXISTANCE_CHECK, EEFOptions.SOME);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Factory method for creation of the object
     * @param operationMode The mode of operation. Supports ADD and EDIT mode.
     * @return The generated EditEntryFragment instance
     */
    public static EditEntryFragment newInstance(
            OpMode operationMode
    ) {
        EditEntryFragment fragment = new EditEntryFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_OP_MODE, operationMode);
        args.putSerializable(ARG_EXISTANCE_CHECK, EEFOptions.NONE);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * The entrance method.
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Running the inherited
        if (getArguments() != null) {   // Argument safety-check
            Expense argExpense;
            operationMode = (OpMode) requireArguments().getSerializable(ARG_OP_MODE);
            EEFOptions option = (EEFOptions) requireArguments().getSerializable(
                    ARG_EXISTANCE_CHECK);
            switch(Objects.requireNonNull(option)) {
                case SOME:
                    argExpense = (Expense) getArguments().getSerializable(ARG_EXPENSE);
                    fName = argExpense.getName();
                    fMonthStarted = argExpense.getMonthStarted();
                    fMonthlyExpense = argExpense.getMonthlyCharge();
                    /* Comment existence check. */
                    if (argExpense.getCommentFlag()) {
                        fComment = argExpense.getComment();
                    }
                    break;
                case NONE:
                    argExpense = null;
                    break;
                default:
            }
            switch(operationMode) {
                case EDIT:
                    fragmentHeaderS = "Edit entry";
                    break;
                case ADD:
                    fragmentHeaderS = "Add entry";
                    break;
                default:
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
        fragmentHeaderTv = fView.findViewById(R.id.fragment_header_tv);
        Button fSubmitB = fView.findViewById(R.id.edit_submit_b);
        Button fCancelB = fView.findViewById(R.id.edit_cancel_b);

        /* Setting up the listeners */
        fCancelB.setOnClickListener(cancelButtonListener);
        fSubmitB.setOnClickListener(submitChangesButtonListener);

        /* Order matters here. We are setting the values in the edit text box */
        setTextBoxes();

        /* Automatically putting in the hyphen when the user is putting in the month started */
        this.fMonthStartedEt.addTextChangedListener(
                new TextWatcher() {
                    int first = 0;
                    int second;

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        second = first;
                        first = s.length();

                        if (fMonthStartedEt.length() == 4 && first > second) {
                            fMonthStartedEt.append("-");
                        }
                    }
                }
        );

        /* The following methods are for setting constraints on the edit texts */
        applyFilters();

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

    View.OnClickListener submitChangesButtonListener = new View.OnClickListener() {

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (checkFields()) {
                sendResult(acceptUserInput());
                dismiss();
            }
        }
    };

    /**
     * This method sets the texts for the EditText text boxes. This method has to be called after
     * both the strings from argument has been extracted, and the EditText text boxes have been
     * linked.
     */
    private void setTextBoxes() {
        switch (operationMode) {
            case EDIT:
                fragmentHeaderTv.setText(fragmentHeaderS);
                fNameEt.setText(fName);
                fMonthlyExpenseEt.setText(fMonthlyExpense);
                fMonthStartedEt.setText(fMonthStarted);
                if (Expense.commentCheck(fComment)) fCommentEt.setText(fComment);
                break;
            case ADD:
                fragmentHeaderTv.setText(fragmentHeaderS);
                break;
            default:
        }

    }

    private void applyFilters() {
        // Creating the input filter for the edit texts
        InputFilter blockSpecChar = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
                final String blockCharacterSet = "./#";
                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        // Applying the user input filter into the edit texts
        this.fMonthlyExpenseEt.setFilters(
                new InputFilter[] {
                        new DecimalDigitInputFilter(
                                100, 2)});
        this.fMonthStartedEt.setFilters(
                new InputFilter[] {
                        blockSpecChar, new InputFilter.LengthFilter(7)});
        this.fCommentEt.setFilters(
                new InputFilter[] {
                        new InputFilter.LengthFilter(20)});
        this.fNameEt.setFilters(
                new InputFilter[] {new InputFilter.LengthFilter(15)});
    }

    private boolean checkFields() {
        return BaseActivity.checkFields(
                this.fNameEt, this.fMonthStartedEt, this.fMonthlyExpenseEt);
    }

    private Expense acceptUserInput() {
        updateLocalRegisters();
        return generateExpense();
    }
    private void updateLocalRegisters() {
        fName = fNameEt.getText().toString();
        fMonthStarted = fMonthStartedEt.getText().toString();
        fMonthlyExpense = fMonthlyExpenseEt.getText().toString();
        fComment = fCommentEt.getText().toString();
    }

    /**
     * This method creates an expense from the data stored in the local scope.
     * @return A new expense created from the local fragment scope data.
     */
    private Expense generateExpense() {
        return Expense.newInstance(
                fName,
                fMonthStarted,
                fMonthlyExpense,
                fComment
        );
    }

    /**
     * This method was used for sending the edited expenses back to the activity that called it.
     * @param newExpense
     */
    private void sendResult(Expense newExpense) {
        Bundle result = BaseActivity.bundleExpense(newExpense, ARG_FRAG_BUNDLE_KEY);

        /* Need to choose the activity that we are sending the result to */
        switch(operationMode) {
            case EDIT:
                getParentFragmentManager().setFragmentResult(ARG_DETAIL_REQUEST_KEY, result);
                break;
            case ADD:
                getParentFragmentManager().setFragmentResult(ARG_MAIN_REQUEST_KEY, result);
                break;
        }
    }
}