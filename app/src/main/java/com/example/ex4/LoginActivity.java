package com.example.ex4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    /**
     * Function Name: onCreate
     * Function Operation: sets the login activity on.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Function Name: ToJoystickActivity
     * Funtion Operation: get the ip,port values from the user
     * and pass to the Joystick activity and call the Joystick activity
     * onCreate method.
     * @param view JoystickView
     */
    public void ToJoystickActivity(View view){
        String ip = ((EditText)findViewById(R.id.ipTxt)).getText().toString();
        String port = ((EditText)findViewById(R.id.portTxt)).getText().toString();
        Intent intent = new Intent(this,JoystickActivity.class);
        intent.putExtra("ip", ip);
        intent.putExtra("port", port);
        startActivity(intent);
    }
}
