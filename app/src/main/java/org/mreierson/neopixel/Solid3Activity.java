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

public class Solid3Activity extends AppCompatActivity
{
    private int mRed0, mGreen0, mBlue0;
    private int mRed1, mGreen1, mBlue1;
    private int mRed2, mGreen2, mBlue2;

    private EditText mDelayEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solid3);

        setTitle(getTitle() + " - Solid3");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDelayEditText = (EditText) findViewById(R.id.etSolid3Delay);

        final Button selectColor1 = (Button) findViewById(R.id.solid3SelectColor1);
        selectColor1.setBackgroundColor(Color.rgb(mRed0, mGreen0, mBlue0));

        selectColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(Solid3Activity.this, mRed0, mGreen0, mBlue0);
                cp.show();

                final Button okButton = (Button) cp.findViewById(R.id.okColorButton);
                okButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mRed0 = cp.getRed();
                        mGreen0 = cp.getGreen();
                        mBlue0 = cp.getBlue();

                        selectColor1.setBackgroundColor(Color.rgb(mRed0, mGreen0, mBlue0));

                        cp.dismiss();

                    }
                });
            }
        });

        final Button selectColor2 = (Button) findViewById(R.id.solid3SelectColor2);
        selectColor2.setBackgroundColor(Color.rgb(mRed1, mGreen1, mBlue1));

        selectColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(Solid3Activity.this, mRed1, mGreen1, mBlue1);
                cp.show();

                final Button okButton = (Button) cp.findViewById(R.id.okColorButton);
                okButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mRed1 = cp.getRed();
                        mGreen1 = cp.getGreen();
                        mBlue1 = cp.getBlue();

                        selectColor2.setBackgroundColor(Color.rgb(mRed1, mGreen1, mBlue1));

                        cp.dismiss();

                    }
                });
            }
        });

        final Button selectColor3 = (Button) findViewById(R.id.solid3SelectColor3);
        selectColor3.setBackgroundColor(Color.rgb(mRed2, mGreen2, mBlue2));

        selectColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(Solid3Activity.this, mRed2, mGreen2, mBlue2);
                cp.show();

                final Button okButton = (Button) cp.findViewById(R.id.okColorButton);
                okButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mRed2 = cp.getRed();
                        mGreen2 = cp.getGreen();
                        mBlue2 = cp.getBlue();

                        selectColor3.setBackgroundColor(Color.rgb(mRed2, mGreen2, mBlue2));

                        cp.dismiss();

                    }
                });
            }
        });

        final Button apply = (Button) findViewById(R.id.solid3Apply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {

                    int delay = Integer.parseInt(mDelayEditText.getText().toString());

                    Messaging.sendSolid3(0, 288, mRed0, mGreen0, mBlue0, mRed1, mGreen1, mBlue1, mRed2, mGreen2, mBlue2, delay);

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

        editor.putInt("Solid3.Red0", mRed0);
        editor.putInt("Solid3.Green0", mGreen0);
        editor.putInt("Solid3.Blue0", mBlue0);
        editor.putInt("Solid3.Red1", mRed1);
        editor.putInt("Solid3.Green1", mGreen1);
        editor.putInt("Solid3.Blue1", mBlue1);
        editor.putInt("Solid3.Red2", mRed2);
        editor.putInt("Solid3.Green2", mGreen2);
        editor.putInt("Solid3.Blue2", mBlue2);
        editor.putString("Solid3.Delay", mDelayEditText.getText().toString());

        editor.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        mRed0 = prefs.getInt("Solid3.Red0", 0);
        mGreen0 = prefs.getInt("Solid3.Green0", 0);
        mBlue0 = prefs.getInt("Solid3.Blue0", 0);

        mRed1 = prefs.getInt("Solid3.Red1", 0);
        mGreen1 = prefs.getInt("Solid3.Green1", 0);
        mBlue1 = prefs.getInt("Solid3.Blue1", 0);

        mRed2 = prefs.getInt("Solid3.Red2", 0);
        mGreen2 = prefs.getInt("Solid3.Green2", 0);
        mBlue2 = prefs.getInt("Solid3.Blue2", 0);

        mDelayEditText.setText(prefs.getString("Solid3.Delay", "0"));

        final Button selectColor1 = (Button) findViewById(R.id.solid3SelectColor1);
        selectColor1.setBackgroundColor(Color.rgb(mRed0, mGreen0, mBlue0));

        final Button selectColor2 = (Button) findViewById(R.id.solid3SelectColor2);
        selectColor2.setBackgroundColor(Color.rgb(mRed1, mGreen1, mBlue1));

        final Button selectColor3 = (Button) findViewById(R.id.solid3SelectColor3);
        selectColor3.setBackgroundColor(Color.rgb(mRed2, mGreen2, mBlue2));
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
