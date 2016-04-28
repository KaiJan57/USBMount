package com.kai_jan_57.usbmount;

import android.content.Context;
import android.content.Intent;

public class AttachedReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        USBStorage.Attached(context);
    }

}
