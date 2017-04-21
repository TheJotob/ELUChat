package de.motius.eluchat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener {

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
        if(scanner != null) {
            Log.e(LOG_TAG, "Scanning");
            Toast.makeText(this, "Scanning...", Toast.LENGTH_LONG).show();
            MScanCallback mScanCallback = new MScanCallback();
            scanner.startScan(mScanCallback);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnScan)
            bluetoothLeScan();

        else if(v.getId() == R.id.btnSend) {
            EditText message = (EditText) findViewById(R.id.inputMessage);
            Toast.makeText(this, message.getText(), Toast.LENGTH_SHORT).show();
        }

    }
}
