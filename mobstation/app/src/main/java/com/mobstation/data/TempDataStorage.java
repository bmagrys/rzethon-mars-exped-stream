package com.mobstation.data;

/**
 * Created by lomo1 on 09.12.2016.
 */
public class TempDataStorage {

    private String jsonToSend;
    private boolean sendJson = false;
    private String message;
    private String temp = "";
    private boolean keepReading = false;

    public void appendMessage(String message) {
        this.message = message;
        this.getJsonFromTmpMsg();
    }

    private void getJsonFromTmpMsg() {
        for(char c : message.toCharArray()) {
            if(c == '{') {
                keepReading = true;
            } else if(c == '}') {
                keepReading = false;
                jsonToSend = temp + c;
                sendJson = true;
                temp = "";
            }
            if(keepReading == true) {
                temp += c;
            }
        }
        message = "";
    }

    public String getJsonToSend() {
        if(sendJson) {
            String temp = jsonToSend;
            sendJson = false;
            jsonToSend = "";
            return temp;
        } else
            return null;
    }
}
