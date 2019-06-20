package com.example.ex4;

import android.app.Activity;
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
        int port = Integer.parseInt(((EditText)findViewById(R.id.portTxt)).getText().toString());
        DataParams params = new DataParams(ip,port);
        Client client = new Client(ip, port);
        client.execute();
        System.out.println("after execute..\r\n");
        Intent intent = new Intent(this,JoystickActivity.class);
        intent.putExtra("Client",client);
        startActivity(intent);
    }

}
