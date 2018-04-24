package lab.standards.bonjour;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class WakeUpService extends Service {

    NotificationManager notificationManager;
    static Ringtone ringtone;
    Notification notify;

    static MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String status = intent.getStringExtra(AlarmReceiver.EXTRA_STATE);


        Intent start = new Intent(WakeUpService.this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(WakeUpService.this, 0, start, 0);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notify = new Notification.Builder(this)
                .setContentTitle("Bonjour")
                .setContentText("It's time to wake up.")
                .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        assert status != null;
        System.out.println("We've got here another state " + status);
        switch (status) {
            /*case "stop":
                ringtone.stop();
                break;*/
            case "start":
                notificationManager.notify(0, notify);
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
                ringtone.play();
                break;
            default:
                break;
        }

        /*if (!this.isWakingUp && startId == 1) {
            mediaPlayer = mediaPlayer.create(this, R.raw.imagine);
            mediaPlayer.start();

            //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            //ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            //ringtone.play();

            notificationManager.notify(0, notify);
            this.isWakingUp = true;
            this.startId = 0;
        } else if (this.isWakingUp && startId == 1) {
            this.isWakingUp = true;
            this.startId = 0;
        } else {
            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isWakingUp = false;
            this.startId = 0;
        }*/

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        ringtone.stop();

    }

}
