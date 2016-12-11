package com.mobstation.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lomo1 on 11.12.2016.
 */
public class ReceivedDataObject {

    private String name;
    private String positionX;
    private String positionY;
    private String temperature;
    private String humidity;
    private String lightIntensity;
    private String vibrations;
    private String gasConcentration;
    private String timeStamp;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(String lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public String getVibrations() {
        return vibrations;
    }

    public void setVibrations(String vibrations) {
        this.vibrations = vibrations;
    }

    public String getGasConcentration() {
        return gasConcentration;
    }

    public void setGasConcentration(String gasConcentration) {
        this.gasConcentration = gasConcentration;
    }

    @Override
    public String toString() {
        String temp = "{\"name\":\"" + name + "\"," +
                        "\"positionX\": " + positionX +
                        ",\"positionY\": " + positionY +
                        ",\"temperature\": " + temperature +
                        ",\"humidity\": " + humidity +
                        ",\"lightIntensity\": " + lightIntensity +
                        ",\"vibrations\": " + vibrations +
                        ",\"gasConcentration\": " + gasConcentration + "}";
        return temp;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null) {
            if(obj.getClass() == this.getClass()) {
                ReceivedDataObject temp = (ReceivedDataObject) obj;
                if(temp.toString().equals(this.toString())) {
                    return true;
                }
            }
        }
        return false;
    }
}
