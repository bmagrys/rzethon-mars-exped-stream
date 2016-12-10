package com.mobstation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.mobstation.communication.BluetoothManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BluetoothManager bMgr;
    private ArrayList<String> myStringArray1;
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobstation.R.layout.activity_main);
//        listView = (ListView) findViewById(R.id.transferHistoryListView);
        textView = (TextView) findViewById(R.id.curConDevTextView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bMgr = new BluetoothManager(this);
    }

    public void addToListView(String messageToAdd) {
        textView.setText(messageToAdd);
    }
}
