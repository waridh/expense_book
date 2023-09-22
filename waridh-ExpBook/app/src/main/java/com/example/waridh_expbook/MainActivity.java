package com.example.waridh_expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

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
    }

    private AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            /* TODO: Put in the code that will open up the detailed view of the expense */
        }
    };

    public void openNewEntry(View view) {
        /**
         * This method will open up the view where the user can input a new expense entry.
         * @param View view - view
         */
        Intent intent = new Intent(this, NewEntryActivity.class);
        startActivity(intent);
    }

    /**
     * This method is used to open up the detailed view of an entry.
     */
    public void openEntryDetails() {

    }
}