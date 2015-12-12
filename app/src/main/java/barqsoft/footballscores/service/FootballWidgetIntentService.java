package barqsoft.footballscores.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.FootballScoresWidgetProvider;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by rclark on 12/11/2015.
 */
public class FootballWidgetIntentService extends IntentService {

    public FootballWidgetIntentService()
    {
        super("FootballWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName cn = new ComponentName(getApplicationContext(), FootballScoresWidgetProvider.class);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(cn);

        if ((appWidgetIds != null) && (appWidgetManager != null)) {

            RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_football);

            for (int i = 0; i < appWidgetIds.length; i++) {

                Intent mainintent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mainintent, 0);
                views.setOnClickPendingIntent(R.id.widget, pendingIntent);

                upDateWidgetData(views);

                appWidgetManager.updateAppWidget(appWidgetIds[i], views);
            }
        }

        return;
    }

    //only one type of widget at this point
    private void upDateWidgetData(RemoteViews view) {
        String team_home = "Default1";
        String team_away = "Default2";
        String score = "0-0";

        //create a cursor. For now, just put in a date of today
        //sort by date
        Uri scores = DatabaseContract.scores_table.buildScoreWithDate();
        Date date = new Date(System.currentTimeMillis());       //today
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        String[] selectargs = {mformat.format(date)};

        Cursor c = getApplicationContext().getContentResolver().query(scores, null, null, selectargs, DatabaseContract.scores_table.DATE_COL);

        //3 possible states...
        //no data found
        //full data for widget
        //incomplete data widget

        //move to first
        if (c.moveToFirst()) {

            //get the data
            //TODO - RtL
            team_home = c.getString(c.getColumnIndex(DatabaseContract.scores_table.HOME_COL));
            team_away = c.getString(c.getColumnIndex(DatabaseContract.scores_table.AWAY_COL));
            score = Utilies.getScores(c.getInt(c.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL)), c.getInt(c.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL)), getApplicationContext());
        }
        //close the cursor
        c.close();

        //set the widget
        view.setTextViewText(R.id.widget_team1, team_home);
        view.setTextViewText(R.id.widget_team2, team_away);
        view.setTextViewText(R.id.widget_score, score);

    }
}
