package barqsoft.footballscores.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.FootballScoresWidgetProvider;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.WidgetListProvider;

/**
 * Created by rclark on 12/11/2015.
 *  * Adaptation from LAAPTU listview widget example.
 * https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
 */
public class FootballWidgetIntentService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return (new WidgetListProvider(this.getApplicationContext(), intent));
    }

}
