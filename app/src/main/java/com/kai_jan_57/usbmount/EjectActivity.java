package com.kai_jan_57.usbmount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class EjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(android.R.style.Theme_Material_Dialog_Alert);
        this.setContentView(R.layout.activity_eject);
        Mount.UnMountNExist();
        String device = getIntent().getStringExtra("device");
        String mountpoint = getIntent().getStringExtra("mountpoint");
        for (Mount mount : Mount.mounts) {
            if (mount.getDevice().equals(device) && mount.getMountPoint().equals(mountpoint)) {
                mount.UnMount();
                break;
            }
        }
        finish();
    }
}