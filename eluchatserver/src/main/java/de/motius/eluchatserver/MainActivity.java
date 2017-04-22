package de.motius.eluchatserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ENABLE_BT = 0x01;
    public static final String LOG_TAG = "MainActivity";

    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // Get Bluetooth Manager to
        final BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothAdapter.enable();
        }
        bluetoothLeScan();
    }

    private void bluetoothLeScan() {
        mBluetoothAdapter.startDiscovery();
        BluetoothLeAdvertiser mAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        ParcelUuid pUuid = new ParcelUuid(UUID.randomUUID());

        AdvertiseSettings adSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .setConnectable(false)
                .build();

        AdvertiseData adData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .addServiceUuid(pUuid)
                .addServiceData(pUuid, "ELU".getBytes(Charset.forName("UTF-8")))
                .build();
        Log.e(LOG_TAG, adData.toString());
        Log.e(LOG_TAG, adData.getServiceData().toString());

        AdvertiseCallback adCallback = new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Log.i(LOG_TAG, "BLE Advertising succeeded");
                super.onStartSuccess(settingsInEffect);
            }

            @Override
            public void onStartFailure(int errorCode) {
                Log.e(LOG_TAG, "BLE Advertising failed. Code: " + errorCode);
                super.onStartFailure(errorCode);
            }
        };
        mAdvertiser.startAdvertising(adSettings, adData, adCallback);
        /*BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();
        if(scanner != null) {
            Log.i(LOG_TAG, "Scanning");
            Toast.makeText(this, "Scanning...", Toast.LENGTH_LONG).show();
            BluetoothScanCallback mScanCallback = new BluetoothScanCallback(this);
            scanner.startScan(mScanCallback);
        }*/
    }
}
