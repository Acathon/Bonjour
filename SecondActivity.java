package lab.standards.bonjour;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static lab.standards.bonjour.MainActivity.EXTRA_OPEN;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_CLOSE = "lab.standards.bonjour.EXTRA_CLOSE";

    DateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    String date;
    String time;

    TextView isTime;
    TextView isDate;
    Button dismiss;
    Button snooze;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final Intent intent = new Intent(SecondActivity.this, AlarmReceiver.class);

        time = timeFormat.format(Calendar.getInstance().getTime());
        isTime = findViewById(R.id.time_alarme);
        isTime.setText(time);

        date = dateFormat.format(Calendar.getInstance().getTime());
        isDate = findViewById(R.id.day_alarme);
        isDate.setText(date.toString());

        dismiss = findViewById(R.id.dismiss_alarme);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                stopService(new Intent(getBaseContext(), WakeUpService.class));
                finish();
            }
        });

        snooze = findViewById(R.id.snooze_alarme);
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(getBaseContext(), WakeUpService.class));
                intent.putExtra(EXTRA_OPEN, "start");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                //alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                //        2 * 60 * 60, pendingIntent);

                finish();
            }
        });


    }


}
