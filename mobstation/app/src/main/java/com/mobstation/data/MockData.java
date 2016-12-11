package com.mobstation.data;

import com.mobstation.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lomo1 on 11.12.2016.
 */
public class MockData implements Runnable{

    private MainActivity mainActivity;

    public MockData(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {

        final List<ReceivedDataObject> tempDataStorage = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS z");
        Date tempDate;

        Random randomGenerator = new Random();

        for(int i = 0; i < 10; i++) {
            ReceivedDataObject temp = new ReceivedDataObject();
            temp.setName("Mock_Device_" + String.valueOf(i));

            tempDate = new Date();
            temp.setTimeStamp(dateFormat.format(tempDate));
            temp.setPositionX(String.valueOf((1000*randomGenerator.nextFloat())-500));
            temp.setPositionY(String.valueOf((1000*randomGenerator.nextFloat())-500));
            temp.setTemperature(String.valueOf((100*randomGenerator.nextFloat()-50)));
            temp.setHumidity(String.valueOf((40*randomGenerator.nextFloat())+20));
            temp.setLightIntensity(String.valueOf(randomGenerator.nextInt(1024)));
            temp.setVibrations(String.valueOf(randomGenerator.nextInt(25000)));
            temp.setGasConcentration(String.valueOf(randomGenerator.nextInt(1024)));

            tempDataStorage.add(temp);
        }

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.mockDataProcess(tempDataStorage);
            }
        });
    }
}
