package com.example.lenovo.calabashiland.View;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import base.App;
import tools.BrightnessUtil;
import tools.SharedPreferenceManager;

public class BaseActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerScreenListener();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (((int) event.values[0] < 5 && (int) event.values[1] < 5 && (int) event.values[2] <
                -2)) {
            BrightnessUtil.setBrightness(this, 1);
        } else {
            BrightnessUtil.setBrightness(this, 200);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void registerScreenListener() {
        if (sm == null)
            sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        int sensorType = Sensor.TYPE_ACCELEROMETER;
        if (TextUtils.equals(SharedPreferenceManager.getInstance(getApplicationContext()).getSavePower(),"关闭") ) {
            sm.registerListener(this, sm.getDefaultSensor(sensorType), SensorManager
                    .SENSOR_DELAY_NORMAL);
        }
    }

    private void unregisterScreenListener() {
        if (sm != null)
            sm.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterScreenListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
}
