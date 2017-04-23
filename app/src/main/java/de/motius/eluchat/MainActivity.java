package de.motius.eluchat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    public static final int REQUEST_ENABLE_BT = 0x01;
    public static final String LOG_TAG = "ClientMainActivity";

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
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        setContentView(R.layout.activity_main);

        Button btnScan = (Button) findViewById(R.id.btnScan);
        btnScan.setOnClickListener(this);

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    private void bluetoothLeScan() {
        BluetoothLeScanner scanner = mBluetoothAdapter.getBluetoothLeScanner();
        List<ScanFilter> filters = new ArrayList<>();

        ScanFilter scFilter = new ScanFilter.Builder()
                .setDeviceName("RPI3")
                .build();
        filters.add(scFilter);

        ScanSettings scSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();

        ScanCallback mScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                Log.e(LOG_TAG, result.toString());
                if (result.getDevice() == null
                        || TextUtils.isEmpty(result.getDevice().getName()))
                    return;

                Log.i(LOG_TAG, result.toString());
                //Handler handler = new Handler();
                //BluetoothChatService service = new BluetoothChatService(getApplicationContext(), handler);
                //service.start();
                //service.connect(result.getDevice(), true);
            }
        };
        scanner.startScan(filters, scSettings, mScanCallback);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnScan)
            bluetoothLeScan();

        else if (v.getId() == R.id.btnSend) {
            EditText message = (EditText) findViewById(R.id.inputMessage);
            Toast.makeText(this, message.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
