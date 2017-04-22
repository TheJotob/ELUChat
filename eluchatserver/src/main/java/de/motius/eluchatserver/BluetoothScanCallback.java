package de.motius.eluchatserver;

import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class BluetoothScanCallback extends ScanCallback {
    private static final String LOG_TAG = "ScanCallback";

    private Context context;

    public BluetoothScanCallback (Context context) {
        this.context = context;
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        if(result.getDevice().getName() != null)
            Log.e(LOG_TAG, result.getDevice().getName());

        ConnectionCallback callback = new ConnectionCallback(context);
        result.getDevice().connectGatt(context, true, callback);

        super.onScanResult(callbackType, result);
    }

    @Override
    public void onBatchScanResults(List<ScanResult> results) {
        Log.e(LOG_TAG, results.toString());
        super.onBatchScanResults(results);
    }

    @Override
    public void onScanFailed(int errorCode) {
        Log.e(LOG_TAG, "Error: " + errorCode);
        super.onScanFailed(errorCode);
    }
}
