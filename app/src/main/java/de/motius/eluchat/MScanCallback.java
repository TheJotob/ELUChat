package de.motius.eluchat;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MScanCallback extends ScanCallback {
    private static final String LOG_TAG = "ScanCallback";

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        if(result.getDevice().getName() != null)
            Log.e(LOG_TAG, result.getDevice().getName());

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
