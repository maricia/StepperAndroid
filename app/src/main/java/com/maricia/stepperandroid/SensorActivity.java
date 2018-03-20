package com.maricia.stepperandroid;

import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    ListView sensorlist;
    TextView sensorCountTextView;

    TextView sensorNameDisplay;
    TextView sensorVendorDisplay;
    TextView sensorVersionDisplay;
    TextView sensorTypeDisplay;
    TextView sensorMaxRangeDisplay;
    TextView sensorResolutionDisplay;
    TextView sensorPowerDisplay;
    TextView sensorMinDelayDisplay;


    private static String TAG = "SensoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
 */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.d(TAG, "onCreate: " + deviceSensors);

        ArrayList<String> list = new ArrayList<String>();
        for(Sensor temp : deviceSensors){
            Log.d(TAG, "onCreate: temp " + temp.getName());
            String name = temp.getName();
            String vendor = temp.getVendor();
            Integer version = temp.getVersion();
            Integer type = temp.getType();
            float maxRange = temp.getMaximumRange();
            float resolution = temp.getResolution();
            float power = temp.getPower();
            Integer minDelay = temp.getMinDelay();
            list.add("name: " + name + "\nvendor: " + vendor + "\nType: " +String.valueOf(version) + "\nMax Range: " + String.valueOf(maxRange) + "\nResolution: " + String.valueOf(resolution) +"\nPower: " + String.valueOf(power) + "\nMin Delay: " + String.valueOf(minDelay) );
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);//deviceSensors
        sensorlist = (ListView) findViewById(R.id.sensorslist);
        sensorCountTextView = this.findViewById(R.id.senorsCountTextView);
        sensorCountTextView.setText(String.valueOf(deviceSensors.size()));
        sensorlist.setAdapter(adapter);

    }

    private void GetViews() {
        sensorNameDisplay = (TextView) findViewById(R.id.sensorNameDisplay);
        sensorVendorDisplay = (TextView) findViewById(R.id.sensorVendorDisplay);
        sensorVersionDisplay = (TextView) findViewById(R.id.sensorVersionDisplay);
        sensorTypeDisplay = (TextView) findViewById(R.id.sensorTypeDisplay);
        sensorMaxRangeDisplay = (TextView) findViewById(R.id.sensorMaxRangeDisplay);
        sensorResolutionDisplay = (TextView) findViewById(R.id.sensorResolutionDisplay);
        sensorPowerDisplay = (TextView) findViewById(R.id.sensorPowerDisplay);
        sensorMinDelayDisplay = (TextView) findViewById(R.id.sensorMinDelayDisplay);
    }



}
