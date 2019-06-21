package com.example.ex4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity implements JoystickView.JoystickListener{
    // client member
    private Client client = new Client();

    /**
     * Function Name: onCreate
     * Function Operation: sets the activity on and sets the client
     * member with ip and port given from the login activity, then
     * calls connection method.
     * @param savedInstanceState - save the current state
     */
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

    /**
     * Function Name: onDestroy
     * Function Operation: calls closeStream to close connection
     * resources gracefully.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        this.client.closeStream();
    }

    /*
    adding normalized valued parameters the client queue
    we multiply elevator by -1 to adjust to screen axis.
     */
    @Override
    public void onJoystickMoved(float x, float y,int source, float radius) {
        client.addDataToQueue("aileron",normalizeX(x,radius));
        client.addDataToQueue("elevator",normalizeY(y,radius));
    }

    // normalize value by the radius to by -1 to 1.
    private float normalizeX(float value, float radius) {
        return value/radius;
    }

    // normalize value by the radius to by -1 to 1.
    private float normalizeY(float value, float radius) {
        float temp = value/radius;
        if (temp == 0) {
            return 0;
        }
        return -1*temp;
    }
}
