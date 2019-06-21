package com.example.ex4;
import android.os.AsyncTask;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

// Async class - using doInBackground method
public class Client extends AsyncTask<Void, Void, Void> implements Serializable {

    // members
    // connection fields
    private DataOutputStream out;
    private Socket socket;
    private InetAddress server;
    private int port;
    private String ip;
    // holds the commands waiting to be sent to the server
    private LinkedBlockingQueue<String> data = new LinkedBlockingQueue<String>();

    private boolean stop = false;

    // default ctor
    public Client() {}

    // setter
    public void setClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Function Name: doInBackground
     * Function Operation: connect to server via tcp
     * connection and run a while loop in which it writes
     * a string of command to the server from our queue.
     * @param params non
     * @return void
     */
    @Override
    public Void doInBackground(Void...params) {

        try {
            this.server = InetAddress.getByName(this.ip);
            try {
                this.socket = new Socket(this.server, this.port);
                this.out = new DataOutputStream(this.socket.getOutputStream());
                while (!stop) {
                    if ((this.out != null) && (this.data.size() > 0)) {
                        try {
                            this.out.write(this.data.take().getBytes());
                            this.out.flush();
                        } catch (Exception e) {
                            System.out.println("error....\r\n");
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

    /**
     * Function Name: closeStream.
     * Function Operation: changes 'stop' flag to true so the doInBackground
     * method will stop and closes the stream.
     */
    public void closeStream() {
        this.stop = true;
        try {
            this.out.close();
            this.socket.close();
        } catch (Exception e) {
            System.out.println("error.\r\n");

        }
    }

    /**
     * Function Name: addDataToQueue.
     * Function Operation: add command to the queue member.
     * @param data field name
     * @param value double
     */
    public void addDataToQueue(String data, double value) {

        try {
            this.data.put(getCommand(data, value));
        } catch (Exception e) {
            System.out.println("error.\r\n");
        }
    }

    /**
     * Function Name: getCommand
     * Function Operation: creating the string in the structure
     * that the simulator knows based on the parameter and value.
     * @param data parameter
     * @param value value
     * @return
     */

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
