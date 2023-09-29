package com.example.waridh_expbook;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class EditEntryFragment extends DialogFragment {

    /* The following enums are for fragment controlling */
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
            EEFOptions option
                    = (EEFOptions) requireArguments().getSerializable(ARG_EXISTANCE_CHECK);
            /* Checking if we have an input expense or not. Also include the flow control for that*/
            switch(Objects.requireNonNull(option)) {
                case SOME:  // There is expense being passed in
                    argExpense = (Expense) getArguments().getSerializable(ARG_EXPENSE);
                    fName = argExpense.getName();
                    fMonthStarted = argExpense.getMonthStarted();
                    fMonthlyExpense = argExpense.getMonthlyCharge();
                    /* Comment existence check. */
                    if (argExpense.getCommentFlag()) {
                        fComment = argExpense.getComment();
                    }
                    break;
                case NONE:  // No expense being passed in
                    argExpense = null;
                    break;
                default:
            }

            /* The effects from the operation mode */
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

    /**
     * The method that will run after views are created. Many UI setups.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
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
                    int current = 0;
                    int previous;

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        previous = current;
                        current = s.length();

                        /* This handle will work when the user is on mobile (no access to -) */
                        if (fMonthStartedEt.length() == 4 && current > previous) {
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
         * Called when a view has been clicked. Needed for controlling the cancel button on fragment
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
         * Called when a view has been clicked. Using on click listener since it works on fragments
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

    /**
     * This method sets up the edit text box filters so that the users do not break the constraints
     */
    private void applyFilters() {
        // Creating the input filter for month started edit text. Blocks special characters
        InputFilter blockSpecChar = (source, start, end, dest, dStart, dEnd) -> {
            final String blockCharacterSet = "./#";
            if (source != null && blockCharacterSet.contains(("" + source))) return "";
            return null;
        };
        // Applying the user input filter into the edit texts
        this.fMonthlyExpenseEt.setFilters( new InputFilter[] {
                        new DecimalDigitInputFilter(10, 2)});
        /* Setting up a length filter for the month started as well */
        this.fMonthStartedEt.setFilters( new InputFilter[] {blockSpecChar,
                new InputFilter.LengthFilter(7)});
        // Comment length constraint
        this.fCommentEt.setFilters( new InputFilter[] {new InputFilter.LengthFilter(20)});
        // Name length constraint
        this.fNameEt.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});
    }

    /**
     * This method does a local state check on if the user input meets the required constraints.
     * @return Boolean that is true when the user input follows the constraint, and false if not
     */
    private boolean checkFields() {
        return BaseActivity.checkFields(this.fNameEt, this.fMonthStartedEt, this.fMonthlyExpenseEt);
    }

    /**
     * Generates an Expense object from the user input
     * @return The Expense object that contains the data that the user input.
     */
    private Expense acceptUserInput() {
        return Expense.newInstance( fNameEt.getText().toString(),
                fMonthStartedEt.getText().toString(), fMonthlyExpenseEt.getText().toString(),
                fCommentEt.getText().toString()
        );
    }

    /**
     * This method was used for sending the edited expenses back to the activity that called it.
     * @param newExpense The expense object that is being sent back to the previous activity
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