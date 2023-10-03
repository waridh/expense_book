package com.example.waridh_expbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is the main activity class. It handles both the UI control for the main activity and the
 * logic behind user interaction and how the program should react to them.
 */
public class MainActivity extends BaseActivity {

    /* Data structure for storing the expenses entries */
    private ExpenseList entries;

    private Button mainDeleteB;
    private TextView appTitle, nameHeader, dateHeader,  priceHeader, expenseSumTv;

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
        ListView expenseListView = findViewById(R.id.expense_list);
        this.mainDeleteB = findViewById(R.id.main_delete_button);
        this.appTitle = findViewById(R.id.app_title);
        this.nameHeader = findViewById(R.id.name_header);
        this.dateHeader = findViewById(R.id.date_header);
        this.priceHeader = findViewById(R.id.price_header);
        this.expenseSumTv = findViewById(R.id.expense_sum_text);

        setDefaultModeUI(); // Set standard UI elements

        this.expenseAdapter = new ExpenseListAdapter(
                this, this.entries);

        expenseListView.setAdapter(this.expenseAdapter);
        expenseListView.setOnItemClickListener(expenseListClick);
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
            }};

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
                    if (result.getData().getExtras().getByte(ARG_EXPENSE_LIST_COMMAND)
                            == DEAD_CODE) {
                        expenseAdapter.remove(index);
                    }
                    else {
                        expenseAdapter.set(index,
                                extractExpense(result.getData(),
                                        ARG_RETURNED_EXPENSE));
                    }
                    setDefaultModeUI(); // Quick way to get the summation updated
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
                (requestKey, bundle) -> {
                    // Unwrapping expense from a fragment package
                    Expense theExpense = extractExpense(bundle,
                            EditEntryFragment.ARG_FRAG_BUNDLE_KEY);
                    expenseAdapter.add(theExpense); // Adding the returned expense to the list
                    setDefaultModeUI();
                });
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
        /* Need to make sure that the other header UIs are not gone */
        this.dateHeader.setVisibility(View.VISIBLE);
        priceHeader.setVisibility(View.VISIBLE);

        /* Setting up the text for these */
        this.appTitle.setText(getString(R.string.header_text));
        this.nameHeader.setText("Name");
        this.dateHeader.setText("Date");
        this.priceHeader.setText("Price");
        this.mainDeleteB.setText(getString(R.string.delete_expense_button_text));
        this.expenseSumTv.setText(entries.getSum());
    }

    /**
     * Sets the texts on the UI to guide users to do delete mode.
     */
    private void setDeleteModeUI() {
        /* Need to hide the other fields so it doesn't interfere*/
        this.dateHeader.setVisibility(View.GONE);
        this.priceHeader.setVisibility(View.GONE);

        this.appTitle.setText(getString(R.string.delete_mode_header));
        this.nameHeader.setText(getString(R.string.delete_mode_sub_header));
        this.mainDeleteB.setText(getString(R.string.delete_mode_delete_button));
    }
}