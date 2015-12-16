package barqsoft.footballscores;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rclark on 12/12/2015.
 * Adaptation from LAAPTU listview widget example.
 * https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
 *
 * This widget will show all matches from TODAY. And allow user to jump to the proper match
 *
 */
public class WidgetListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mCtx = null;
    private int m_appWidgetId;

    public WidgetListProvider(Context context, Intent intent) {
        mCtx = context;
        m_appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public int getCount() {
        //return number of elements returned by cursor...
        //create a cursor.
        //sort by date
        Uri scores = DatabaseContract.scores_table.buildScoreWithDate();
        String[] selectargs = {getTodayDateArg()};
        int count = 0;

        Cursor c = mCtx.getContentResolver().query(scores, null, null, selectargs, DatabaseContract.scores_table.DATE_COL);

        if (c != null) {
            count = c.getCount();

            c.close();
        }

        return count;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     * NOTE - getViewAt does *NOT* run on UI thread. Perfectly fine to do database operations here...
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(mCtx.getPackageName(), R.layout.widget_football_list_item);
        Uri scores = DatabaseContract.scores_table.buildScoreWithDate();
        String[] selectargs = {getTodayDateArg()};

        String team_home = "teamH"; //dummy test data
        String team_away = "teamA";
        String score = "0-0";
        double matchid = -1.0;

        Cursor c = mCtx.getContentResolver().query(scores, null, null, selectargs, DatabaseContract.scores_table.DATE_COL);

        if ((c != null) && (c.moveToPosition(position))) {
            team_home = c.getString(c.getColumnIndex(DatabaseContract.scores_table.HOME_COL));
            team_away = c.getString(c.getColumnIndex(DatabaseContract.scores_table.AWAY_COL));
            matchid = c.getDouble(c.getColumnIndex(DatabaseContract.scores_table.MATCH_ID));

            score = Utilies.getScores(c.getInt(c.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL)), c.getInt(c.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL)), mCtx);
            //now for the score item, either put in the score... Or the time (if it has not happened yet)
            if (score.equals(mCtx.getResources().getString(R.string.no_score))) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", java.util.Locale.US);
                Date date = null;
                try {
                    date = inputFormat.parse(c.getString(c.getColumnIndex(DatabaseContract.scores_table.TIME_COL)));
                    SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
                    score = outputFormat.format(date);
                } catch (ParseException e) {
                    //if there was an error in parsing, just put back in original
                    score = c.getString(c.getColumnIndex(DatabaseContract.scores_table.TIME_COL));
                }
            }
        }

        c.close();

        //set the team names
        remoteView.setTextViewText(R.id.list_item_home_team, team_home);
        remoteView.setTextViewText(R.id.list_item_away_team, team_away);
        remoteView.setTextViewText(R.id.list_item_score, score);

        //finally, set up onClick intent.
        //add match_id so we can open the right match when invoked.
        final Intent fillinIntent = new Intent();
        fillinIntent.putExtra(MainActivity.INTENTARG_WIDGET_SELECTED_MATCHID, matchid);
        fillinIntent.putExtra(MainActivity.INTENTARG_WIDGET_SELECTED_POSITION, position);
        remoteView.setOnClickFillInIntent(R.id.widget_list_item, fillinIntent);

        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }


    @Override
    public void onDataSetChanged() {
        //called when we invalidate view...
        //lets make a sound to test to make sure updates getting through...
        //ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        //toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

    @Override
    public void onDestroy() {
    }

    private String getTodayDateArg() {
        //create a cursor. This routine determines what date we show in widget. Will be today's matches
        //sort by date
        Date date = new Date(System.currentTimeMillis());       //today
        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
        return mformat.format(date);
    }
}
