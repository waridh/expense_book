package com.example.waridh_expbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Expense> entries;

    LayoutInflater inflater;
    public CustomAdapter(Context applicationContext, ArrayList<Expense> entries) {
        this.context = applicationContext;
        this.entries = entries;
        this.inflater = (LayoutInflater.from(applicationContext));
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return entries.size();
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
        return entries.get(position);
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
        TextView testLister = (TextView)           view.findViewById(R.id.label);
        TextView test2 = (TextView)           view.findViewById(R.id.rowName);
        TextView test3 = (TextView)           view.findViewById(R.id.cost);
//        TextView numList = (TextView) view.findViewById(R.id.number);
        testLister.setText((this.entries.get(position)).getMonthStarted());
//        testLister.setText("tester");
        test2.setText(this.entries.get(position).getName());
        test3.setText(this.entries.get(position).getMonthlyCharge());
//        numList.setText(String.valueOf(flags[position]));

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//                /* I am not sure what I am doing with this one*/
//            }
//        });
        return view;
    }
}
