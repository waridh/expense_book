package com.example.waridh_expbook;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        InputFilter blockSpecChar = new InputFilter() { // Keeping this in scope.
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
        return checkFields(this.nameEdit, this.monthStartedEdit, moneyEdit);
    }

    /**
     * This method gets called when the user clicks on the add entry button. It has to do field
     * checks, and after doing so, determines if the data can be sent back to the main activity.
     */
    public void addEntry(View view) {
        if (checkFields()) {
            /* This is a placeholder. I think we are going to try to use finish instead of this */
            Expense newEntry = Expense.newInstance(
                    this.nameEdit.getText().toString(),
                    this.monthStartedEdit.getText().toString(),
                    this.moneyEdit.getText().toString(),
                    this.commentEdit.getText().toString()
            );
            returnResultToMain(bundleExpense(newEntry, ARG_RETURNED_EXPENSE));
        }
    }
}
