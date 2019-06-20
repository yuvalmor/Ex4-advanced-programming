package com.example.ex4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class JoystickActivity extends AppCompatActivity implements JoystickView.JoystickListener{
    private Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
        JoystickView joystickView = new JoystickView(this);
        setContentView(joystickView);
        client =(Client)getIntent().getSerializableExtra("Client");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onJoystickMoved(float x, float y,int source, float radius) {
        Log.d("FIRST", "X: " + x
                + " Y: " +y +"RADIUS:  "+ radius);
        Log.d("JOYSTICK ACTIVITY", "X: " + normallizeValue(x,radius)
                + " Y: " + normallizeValue(y,radius));
        client.addDataToList("aileron",normallizeValue(x,radius));
        client.addDataToList("elevator",normallizeValue(y,radius));

    }
    private float normallizeValue(float value, float radius) {
        return value/radius;
    }
}
