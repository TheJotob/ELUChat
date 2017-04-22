package de.motius.eluchatserver;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.util.Log;

public class ConnectionCallback extends BluetoothGattCallback {
    private Context context;
    public static final String TAG = "ConnectionCallback";

    public ConnectionCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        Log.d(TAG, gatt.toString());
        super.onConnectionStateChange(gatt, status, newState);
    }
}
