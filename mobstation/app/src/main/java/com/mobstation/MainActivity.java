package com.mobstation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mobstation.communication.BluetoothManager;
import com.mobstation.communication.RESTManager;
import com.mobstation.data.TempDataStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BluetoothManager bMgr;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems = new ArrayList<>();
    private ListView listView;
    private TextView textView;
    private TempDataStorage tempDataStorage;
    private RESTManager restMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobstation.R.layout.activity_main);
        listView = (ListView) findViewById(R.id.transferHistoryListView);
        textView = (TextView) findViewById(R.id.curConDevTextView);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);

        tempDataStorage = new TempDataStorage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bMgr = new BluetoothManager(this);
    }

    public void addToListView(String messageToAdd) {
//        textView.setText(messageToAdd);
        tempDataStorage.appendMessage(messageToAdd);
        String temp = tempDataStorage.getJsonToSend();
        if (temp != null) {
            listItems.add(temp);
            adapter.notifyDataSetChanged();
            restMgr = new RESTManager(temp);
            restMgr.start();
        }
    }
}
