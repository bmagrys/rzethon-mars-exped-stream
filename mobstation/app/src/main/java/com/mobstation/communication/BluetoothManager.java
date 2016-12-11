package com.mobstation.communication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import com.mobstation.MainActivity;

public class BluetoothManager extends Activity {

    private MainActivity mainActivity;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothConnectionHandler bluetoothConnectionHandler;
    private final int ACTION_REQUEST_BT = 10;

    public BluetoothManager(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

        this.bluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            System.exit(0);
        }
        System.out.println("BluetoothAdapter created.");

        if(!bluetoothAdapter.isEnabled()) {
            System.out.println("BluetoothAdapter not enabled.");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ACTION_REQUEST_BT);
        } else {
            System.out.println("BluetoothAdapter enabled.");
            bluetoothConnectionHandler = new BluetoothConnectionHandler(bluetoothAdapter, this.mainActivity);
            bluetoothConnectionHandler.start();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTION_REQUEST_BT && resultCode == RESULT_OK) {
            bluetoothConnectionHandler = new BluetoothConnectionHandler(bluetoothAdapter, mainActivity);
            bluetoothConnectionHandler.start();
        } else {
            System.exit(0);
        }
    }
}
