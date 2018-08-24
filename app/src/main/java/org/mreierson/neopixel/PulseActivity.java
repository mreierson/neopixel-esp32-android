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

public class PulseActivity extends AppCompatActivity {

    private int mRed0, mGreen0, mBlue0;
    private int mRed1, mGreen1, mBlue1;

    private EditText mStepEditText;
    private EditText mDelayEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse);

        setTitle(getTitle() + " - Pulse");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStepEditText = (EditText) findViewById(R.id.etPulseStep);
        mDelayEditText = (EditText) findViewById(R.id.etPulseDelay);

        final Button selectColor1 = (Button) findViewById(R.id.pulseSelectColor1);
        selectColor1.setBackgroundColor(Color.rgb(mRed0, mGreen0, mBlue0));

        selectColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(PulseActivity.this, mRed0, mGreen0, mBlue0);
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

        final Button selectColor2 = (Button) findViewById(R.id.pulseSelectColor2);
        selectColor2.setBackgroundColor(Color.rgb(mRed1, mGreen1, mBlue1));

        selectColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final ColorPicker cp =  new ColorPicker(PulseActivity.this, mRed1, mGreen1, mBlue1);
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


        final Button apply = (Button) findViewById(R.id.pulseApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    int step = Integer.parseInt(mStepEditText.getText().toString());
                    int delay = Integer.parseInt(mDelayEditText.getText().toString());

                    Messaging.sendPulse(0, 288, mRed0, mGreen0, mBlue0, mRed1, mGreen1, mBlue1, step, delay);

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

        editor.putInt("Pulse.Red0", mRed0);
        editor.putInt("Pulse.Green0", mGreen0);
        editor.putInt("Pulse.Blue0", mBlue0);
        editor.putInt("Pulse.Red1", mRed1);
        editor.putInt("Pulse.Green1", mGreen1);
        editor.putInt("Pulse.Blue1", mBlue1);
        editor.putString("Pulse.Step", mStepEditText.getText().toString());
        editor.putString("Pulse.Delay", mDelayEditText.getText().toString());
        editor.commit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);

        mRed0 = prefs.getInt("Pulse.Red0", 0);
        mGreen0 = prefs.getInt("Pulse.Green0", 0);
        mBlue0 = prefs.getInt("Pulse.Blue0", 0);

        mRed1 = prefs.getInt("Pulse.Red1", 0);
        mGreen1 = prefs.getInt("Pulse.Green1", 0);
        mBlue1 = prefs.getInt("Pulse.Blue1", 0);
        mStepEditText.setText(prefs.getString("Pulse.Step", "1"));
        mDelayEditText.setText(prefs.getString("Pulse.Delay", "25"));

        final Button selectColor1 = (Button) findViewById(R.id.pulseSelectColor1);
        selectColor1.setBackgroundColor(Color.rgb(mRed0, mGreen0, mBlue0));

        final Button selectColor2 = (Button) findViewById(R.id.pulseSelectColor2);
        selectColor2.setBackgroundColor(Color.rgb(mRed1, mGreen1, mBlue1));
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
