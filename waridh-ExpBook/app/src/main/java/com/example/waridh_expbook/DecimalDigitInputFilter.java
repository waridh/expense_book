package com.example.waridh_expbook;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitInputFilter implements InputFilter {
    Pattern decimalPattern;
    public DecimalDigitInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        this.decimalPattern = Pattern.compile(
                "[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?"
        );
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher=decimalPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }
}
