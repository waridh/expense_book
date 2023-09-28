package com.example.waridh_expbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * All sub activity of the main activity has to be able to navigate back. This is a nice abstraction
 * to let that happen.
 */
public abstract class SubActivity extends BaseActivity {
    /**
     * This method is the default behaviour for the back button. Which is just to finish the current
     * activity.
     * @param view Needed for the android API.
     */
    public void returnToMain(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    /**
     * This method will return the activity to main along with passing back some data stored in a
     * bundle.
     * @param bundle The extra data that is being returned. In this app, it's likely going to be an
     *               Expense object.
     */
    protected void returnResultToMain(Bundle bundle) {
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    protected void sendResultToMain(Bundle bundle) {
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
    }

}
