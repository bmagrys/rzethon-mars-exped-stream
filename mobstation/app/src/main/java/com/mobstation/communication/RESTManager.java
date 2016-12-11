package com.mobstation.communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lomo1 on 09.12.2016.
 */
public class RESTManager extends Thread {

    private String endpointAddress = "http://192.168.137.1:5001/event";
    private HttpURLConnection connection;
    private String messageToSend;

    public RESTManager(String message) {
        this.messageToSend = message;
        try {
            URL url = new URL(endpointAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod( "POST" );
            connection.setRequestProperty( "Content-Type", "application/json");
            connection.setDoOutput( true );
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(messageToSend);
            out.flush();
            out.close();
            int responseCode = connection.getResponseCode();
            System.out.println(String.valueOf(responseCode));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
