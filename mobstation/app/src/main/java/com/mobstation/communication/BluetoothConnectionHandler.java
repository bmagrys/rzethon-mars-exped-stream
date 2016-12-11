package com.mobstation.communication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import com.mobstation.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by lomo1 on 10.12.2016.
 */
public class BluetoothConnectionHandler extends Thread {

    private MainActivity mainActivity;

    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    private BluetoothAdapter bluetoothAdapter;

    private InputStream inputStream;

    private static final UUID DEV_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private static final String DEVICE_NAME = "DEV_01";

    public BluetoothConnectionHandler(BluetoothAdapter adapter, MainActivity activity) {
        System.out.println("BluetoothConnectionHandler created.");
        this.bluetoothAdapter = adapter;
        this.mainActivity = activity;

        bluetoothDevice = this.findDevice(DEVICE_NAME, adapter);if(bluetoothDevice != null) {
            try {
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(DEV_UUID);
            } catch (IOException e) {
                System.exit(0);
            }
        }
    }

    public void run() {
        System.out.println("BluetoothConnectionHandler run.");
        try {
            bluetoothSocket.connect();
            inputStream = bluetoothSocket.getInputStream();
            listenForIncomingData();
        } catch (IOException e) {
            try {
                bluetoothSocket.close();
            } catch(IOException ex) {
                ex.printStackTrace();
                System.exit(0);
            }
        }
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch(IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private BluetoothDevice findDevice(String devName, BluetoothAdapter adapter) {
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices) {
                if(device.getName().equals(devName)) {
                    return device;
                }
            }
        }
        return null;
    }

    private void listenForIncomingData() {
        System.out.println("Listening for incoming data.");
        byte buffer[];
        buffer = new byte[2048];
        while(true) {
            try {
                int bytes = inputStream.read(buffer);
                if (bytes > 0) {
                    final String message = new String(buffer, 0, bytes);
                    mainActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.addToListView(message);
                        }
                    });
                }
            } catch (IOException e) {
                break;

            }
        }
    }
}
