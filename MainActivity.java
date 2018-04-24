package lab.standards.bonjour;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.BubbleSeekBar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_OPEN = "lab.standards.bonjour.EXTRA_OPEN";

    public static AlarmManager alarmManager;
    public static PendingIntent pendingIntent;

    Toolbar toolbar;
    BubbleSeekBar hoursBar;
    BubbleSeekBar minutesBar;
    TextView textTime;
    Button cancel;
    Button save;

    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_menu));

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        hoursBar = findViewById(R.id.barHour);
        minutesBar = findViewById(R.id.barMinute);
        textTime = findViewById(R.id.textTime);

        hoursBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                String progressive = String.valueOf(progress);
                String progressed = String.valueOf(minutesBar.getProgress());
                if (progress < 10) {
                    progressive = "0" + progressive;
                }
                if (minutesBar.getProgress() < 10) {
                    progressed = "0" + progressed;
                }
                textTime.setText(String.valueOf(progressive + ":" + progressed));
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
            }
        });

        minutesBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                String progressive = String.valueOf(progress);
                String progressed = String.valueOf(hoursBar.getProgress());
                if (progressive.length() == 1) {
                    progressive = "0" + progress;
                } else {
                    progressive = String.valueOf(progress);
                }
                if (progressed.length() == 1) {
                    progressed = "0" + hoursBar.getProgress();
                }
                textTime.setText(progressed + ":" + progressive);

            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (alarmManager != null)
                    alarmManager.cancel(pendingIntent);
                finish();

            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, hoursBar.getProgress());
                calendar.set(Calendar.MINUTE, minutesBar.getProgress());

                intent.putExtra(EXTRA_OPEN, "start");
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(getApplicationContext(), "Time set at: " + textTime.getText(), Toast.LENGTH_LONG).show();

                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_open):
                Intent open = new Intent(MainActivity.this, openActivity.class);
                startActivity(open);
                return true;
            case (R.id.action_about):
                Intent about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
