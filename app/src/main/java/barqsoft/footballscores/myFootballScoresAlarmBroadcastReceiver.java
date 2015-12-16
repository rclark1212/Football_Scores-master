package barqsoft.footballscores;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import barqsoft.footballscores.service.myFetchService;

/**
 * Created by rclark on 12/15/2015.
 * Used to receive alarm events.
 */
public class myFootballScoresAlarmBroadcastReceiver extends BroadcastReceiver {
    public static final long DATA_UPDATE_ALARM_PERIOD = 60000;  //check for data every minute

    @Override
    public void onReceive(@NonNull Context ctx, @NonNull Intent intent) {
        //and kick off a fetch service
        Intent service_start = new Intent(ctx, myFetchService.class);
        ctx.startService(service_start);
    }

}
