package com.example.ex4;

public class ClientParams {

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
