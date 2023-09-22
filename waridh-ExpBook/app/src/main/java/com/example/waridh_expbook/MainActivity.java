package com.example.waridh_expbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    /* Array of String for now */

    private ExpenseList entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.entries = new ExpenseList();
        this.entries.add(new Expense("Bach", "2000-05", "100.00"));
        this.entries.add(new Expense("Brahm", "2000-05", "100.00"));
        this.entries.add(new Expense("Watrina", "2000-05", "1000.00"));

        ExpenseListAdapter adapter = new ExpenseListAdapter(
                this, this.entries);
        ListView listView = (ListView) findViewById(R.id.expense_list);
        listView.setAdapter(adapter);
    }

    public void openNewEntry(View view) {
        /**
         * This method will open up the view where the user can input a new expense entry.
         * @param View view - view
         */
        Intent intent = new Intent(this, NewEntryActivity.class);
        startActivity(intent);
    }
}