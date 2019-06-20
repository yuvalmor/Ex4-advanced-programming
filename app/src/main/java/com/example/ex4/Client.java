package com.example.ex4;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Client extends AsyncTask<Void, Void, String> implements Serializable {

    // call CloseStream from onDestroy in JoystickActivity
    private DataOutputStream out;
    private Socket socket;
    private InetAddress server;

    private List<String> data;
    private int port;
    private String ip;
    private boolean stop = false;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.data = new LinkedList<String>();
    }

    @Override
    public String doInBackground(Void...params) {

        try {
            this.server = InetAddress.getByName(this.ip);
            System.out.println("server is set\r\n");
            try {
                this.socket = new Socket(this.server, this.port);
                System.out.println("socket is set\r\n");
                this.out = new DataOutputStream(this.socket.getOutputStream());
                System.out.println("out is set\r\n");

                while (!stop) {
                    System.out.println("try to write..\r\n");
                    if ((this.out != null)) {
                        System.out.println("out not null..\r\n");
                        if (!(this.data.isEmpty())) {
                            System.out.println("data not empty \r\n");
                            try {
                                this.out.write(this.data.get(0).getBytes());
                                System.out.println("after writing\r\n");
                                this.out.flush();
                                this.data.remove(0);
                            } catch (Exception e) {
                                System.out.println("error....\r\n");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("error..\r\n");
            }

        } catch (Exception e) {
            System.out.println("error..\r\n");
        }

        return "success";
    }

    public void closeStream() {
        this.stop = true;
        try {
            this.out.close();
            this.socket.close();
        } catch (Exception e) {
            System.out.println("error.\r\n");
        }
    }

    public void addDataToList(String data, double value) {
        this.data.add(getCommand(data, value));
        System.out.println("data is added to list\r\n");
    }

    public String getCommand(String data, double value) {
        String command = "";
        command += "set ";
        if (data.equals("aileron")) {
            command += "/controls/flight/aileron ";
        } else {
            command += "/controls/flight/elevator ";
        }
        command += Double.toString(value);
        command += "\r\n";
        return command;
    }
}

class DataParams {

    private String type;
    private double value;

    DataParams(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public double getValue(){
        return this.value;
    }
}