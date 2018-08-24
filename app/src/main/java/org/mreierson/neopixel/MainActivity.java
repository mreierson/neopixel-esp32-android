package org.mreierson.neopixel;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    public static final String TAG = "NeoPixel";

    private Map<String,Class> mActivityMap = new HashMap<String,Class>();
    private ArrayAdapter<String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActivityMap.put("Off", null);
        mActivityMap.put("Solid", SolidActivity.class);
        mActivityMap.put("Solid 2", Solid2Activity.class);
        mActivityMap.put("Solid 3", Solid3Activity.class);
        mActivityMap.put("Cylon", CylonActivity.class);
        mActivityMap.put("Pulse", PulseActivity.class);

        ListView listView = (ListView) findViewById(R.id.listView);

        String[] effects = mActivityMap.keySet().toArray(new String[] {});

        mListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                new String[] { "Off", "Solid", "Solid 2", "Solid 3", "Cylon", "Pulse" });

        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String effect = mListAdapter.getItem(position);

        if ("off".equalsIgnoreCase(effect)) {
            try {
                Messaging.sendOff(0, 288);
            } catch (Exception ex) {
                Log.e(MainActivity.TAG, "", ex);
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            if (mActivityMap.containsKey(effect)) {
                Class activityClass = mActivityMap.get(effect);
                if (activityClass != null) {
                    startActivity(new Intent(this, activityClass));
                }
            }
        }
    }
}
