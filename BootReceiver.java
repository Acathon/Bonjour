package lab.standards.bonjour;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BootReceiver extends BroadcastReceiver {
    long[] vibrate = {0, 100, 200, 300};

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent reminder = new Intent(context, MainActivity.class);
        reminder.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, reminder, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = new Notification.Builder(context)
                .setContentTitle("Bonsoir")
                .setContentText("Hey, it's time to set your alarm!")
                .setSmallIcon(R.drawable.bell)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        notify.vibrate = vibrate;
        notificationManager.notify(0, notify);
    }
}
