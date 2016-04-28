package com.kai_jan_57.usbmount;

import android.content.Context;
import android.widget.Toast;

public class USBStorage {

    public static void Attached(Context context) {
        Globals.AppContext = context;
        try {
            UI.MountingNotification mountingNotification = new UI.MountingNotification(Globals.AppContext);
            Mount.MountAll(Globals.SharedPrefs.getString(Globals.AppContext, Constants.PREF_MOUNTPOINT, Constants.DEFAULT_MOUNTPOINT), Globals.SharedPrefs.getString(Globals.AppContext, Constants.PREF_DEVICEPATH, Constants.DEFAULT_DEVICEPATH));
            mountingNotification.cancel();
        } catch (Exception e) {
            UI.showNoRootDialog(Globals.AppContext);
            Toast.makeText(Globals.AppContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void Detached(Context context) {
        Mount.UnMountNExist();
    }

}
