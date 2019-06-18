package com.example.ex4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);
       /* JoystickView jv = new JoystickView(this);
        setContentView(jv);*/
       JoystickView joystickView = new JoystickView(this);
       setContentView(joystickView);
       // need to get the client...
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}


