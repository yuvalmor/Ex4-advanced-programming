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

public class Client extends AsyncTask<ClientParams, Void, String> implements Serializable {

    // call CloseStream from onDestroy in JoystickActivity
    private DataOutputStream out;
    //private BufferedReader in;
    private Socket socket;
    private BufferedReader read;
    private String command;
    private String ip;
    private int port;
    private InetAddress server;

    public Client() { }

    @Override
    public String doInBackground(ClientParams...params) {
        String ip = params[0].getIP();
        int port = params[0].getPort();
        this.ip = ip;
        this.port = port;
        connect(ip,port);
        return "success";
    }

    public void connect(String ip, int port) {
        System.out.println("begin connection..\r\n");
        while (true) {
            try {
                server = InetAddress.getByName(ip);

                this.socket = new Socket(server, port);
                try {
                    out = new DataOutputStream(this.socket.getOutputStream());
                    System.out.println("got outputstream\r\n");
                } catch (Exception e) {
                    System.out.println("error.\r\n");
                }
                finally {
                    socket.close();
                }

                System.out.println("after socket..\r\n");
                break;

            } catch (Exception e) {
                System.out.println("error.\r\n");
            }
        }
    }

    public void closeStream() {
        try {
            this.out.close();
            this.socket.close();
        } catch (Exception e) {
            System.out.println("error.\r\n");
        }
    }

    public void writeToSimulator(String data, double value) {

        System.out.println("begin write..\r\n");
        getCommand(data, value);
        System.out.println("line is.." + "\r\n"+ command+"\r\n");
        String message="";
        byte[] bytes = command.getBytes();
        System.out.println("got command in bytes\r\n");
        /*
        try {
            out = new DataOutputStream(this.socket.getOutputStream());
            System.out.println("got outputstream\r\n");
        } catch (Exception e) {
            System.out.println("error.\r\n");
        }*/

        while (true) {
            try {
                this.socket = new Socket(server, this.port);
                try {
                    out = new DataOutputStream(this.socket.getOutputStream());
                    System.out.println("got outputstream\r\n");

                    if (this.out != null) {
                        try {
                            this.out.write(bytes);
                            System.out.println("wrote bytes...\r\n");
                            this.out.flush();
                        } catch (Exception e) {
                            System.out.println("error.\r\n");
                        }

                        System.out.println("go to read..\r\n");
                        //message = readFromSimulator();
                    }

                } catch (Exception e) {
                    System.out.println("error.\r\n");
                }
                finally {
                    socket.close();
                }

                System.out.println("after socket..\r\n");
                break;

            } catch (Exception e) {
                System.out.println("error.\r\n");
            }
        }



        System.out.println("wrote.." + message + "\r\n");
        //return message;
        command="";
    }

/*
    public String readFromSimulator() {
        String message="";
        System.out.println("begin read..\r\n");
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            char[] buffer = new char[200];
            in.read(buffer,0, buffer.length);
            message = new String(buffer);
            System.out.println("after read.." + message +"\r\n");

        }catch (Exception e) {
            System.out.println("error.\r\n");
        }
        return message;
    }
*/

    public void getCommand(String data, double value) {
         command = "set ";
        if (data.equals("aileron")) {
            command += "/controls/flight/aileron ";
        } else {
            command += "/controls/flight/elevator ";
        }
        command += Double.toString(value);
        command += "\r\n";
    }
}

class ClientParams {

    private String ip;
    private int port;

    ClientParams(String ip, int port) {
        this.ip=ip;
        this.port=port;
    }

    public String getIP() {
        return this.ip;
    }

    public int getPort(){
        return this.port;
    }
}

