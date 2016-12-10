package com.mobstation.communication;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * Created by lomo1 on 09.12.2016.
 */
public class BluetoothManager extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private final int ACTION_REQUEST_BT = 10;

    public BluetoothManager() {
        this.bluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            //device doesn't support bluetooth
            //close application
            System.exit(0);
        }

        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ACTION_REQUEST_BT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTION_REQUEST_BT && resultCode == RESULT_OK) {
            //bluetooth wlaczony
        } else {
            System.exit(0);
        }
    }
}
