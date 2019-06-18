
package com.example.ex4;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends AsyncTask<ClientParams, Void, String> implements Serializable {

    // call CloseStream from onDestroy in JoystickActivity
    private PrintWriter buffer;
    private Socket socket;
    private BufferedReader read;

    public Client() { }

    @Override
    public String doInBackground(ClientParams...params) {
        String ip = params[0].getIP();
        int port = params[0].getPort();
        connect(ip,port);
        return "success";
    }

    public void connect(String ip, int port){
        System.out.println("ip: "+ ip);
        System.out.println("port: "+ port);
        while (true) {
            try {
                System.out.println("try to connect :)))\n");
                InetAddress server = InetAddress.getByName(ip);
                this.socket = new Socket(server, port);
                buffer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println("we are ok :)))\n");
                break;
            } catch (Exception e) {
                Log.e("error: ", e.getMessage());
            }
        }
    }

    public void closeStream() {
        buffer.close();
        try {
            this.socket.close();
        } catch (Exception e) {
            Log.e("error: ", e.getMessage());
        }
    }

    public void writeToSimulator(String data, float value) {
        String command = getCommand(data, value);

        try {
            if (buffer != null && !buffer.checkError()) {
                Log.d("send ", "send: " + command) ;
                buffer.println(command);
                String message = read.readLine();
                Log.d("got: ", "got: " + message) ;
                buffer.flush();
            }
        } catch (Exception e) {
            Log.e("error: ", e.getMessage());
        }
    }

    public String getCommand(String data, float value) {
        String command = "set ";
        if (data.equals("aileron")) {
            command += "/controls/flight/aileron ";
        } else {
            command += "/controls/flight/elevator ";
        }
        command += Float.toString(value);
        command += "\r\n";
        return command;
    }
}
