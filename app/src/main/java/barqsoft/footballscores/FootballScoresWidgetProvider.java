package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by rclark on 12/10/2015.
 */
public class FootballScoresWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context ctx, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            RemoteViews views = new RemoteViews(ctx.getPackageName(), R.layout.widget_football);

            Intent intent = new Intent(ctx, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], views);
        }
    }
}
