package com.kai_jan_57.usbmount;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class UI {

    public static void showNoRootDialog(Context context) {
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab
                .setTitle(context.getString(R.string.alertdialog_noroot_title))
                .setMessage(context.getString(R.string.alertdialog_noroot_content))
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNeutralButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        ab.show();
    }

    public static class MountedNotification {

        Notification notification;
        NotificationManager notificationManager;
        PendingIntent eject;
        PendingIntent openPath;
        int notid;

        public MountedNotification(Context context, Class launch, String mountpoint, String device) {
            notid = (int) System.currentTimeMillis();
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent ejectx = new Intent(context, launch);
            ejectx.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ejectx.putExtra("device", device);
            ejectx.putExtra("mountpoint", mountpoint);
            eject = PendingIntent.getActivity(context, 1, ejectx, PendingIntent.FLAG_UPDATE_CURRENT);

            Intent openPathx = new Intent(Intent.ACTION_GET_CONTENT);
            openPathx.setDataAndType(Uri.parse(mountpoint), "*/*");
            openPathx.setAction(Intent.ACTION_VIEW);
            openPath = PendingIntent.getActivity(context, 1, Intent.createChooser(openPathx, context.getString(R.string.intent_selectfolder_title)), 0);

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle(context.getString(R.string.notification_eject_title));
            inboxStyle.addLine(context.getString(R.string.notification_eject_content1));
            inboxStyle.addLine(context.getString(R.string.notification_eject_content2));
            inboxStyle.addLine(context.getString(R.string.notification_eject_content3) + " " + device);
            inboxStyle.addLine(context.getString(R.string.notification_eject_content4) + " " + mountpoint);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.noti_usb)
                    .addAction(R.drawable.media_eject, context.getString(R.string.notification_eject_button), eject)
                    .setContentIntent(openPath)
                    .setStyle(inboxStyle);

            notification = builder.build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notificationManager.notify(notid, notification);
        }

        public void cancel() {
            notificationManager.cancel(notid);
        }
    }

    public static class MountingNotification {

        Notification notification;
        NotificationManager notificationManager;
        PendingIntent eject;
        int notid;

        public MountingNotification(Context context) {
            notid = (int) System.currentTimeMillis();
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.noti_usb)
                    .setContentTitle(context.getString(R.string.notification_mounting_title))
                    .setContentText(context.getString(R.string.notification_mounting_content));
            notification = builder.build();
            notification.flags |= Notification.FLAG_ONGOING_EVENT;
            notificationManager.notify(notid, notification);
        }

        public void cancel() {
            notificationManager.cancel(notid);
        }
    }

}
