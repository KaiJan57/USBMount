package com.kai_jan_57.usbmount;

import android.content.Context;
import android.content.Intent;

public class DetachedReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        USBStorage.Detached(context);
    }

}
