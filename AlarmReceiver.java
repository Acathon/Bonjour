package lab.standards.bonjour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {

    public static final String EXTRA_STATE = "lab.standards.bonjour.EXTRA_STATE";

    String state;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, WakeUpService.class);

        if (!MainActivity.EXTRA_OPEN.isEmpty()) {
            state = intent.getStringExtra(MainActivity.EXTRA_OPEN);
        } else if (!SecondActivity.EXTRA_CLOSE.isEmpty()) {
            state = intent.getStringExtra(SecondActivity.EXTRA_CLOSE);
        } else if (SecondActivity.EXTRA_CLOSE.isEmpty()) {
            state = "stop";
        } else {
            state = "stop";
        }
        service.putExtra(EXTRA_STATE, state);

        context.startService(service);
    }
}
