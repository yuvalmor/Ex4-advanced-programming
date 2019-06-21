package com.example.ex4;
import android.os.AsyncTask;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Client extends AsyncTask<Void, Void, Void> implements Serializable {

    // call CloseStream from onDestroy in JoystickActivity
    private DataOutputStream out;
    private Socket socket;
    private InetAddress server;
    private LinkedBlockingQueue<String> data = new LinkedBlockingQueue<>();
    private int port;
    private String ip;
    private boolean stop = false;

    public Client() {}

    public void setClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Void doInBackground(Void...params) {

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
                        System.out.println("data size" + this.data.size() +"\r\n");
                        if (this.data.size() > 0) {
                            System.out.println("data not empty \r\n");
                            try {
                                this.out.write(this.data.take().getBytes());
                                System.out.println("after writing\r\n");
                                this.out.flush();
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
        return null;
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

    public void addDataToQueue(String data, double value) {
        try {
            this.data.put(getCommand(data, value));
            int size = this.data.size();
            System.out.println("data is added to list" + size+ "\r\n");
        } catch (Exception e) {
            System.out.println("error.\r\n");
        }
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
