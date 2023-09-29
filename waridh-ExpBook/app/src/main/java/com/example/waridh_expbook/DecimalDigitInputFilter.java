package com.example.waridh_expbook;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class was used to implement the input filter. Makes sure that the user cannot input more
 * than 2 decimal digits.
 */
public class DecimalDigitInputFilter implements InputFilter {
    Pattern decimalPattern;

    /**
     * Constructor that uses regex to match how the format of the money should be
     * @param digitsBeforeZero
     * @param digitsAfterZero
     */
    public DecimalDigitInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        this.decimalPattern = Pattern.compile(
                "[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?"
        );
    }

    /**
     * The filtering
     * @param source
     * @param start
     * @param end
     * @param dest
     * @param dstart
     * @param dend
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher=decimalPattern.matcher(dest);
        if(!matcher.matches())
            return "";
        return null;
    }
}
