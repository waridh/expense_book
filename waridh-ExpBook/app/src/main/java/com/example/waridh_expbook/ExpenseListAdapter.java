package com.example.waridh_expbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ExpenseListAdapter extends BaseAdapter {
    Context context;
    ExpenseList expenses;   // Points to the same expense list on the heap as the activity.
    LayoutInflater inflater;
    public ExpenseListAdapter(Context applicationContext, ExpenseList entries) {
        this.context = applicationContext;
        this.expenses = entries;
        this.inflater = (LayoutInflater.from(applicationContext));
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return expenses.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return expenses.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.activity_listview, null);
        TextView monthStartedText = (TextView)      view.findViewById(R.id.label);
        TextView expenseName = (TextView)           view.findViewById(R.id.rowName);
        TextView monthlyCost = (TextView)           view.findViewById(R.id.cost);

        /* Setting the text of the list */
        monthStartedText.setText(this.expenses.getMonthStarted(position));
        expenseName.setText(this.expenses.getName(position));
        monthlyCost.setText(this.expenses.getMonthlyChargeNice(position));

        return view;
    }

    /**
     * This method will remove the entry at the specified index, and then update the adapter
     * @param index The index of the list that is being removed
     */
    public void remove(int index) {
        this.expenses.remove(index);
        notifyDataSetChanged();
    }

    /**
     * Method for adding a new expense item and then updating the adapter
     * @param newEntry The new Expense entry
     */
    public void add(Expense newEntry) {
        if (newEntry != null) expenses.add(newEntry);
        notifyDataSetChanged();
    }

    /**
     * This method will update the ExpenseList with the new expense, which will likely be generated
     * from the detailed expense view.
     * @param index The index of the expense that is being updated
     * @param editedEntry The new expense that is replacing that edited expense.
     */
    public void set(int index, @NonNull Expense editedEntry) {
        expenses.set(index, editedEntry);
        notifyDataSetChanged();
    }
}
