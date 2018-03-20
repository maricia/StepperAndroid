package com.maricia.stepperandroid;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.maricia.logger.Log;
import com.maricia.logger.LogView;
import com.maricia.logger.LogWrapper;
import com.maricia.logger.MessageOnlyLogFilter;

public class MainActivity extends AppCompatActivity {


    public static String TAG = "MainActivity";
    // State of application, used to register for sensors when app is restored
    public static final int STATE_OTHER = 0;
    public static final int STATE_COUNTER = 1;
    public static final int STATE_DETECTOR = 2;
    // State of the app (STATE_OTHER, STATE_COUNTER or STATE_DETECTOR)
    private int mState = STATE_OTHER;
    // When a listener is registered, the batch sensor delay in microseconds
    private int mMaxDelay = 0;
    // Value of the step counter sensor when the listener was registered.
    // (Total steps are calculated from this value.)
    private int mCounterSteps = 0;

    private Button stepStart;
    private TextView results;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeLogging();




    }//end onCreate

    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        private float mStepOffset;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (mStepOffset == 0) {
                mStepOffset = event.values[0];
            }
            results = findViewById(R.id.results);
            results.setText(Float.toString(event.values[0] - mStepOffset));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;



    }//end onCreate

    private void GetButtons(){
        stepStart = this.findViewById(R.id.stepStart);
    }




    //button
    public void onClick(View arg0){


        android.util.Log.d(TAG, "onClick: here I am");
        Log.i(TAG, "here I am ");


        registerEventListener(0,Sensor.TYPE_STEP_COUNTER);


        //stepStart.setEnabled(false); //disable the button


    }

    private void registerEventListener(int maxdelay, int sensorType) {
        android.util.Log.d(TAG, "registerEventListener: Now I am here");
        // BEGIN_INCLUDE(register)

        // Keep track of state so that the correct sensor type and batch delay can be set up when
        // the app is restored (for example on screen rotation).


        mMaxDelay = maxdelay;
        if (sensorType == Sensor.TYPE_STEP_COUNTER) {
            mState = STATE_COUNTER;
            /*
            Reset the initial step counter value, the first event received by the event listener is
            stored in mCounterSteps and used to calculate the total number of steps taken.
             */
            mCounterSteps = 0;

            Log.i(TAG, "Event listener for step counter sensor registered with a max delay of "+ mMaxDelay);
          // logResults.setText("Event listener for step counter sensor registered with a max delay of" + mMaxDelay);
        } else {
            mState = STATE_DETECTOR;
            Log.i(TAG, "Event listener for step detector sensor registered with a max delay of "
                    + mMaxDelay);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent sensorintent = new Intent(this, SensorActivity.class);
                startActivity(sensorintent);
                break;
        }//end switch

       /*
        if (id == R.id.action_read_data) {
            readData();
            return true;
        }
    */
        return super.onOptionsItemSelected(item);
    }


    /** Initializes a custom log class that outputs both to in-app targets and logcat. */
    private void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);
        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);
        // On screen logging via a customized TextView.
        LogView logView = (LogView) findViewById(R.id.logResults);

        // Fixing this lint error adds logic without benefit.
        // noinspection AndroidLintDeprecation
        logView.setTextAppearance(R.style.Log);

        logView.setBackgroundColor(Color.WHITE);
        msgFilter.setNext(logView);
        Log.i(TAG, "Ready");
    }

}
