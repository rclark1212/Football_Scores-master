package barqsoft.footballscores;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import barqsoft.footballscores.service.FootballWidgetIntentService;

/**
 * Created by rclark on 12/10/2015.
 *
 * Container widget which will show all matches from today...
 *
 */
public class FootballScoresWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context ctx, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews remoteViews = updateWidgetListView(ctx,
                    appWidgetIds[i]);

            //set up a click intent for the title...
            Intent title_intent = new Intent(ctx, MainActivity.class);
            PendingIntent pendingTitleIntent = PendingIntent.getActivity(ctx, 0, title_intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_title, pendingTitleIntent);

            //set up a click intent for the list items
            Intent clickIntentTemplate = new Intent(ctx, MainActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(ctx).
                    addNextIntentWithParentStack(clickIntentTemplate).getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);

            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(ctx, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(@NonNull Context ctx, @NonNull Intent intent) {

        //notify that data set changed...
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(ctx);
        ComponentName cn = new ComponentName(ctx, FootballScoresWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(cn);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.widget_football);

        super.onReceive(ctx, intent);
    }

    private RemoteViews updateWidgetListView(Context ctx, int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(ctx.getPackageName(), R.layout.widget_football);

        //start the intent service
        Intent svcintent = new Intent(ctx, FootballWidgetIntentService.class);
        ctx.startService(svcintent);

        //creat service needed to provide adapter for ListView
        Intent svcIntent = new Intent(ctx, FootballWidgetIntentService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.widget_list, svcIntent);

        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.widget_list, R.id.empty_view);
        return remoteViews;
    }

}
