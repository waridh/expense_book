package com.example.waridh_expbook;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the class for the user adding a new activity. The goal is to send a new object of the
 * Expense class back to the main activity.
 */
public class NewEntryActivity extends SubActivity {
    /**
     * Default constructor for AppCompatActivity. All Activities must have a default constructor
     * for API 27 and lower devices or when using the default
     * {@link AppComponentFactory}.
     */

    /* Keeping track of all fields so that we can take them in when the user decides to submit */
    private Button addEntryButton;
    private EditText monthStartedEdit, nameEdit, commentEdit, moneyEdit;

    /**
     * The entry method for the new entry page.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * The android onCreate.
         */
        super.onCreate(savedInstanceState); // The super constructor
        setContentView(R.layout.activity_new_entry);

        /* Binding the UI elements to objects */
        this.addEntryButton = findViewById(R.id.add_entry_button);
        this.monthStartedEdit = findViewById(R.id.editTextDate);
        this.nameEdit = findViewById(R.id.editTextName);
        this.commentEdit = findViewById(R.id.editTextComment);
        this.moneyEdit = findViewById(R.id.editTextNumberDecimal);

        /* Automatically putting in the hyphen when the user is putting in the month started */
        this.monthStartedEdit.addTextChangedListener(
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

                         if (monthStartedEdit.length() == 4 && first > second) {
                             monthStartedEdit.append("-");
                         }
                     }
                }
            );

        /* Putting in user input constraints */
        applyFilters();
    }

    private void applyFilters() {

        // Creating the input filter for the edit texts
        InputFilter blockSpecChar = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                final String blockCharacterSet = "./#";
                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };
        // Applying the user input filter into the edit texts
        this.moneyEdit.setFilters(
                new InputFilter[] {
                        new DecimalDigitInputFilter(
                                100, 2
                        )
                });
        this.monthStartedEdit.setFilters(
                new InputFilter[] {
                        blockSpecChar,
                        new InputFilter.LengthFilter(7)
                });
        this.commentEdit.setFilters(
                new InputFilter[] {
                        new InputFilter.LengthFilter(20)
                }
        );
        this.nameEdit.setFilters(
                new InputFilter[] {
                        new InputFilter.LengthFilter(15)
                }
        );
    }

    private boolean checkFields() {
        boolean returnValue = true;
        if (this.nameEdit.length() == 0) {
            this.nameEdit.setError("This field is required");
            returnValue = false;
        } else returnValue = (returnValue && true);
        if (this.monthStartedEdit.length() == 0) {
            this.monthStartedEdit.setError("This field is required");
            returnValue = false;
        }
        else if (
                !Expense.monthStartedCheck(
                        this.monthStartedEdit.getText().toString()
                ))
            this.monthStartedEdit.setError(
                    "The input date is not valid. Please enter a real date.");
        else returnValue = (returnValue && true);
        if (this.moneyEdit.length() == 0) {
            this.moneyEdit.setError("This field is required");
            returnValue = false;
        } else returnValue = (returnValue && true);
        return returnValue;
    }

    /**
     * This method generates the proper new expense based on if the user has given
     * a comment. The assumption is that this function gets called after the input check has
     * completed.
     * @return An Expense object constructed with or without the comment field.
     */
    private Expense createNewExpense(
            String nameText,
            String monthStartedText,
            String chargeText,
            String commentText) {
        Expense retExpense; // Declaration of the output
        if (commentText.length() == 0 || commentText == null || commentText.trim().isEmpty()) {
            retExpense = new Expense(nameText, monthStartedText, chargeText);
        } else {
            retExpense = new Expense(nameText, monthStartedText, chargeText, commentText);
        }
        return retExpense;
    }

    /**
     * This method gets called when the user clicks on the add entry button. It has to do field
     * checks, and after doing so, determines if the data can be sent back to the main activity.
     */
    public void addEntry(View view) {
        if (checkFields()) {
            /* This is a placeholder. I think we are going to try to use finish instead of this */
            Expense newEntry = createNewExpense(
                    this.nameEdit.getText().toString(),
                    this.monthStartedEdit.getText().toString(),
                    this.moneyEdit.getText().toString(),
                    this.commentEdit.getText().toString()
            );
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
