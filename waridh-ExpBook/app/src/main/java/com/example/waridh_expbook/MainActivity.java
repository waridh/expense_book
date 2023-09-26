package com.example.waridh_expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends BaseActivity {

    /* Data structure for storing the expenses entries */
    private ExpenseList entries;

    /* View objects that are needed for user interactions */
    private ListView expenseListView;

    /* The view adapters */
    private ExpenseListAdapter expenseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.entries = new ExpenseList();   // Instantiating the data structure
        this.expenseListView = findViewById(R.id.expense_list);

        /* This is some debugging entries TODO: Delete these.*/
        this.entries.add(new Expense("Bach", "2000-05", "100.00"));
        this.entries.add(new Expense("Brahm", "2000-05", "100.00"));
        this.entries.add(new Expense("Watrina", "2000-05", "1000.00"));

        this.expenseAdapter = new ExpenseListAdapter(
                this, this.entries);

        this.expenseListView.setAdapter(this.expenseAdapter);
        this.expenseListView.setOnItemClickListener(expenseListClick);
    }

    /**
     * Anonymous class for when the user clicks on a list item.
     */
    private AdapterView.OnItemClickListener expenseListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            /* TODO: Put in the code that will open up the detailed view of the expense */
            openEntryDetails(position);
        }
    };

    public void addEntryButtonFc(View view) {
        /**
         * This method will open up the view where the user can input a new expense entry.
         * @param View view - view
         */
//        Intent intent = new Intent(this, NewEntryActivity.class);
//        startActivity(intent );
        openNewEntryForResult();
    }

    /**
     * This method launches the new entry activity, and then also does handling for the returned
     * result.
     */
    private void openNewEntryForResult() {
        Intent intent = new Intent(this, NewEntryActivity.class);
        activityLauncher.launch(intent, result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                /* Taking the input and updating the ExpenseList */
                updateExpenseList(
                        extractExpense(
                                result.getData() != null ? result.getData() : null,
                                ARG_RETURNED_EXPENSE
                        )
                );
            }
        });
    }

    /**
     * This method updates the ExpenseList by added the new
     * @param newEntry
     */
    private void updateExpenseList(Expense newEntry) {
        if (newEntry != null) this.entries.add(newEntry);
        this.expenseAdapter.notifyDataSetChanged();
    }

    /**
     * This method is used to open up the detailed view of an entry.
     */
    public void openEntryDetails(int index) {
        Bundle bundle = bundleExpense(this.entries.get(index), ARG_DETAILED_EXPENSE);

        /* Packaging the bundle into the intent */
        Intent intent = new Intent(this, DetailedExpenseActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}