package com.mobstation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.mobstation.communication.BluetoothManager;
import com.mobstation.communication.RESTManager;
import com.mobstation.data.MockData;
import com.mobstation.data.ReceivedDataObject;
import com.mobstation.data.TempDataStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private BluetoothManager bMgr;
    private ExpendableListAdapter adapter;
    private ExpandableListView expendableListView;
    private List<String> headerListItems = new ArrayList<>();
    private HashMap<String, List<String>>  listItems = new HashMap<>();
    private TextView textView;
    private TempDataStorage tempDataStorage;
    private RESTManager restMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobstation.R.layout.activity_main);
        expendableListView = (ExpandableListView) findViewById(R.id.transferHistoryListView);
        textView = (TextView) findViewById(R.id.deviceTextView);


        adapter = new ExpendableListAdapter(this,
                headerListItems,
                listItems);

        expendableListView.setAdapter(adapter);

        tempDataStorage = new TempDataStorage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bMgr = new BluetoothManager(this);
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new MockData(this), 0, 2, TimeUnit.SECONDS);
    }

    public void addToListView(String messageToAdd) {
        tempDataStorage.appendMessage(messageToAdd);
        List<ReceivedDataObject> tempList = tempDataStorage.getJsonToSend();
        this.processTempDataStorageList(tempList);
    }

    public void mockDataProcess(List<ReceivedDataObject> tempList) {
        this.processTempDataStorageList(tempList);
    }

    private void processTempDataStorageList(List<ReceivedDataObject> tempList) {
        if(tempList != null) {
            for (int i = 0; i < tempList.size(); i++) {
                textView.setText(tempList.get(i).getName());

                headerListItems.add(tempList.get(i).getName() + "     at: " + tempList.get(i).getTimeStamp());
                listItems.put(tempList.get(i).getName() + "     at: " + tempList.get(i).getTimeStamp(), prepareChildElement(tempList.get(i)));

                restMgr = new RESTManager(tempList.get(i).toString());
                restMgr.start();
                tempDataStorage.removeObjectFromList(tempList.get(i));

                if(headerListItems.size() > 2000) {
                    listItems.remove(headerListItems.get(0));
                    headerListItems.remove(0);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    private List<String> prepareChildElement(ReceivedDataObject rdo) {
        List<String> temp = new ArrayList<>();
        temp.add("X position: " + rdo.getPositionX());
        temp.add("Y position: " + rdo.getPositionY());
        temp.add("Temperature: " + rdo.getTemperature());
        temp.add("Humidity: " + rdo.getHumidity());
        temp.add("Light intensity: " + rdo.getLightIntensity());
        temp.add("Vibrations: " + rdo.getVibrations());
        temp.add("Gas concentration: " + rdo.getGasConcentration());
        return temp;
    }
}
