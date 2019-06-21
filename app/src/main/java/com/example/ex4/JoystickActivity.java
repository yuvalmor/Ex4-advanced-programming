package com.example.ex4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class JoystickActivity extends AppCompatActivity implements JoystickView.JoystickListener{
    private Client client = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);
        String ip = (String) getIntent().getSerializableExtra("ip");
        int port = Integer.parseInt((String) getIntent().getSerializableExtra("port"));
        this.client.setClient(ip, port);
        this.client.execute();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.client.closeStream();
    }

    @Override
    public void onJoystickMoved(float x, float y,int source, float radius) {
        Log.d("FIRST", "X: " + x
                + " Y: " +y +"RADIUS:  "+ radius);
        Log.d("JOYSTICK ACTIVITY", "X: " + normalizeValue(x,radius)
                + " Y: " + normalizeValue(y,radius));
        client.addDataToQueue("aileron",normalizeValue(x,radius));
        client.addDataToQueue("elevator",normalizeValue(y,radius));
    }

    private float normalizeValue(float value, float radius) {
        return value/radius;
    }
}
