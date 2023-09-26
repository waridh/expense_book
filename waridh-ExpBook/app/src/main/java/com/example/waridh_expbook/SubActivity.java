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
    public void returnToMain(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    protected void returnResultToMain(Bundle bundle) {
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
