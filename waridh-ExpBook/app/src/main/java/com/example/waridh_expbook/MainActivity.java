package com.example.waridh_expbook;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentResultListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    /* Data structure for storing the expenses entries */
    private ExpenseList entries;

    /* UI elements */
    private ListView expenseListView;
    private Button mainDeleteB;
    private TextView appTitle, tableHeader;

    /* The view adapters */
    private ExpenseListAdapter expenseAdapter;

    /* Activity state control */
    private boolean deleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.entries = new ExpenseList();   // Instantiating the data structure

        /* Linking UI elements */
        this.expenseListView = findViewById(R.id.expense_list);
        this.mainDeleteB = findViewById(R.id.main_delete_button);
        this.appTitle = findViewById(R.id.app_title);
        this.tableHeader = findViewById(R.id.table_header);
        setDefaultModeUI();

        /* This is some debugging entries TODO: Delete these.*/
        this.entries.add(new Expense("Bach", "2000-05", "100.00"));
        this.entries.add(new Expense("Brahm", "2000-05", "100.00"));
        this.entries.add(new Expense("Watrina", "2000-05", "1000.00"));

        this.expenseAdapter = new ExpenseListAdapter(
                this, this.entries);

        this.expenseListView.setAdapter(this.expenseAdapter);
        this.expenseListView.setOnItemClickListener(expenseListClick);
        setupFragmentResultListener();
    }

    /**
     * Anonymous class for when the user clicks on a list item. We don't expect this value to change
     */
    private final AdapterView.OnItemClickListener expenseListClick =
            new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (deleteMode) expenseAdapter.remove(position);
            else openEntryDetailsForResult(position);    // This method opens the detailed view
        }
    };

    /**
     * This method is called when the add entry button has been clicked.
     * @param view Required parameter for xml onClick calls.
     */
    public void addEntryButtonFc(View view) {
        deactivateDeleteMode();
        displayFragment(EditEntryFragment.OpMode.ADD, "add_entry");
    }

    /**
     * This method is called when the add entry button has been clicked.
     * @param view Required parameter for xml onClick calls.
     */
    public void deleteEntryButtonFc(View view) {
        toggleDeleteMode();
    }

    /**
     * This method will open the details page for the expense. What it will also do is accept any
     * changes that that user made in the details page.
     * @param index This is the index on the list view that the expense being viewed is.
     */
    private void openEntryDetailsForResult(int index) {
        /* Getting the expense data */
        Bundle bundle = bundleExpense(this.entries.get(index), ARG_DETAILED_EXPENSE);

        /* Setting up the intent */
        Intent intent = new Intent(this, DetailedExpenseActivity.class);
        intent.putExtras(bundle);

        /* Launching the activity for result */
        activityLauncher.launch(intent, result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                /* This code means that the entry was updated. */
                if (result.getData() != null) {
                    /* Deleting the expense or updating it? */
                    if (result.getData().getExtras().getByte(ARG_EXPENSE_LIST_COMMAND) == DEAD_CODE) {
                        expenseAdapter.remove(index);
                    }
                    else expenseAdapter.set(
                        index,
                        extractExpense(
                                result.getData(),
                                ARG_RETURNED_EXPENSE)
                    );
                }
            }
        });
    }

    /**
     * Method to toggle delete mode in case the user wants to mass delete the stuff in the main
     * activity.
     */
    private void toggleDeleteMode() {
        if (deleteMode) deactivateDeleteMode();
        else            activateDeleteMode();
    }

    /**
     * Turns on the delete mode, which changes the UI, and turns on the delete mode flag.
     */
    private void activateDeleteMode() {
        deleteMode = true;
        setDeleteModeUI();
    }

    /**
     * This method installs the fragment result listener, meaning that when the fragment sends
     * anything, this activity will process it immediately. Updated the current expense and package
     * it for the main location.
     */
    private void setupFragmentResultListener() {
        getSupportFragmentManager().setFragmentResultListener(
            EditEntryFragment.ARG_MAIN_REQUEST_KEY,
            this,
            new FragmentResultListener() {
                @Override
                public void onFragmentResult(
                        @NonNull String requestKey, @NonNull Bundle bundle
                ) {
                    // Unwrapping expense from a fragment package
                    Expense theExpense = extractExpense(
                            bundle, EditEntryFragment.ARG_FRAG_BUNDLE_KEY);
                    expenseAdapter.add(theExpense); // Adding the returned expense to the list
                }});
    }

    /**
     * Turns off the delete mode flag and then restores the default UI.
     */
    private void deactivateDeleteMode() {
        deleteMode = false;
        setDefaultModeUI();
    }

    /**
     * Sets the texts on the UI to be the default values for non-delete usage.
     */
    private void setDefaultModeUI() {
        this.appTitle.setText(getString(R.string.header_text));
        this.tableHeader.setText("Name Date Price");
        this.mainDeleteB.setText(getString(R.string.delete_expense_button_text));
    }

    /**
     * Sets the texts on the UI to guide users to do delete mode.
     */
    private void setDeleteModeUI() {
        this.appTitle.setText(getString(R.string.delete_mode_header));
        this.tableHeader.setText(getString(R.string.delete_mode_sub_header));
        this.mainDeleteB.setText(getString(R.string.delete_mode_delete_button));
    }
}