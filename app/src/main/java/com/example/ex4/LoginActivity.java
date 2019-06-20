package com.example.ex4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void ToJoystickActivity(View view){
        String ip = ((EditText)findViewById(R.id.ipTxt)).getText().toString();
        String port = ((EditText)findViewById(R.id.portTxt)).getText().toString();
        System.out.println("try to make a client\r\n");
        Intent intent = new Intent(this,JoystickActivity.class);
        intent.putExtra("ip", ip);
        intent.putExtra("port", port);
        startActivity(intent);
    }
}
