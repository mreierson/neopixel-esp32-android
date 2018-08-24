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
import android.widget.Toast;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SolidActivity extends AppCompatActivity
{
    private int mCurrRed;
    private int mCurrGreen;
    private int mCurrBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid);

        setTitle(getTitle() + " - Solid");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button selectColor = (Button) findViewById(R.id.solidSelectColor);
        selectColor.setBackgroundColor(Color.rgb(mCurrRed, mCurrGreen, mCurrBlue));

        selectColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(SolidActivity.this, mCurrRed, mCurrGreen, mCurrBlue);
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

        final Button apply = (Button) findViewById(R.id.solidApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {

                    Messaging.sendSolid(0, 288, mCurrRed, mCurrGreen, mCurrBlue);

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

        editor.putInt("Solid.Red", mCurrRed);
        editor.putInt("Solid.Green", mCurrGreen);
        editor.putInt("Solid.Blue", mCurrBlue);
        editor.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        mCurrRed = prefs.getInt("Solid.Red", 0);
        mCurrGreen = prefs.getInt("Solid.Green", 0);
        mCurrBlue = prefs.getInt("Solid.Blue", 0);

        final Button selectColor = (Button) findViewById(R.id.solidSelectColor);
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
