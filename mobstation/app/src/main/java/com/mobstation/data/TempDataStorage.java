package com.mobstation.data;

import java.util.Arrays;
import java.util.List;

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
                prepareJsonToSend();
                sendJson = true;
                temp = "";
            }
            if(keepReading == true) {
                temp += c;
            }
        }
        message = "";
    }

    private void prepareJsonToSend() {
        List<String> items = Arrays.asList(jsonToSend.split("\\s*,\\s*"));
        jsonToSend = "{\"name\":\"Dev_01\",";
        for(String s : items) {
            if(s.charAt(0) == '{') {
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(0);
                s = sb.toString();
                items.set(0, s);
            } else if (s.charAt(s.length()-1) == '}') {
                StringBuilder sb = new StringBuilder(s);
                sb.deleteCharAt(s.length()-1);
                s = sb.toString();
                items.set(items.size()-1, s);
            }
        }
        for(int i = 0; i < items.size(); i++) {
            switch(i) {
                case 0:
                    jsonToSend += "\"positionX\": " + items.get(i);
                    break;
                case 1:
                    jsonToSend += ",\"positionY\": " + items.get(i);
                    break;
                case 2:
                    jsonToSend += ",\"temperature\": " + items.get(i);
                    break;
                case 3:
                    jsonToSend += ",\"humidity\": " + items.get(i);
                    break;
                case 4:
                    jsonToSend += ",\"lightIntensity\": " + items.get(i);
                    break;
                case 5:
                    jsonToSend += ",\"vibrations\": " + items.get(i);
                    break;
                case 6:
                    jsonToSend += "," +
                            "\"gasConcentration\": " + items.get(i);
                    break;
            }
        }
        jsonToSend += "}";
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
