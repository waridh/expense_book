package com.example.waridh_expbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentEditEntry#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditEntry extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_NAME = "param1";
    private static final String ARG_MONTH_STARTED = "param2";
    private static final String ARG_MONTHLY_CHARGE = "param3";
    private static final String ARG_COMMENT = "param4";
    private static final String ARG_COMMENT_CHECK = "param5";

    // TODO: Rename and change types of parameters
    private String fName;
    private String fMonthStarted;
    private String fMonthlyExpense;
    private String fComment;
    private boolean fCommentFlag;

    public FragmentEditEntry() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name The name of the expense in string form
     * @param monthStarted The month started with the format of yyyy-mm in string form.
     * @param monthlyCharge The monthly charge in string form
     * @return A new instance of fragment FragmentEditEntry.
     */
    public static FragmentEditEntry newInstance(
            String name,
            String monthStarted,
            String monthlyCharge
            ) {
        FragmentEditEntry fragment = new FragmentEditEntry();
        Bundle args = new Bundle();

        /* Bundling the arguments that is required to open up the fragment */
        args.putString(ARG_NAME, name);
        args.putString(ARG_MONTH_STARTED, monthStarted);
        args.putString(ARG_MONTHLY_CHARGE, monthlyCharge);

        /* In this case, there is no comment */
        args.putByte(ARG_COMMENT_CHECK, (byte)0);   // Using byte like a boolean. 0 if no comment

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fName = getArguments().getString(ARG_NAME);
            fMonthStarted = getArguments().getString(ARG_MONTH_STARTED);
            fMonthlyExpense = getArguments().getString(ARG_MONTHLY_CHARGE);
            /* Comment existence check. */
            if (getArguments().getByte(ARG_COMMENT_CHECK) == 0) fCommentFlag = false;
            else {
                fCommentFlag = true;
                fComment = getArguments().getString(ARG_COMMENT);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_entry, container, false);
    }
}