package com.kai_jan_57.usbmount;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mount {

    public static List<Mount> mounts = new ArrayList<>();
    public static RootCommand cmd;
    public UI.MountedNotification notification;
    private String mountpoint;
    private String device;


    public Mount(String mountpoint, String device) throws Exception {
        this.mountpoint = mountpoint;
        this.device = device;
        checkInstance();
        //ntfs();
        cmd.writeString("mkdir \"" + mountpoint + "\"");
        cmd.writeString("chmod 777 \"" + mountpoint + "\"");
        cmd.writeString("mount -o rw,umask=0000 \"" + device + "\" \"" + mountpoint + "\"");
        notification = new UI.MountedNotification(Globals.AppContext, EjectActivity.class, mountpoint, device);
    }

    public static String[] getAvailableDevices(String devicepath) {
        try {
            checkInstance();
        } catch (Exception e) {
            Toast.makeText(Globals.AppContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            UI.showNoRootDialog(Globals.AppContext);
        }
        cmd.writeString("cd \"" + devicepath + "\"");
        cmd.writeString("ls");
        String[] devices = cmd.readAll();
        List<String> usbs = new ArrayList<>();
        for (String device : devices) {
            if (device.startsWith("sd") && containsDigit(device)) {
                usbs.add(device);
            }
        }
        Object[] objectArray = usbs.toArray();
        return Arrays.copyOf(objectArray, objectArray.length, String[].class);
    }

    public static void MountAll(String mountpoint, String devicepath) throws Exception {
        String[] devices;
        int trys = 12;
        while (true) {
            devices = getAvailableDevices(devicepath);
            if (devices.length - mounts.size() < 1) {
                Thread.sleep(500);
            } else {
                break;
            }
            if (trys < 1) {
                return;
            }
            trys--;
        }
        int i = 0;
        for (String devicef : devices) {
            i++;
            boolean exists = false;
            for (Mount mount : mounts) {
                if (devicef.equals(mount.getDevice())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                mounts.add(new Mount(mountpoint + String.valueOf(mounts.size() + i), devicef));
            }
        }
    }

    public static void UnMountNExist() {
        if (mounts.size() > 0) {
            for (Mount mount : new ArrayList<>(mounts)) {
                if (!mount.Exists()) {
                    mount.UnMount();
                }
            }
        }
    }

    private static boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }

    public static void checkInstance() throws Exception {
        if (cmd == null) {
            cmd = new RootCommand();
        }
    }

    public void UnMount() {
        try {
            checkInstance();
        } catch (Exception e) {
            Toast.makeText(Globals.AppContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            UI.showNoRootDialog(Globals.AppContext);
        }
        cmd.writeString("umount -l \"" + mountpoint + "\"");
        cmd.writeString("rm -rf \"" + mountpoint + "\"");
        notification.cancel();
        mounts.remove(this);
    }

    public boolean Exists() {
        try {
            checkInstance();
        } catch (Exception e) {
            Toast.makeText(Globals.AppContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            UI.showNoRootDialog(Globals.AppContext);
        }
        cmd.writeString("if test -e \"" + device + "\"; then echo e; else echo n;fi");
        return cmd.readString(false).equals("e");
    }

    public String getMountPoint() {
        return mountpoint;
    }

    public String getDevice() {
        return device;
    }

    /*public static void ntfs() {
        try {
            checkInstance();
        } catch (Exception e) {
            Toast.makeText(Globals.AppContext, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            UI.showNoRootDialog(Globals.AppContext);
        }
        Toast.makeText(Globals.AppContext, "1", Toast.LENGTH_SHORT).show();
        File ntfsexec = new File(Globals.AppContext.getFilesDir().toString() + "/ntfs-3g");
        Toast.makeText(Globals.AppContext, "2", Toast.LENGTH_SHORT).show();
        if (!ntfsexec.exists()) {
            prepNTFS();
            Toast.makeText(Globals.AppContext, "3", Toast.LENGTH_SHORT).show();
        }

        else {
            try {
                checkInstance();
            } catch (Exception e) {
                UI.showNoRootDialog(Globals.AppContext);
            }
            cmd.writeString("cd \"" + Globals.AppContext.getFilesDir() + "\"");
            cmd.writeString("ntfs-3g");
            if (cmd.readString(false).contains("not found")) {
                Toast.makeText(Globals.AppContext, "4", Toast.LENGTH_SHORT).show();
                prepNTFS();
            }
            Toast.makeText(Globals.AppContext, "5", Toast.LENGTH_SHORT).show();
        }
    }

    private static void prepNTFS() {
        File ntfsexec = new File(Globals.AppContext.getFilesDir().toString() + "/ntfs-3g");
        InputStream is = Globals.AppContext.getResources().openRawResource(R.raw.ntfs);
        try {
            ntfsexec.createNewFile();
            FileOutputStream fos = new FileOutputStream(Globals.AppContext.getFilesDir().toString() + "/ntfs-3g");
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.flush();
            fos.close();
        }
        catch (Exception e) {

        }
    }*/
}
