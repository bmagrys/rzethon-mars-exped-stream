package com.mobstation.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by lomo1 on 09.12.2016.
 */
public class TempDataStorage {

    private List<ReceivedDataObject> dataObject;
    private boolean sendJson = false;
    private String message;
    private String temp = "";
    private boolean keepReading = false;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS z");
    private Date tempDate;

    public TempDataStorage() {
        dataObject = new ArrayList<>();
    }

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
                temp += c;
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
        ReceivedDataObject temp = new ReceivedDataObject();
        List<String> items = Arrays.asList(this.temp.split("\\s*,\\s*"));
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
        temp.setName("Dev_01");
        for(int i = 0; i < items.size(); i++) {
            switch(i) {
                case 0:
                    temp.setPositionX(items.get(i));
                    break;
                case 1:
                    temp.setPositionY(items.get(i));
                    break;
                case 2:
                    temp.setTemperature(items.get(i));
                    break;
                case 3:
                    temp.setHumidity(items.get(i));
                    break;
                case 4:
                    temp.setLightIntensity(items.get(i));
                    break;
                case 5:
                    temp.setVibrations(items.get(i));
                    break;
                case 6:
                    temp.setGasConcentration(items.get(i));
                    break;
            }
        }
        tempDate = new Date();
        temp.setTimeStamp(dateFormat.format(tempDate));
        dataObject.add(temp);
    }

    public List<ReceivedDataObject> getJsonToSend() {
        if(sendJson) {
            sendJson = false;
            return dataObject;
        } else
            return null;
    }

    public void removeObjectFromList(ReceivedDataObject rdo) {
        dataObject.remove(rdo);
    }
}
