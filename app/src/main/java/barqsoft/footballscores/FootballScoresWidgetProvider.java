package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import barqsoft.footballscores.service.FootballWidgetIntentService;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by rclark on 12/10/2015.
 */
public class FootballScoresWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context ctx, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(ctx, appWidgetManager, appWidgetIds);

        //start the intent service
        Intent svcintent = new Intent(ctx, FootballWidgetIntentService.class);
        ctx.startService(svcintent);
    }

    @Override
    public void onReceive(@NonNull Context ctx, @NonNull Intent intent) {
        super.onReceive(ctx, intent);
        if (myFetchService.FOOTBALL_DATA_UPDATED.equals(intent.getAction())) {
            //start the intent service
            ctx.startService(new Intent(ctx, FootballWidgetIntentService.class));
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        //do nothing for now
    }
}
