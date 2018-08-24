package org.mreierson.neopixel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class CylonActivity extends AppCompatActivity
{
    private int mCurrRed;
    private int mCurrGreen;
    private int mCurrBlue;

    private EditText mLengthEditText;
    private EditText mDelayEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cylon);

        setTitle(getTitle() + " - Cylon");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLengthEditText = (EditText) findViewById(R.id.etCylonLength);
        mDelayEditText = (EditText) findViewById(R.id.etCylonDelay);

        final Button selectColor = (Button) findViewById(R.id.cylonSelectColor);
        selectColor.setBackgroundColor(Color.rgb(mCurrRed, mCurrGreen, mCurrBlue));

        selectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(CylonActivity.this, mCurrRed, mCurrGreen, mCurrBlue);
                cp.show();

                final Button okButton = (Button) cp.findViewById(R.id.okColorButton);
                okButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mCurrRed = cp.getRed();
                        mCurrGreen = cp.getGreen();
                        mCurrBlue = cp.getBlue();

                        selectColor.setBackgroundColor(Color.rgb(mCurrRed, mCurrGreen, mCurrBlue));

                        cp.dismiss();

                    }
                });
            }
        });

        final Button apply = (Button) findViewById(R.id.cylonApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    int length = Integer.parseInt(mLengthEditText.getText().toString());
                    int delay = Integer.parseInt(mDelayEditText.getText().toString());

                    Messaging.sendCylon(0, 288, mCurrRed, mCurrGreen, mCurrBlue, length, delay);

                    setResult(Activity.RESULT_OK);
                    finish();

                } catch (Exception ex) {
                    Log.e(MainActivity.TAG, "", ex);
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("Cylon.Red", mCurrRed);
        editor.putInt("Cylon.Green", mCurrGreen);
        editor.putInt("Cylon.Blue", mCurrBlue);
        editor.putString("Cylon.Length", mLengthEditText.getText().toString());
        editor.putString("Cylon.Delay", mDelayEditText.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        mCurrRed = prefs.getInt("Cylon.Red", 0);
        mCurrGreen = prefs.getInt("Cylon.Green", 0);
        mCurrBlue = prefs.getInt("Cylon.Blue", 0);
        mLengthEditText.setText(prefs.getString("Cylon.Length", "255"));
        mDelayEditText.setText(prefs.getString("Cylon.Delay", "25"));

        final Button selectColor = (Button) findViewById(R.id.cylonSelectColor);
        selectColor.setBackgroundColor(Color.rgb(mCurrRed, mCurrGreen, mCurrBlue));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
