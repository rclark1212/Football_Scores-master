package barqsoft.footballscores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by yehya khaled on 2/26/2015.
 */
public class scoresAdapter extends CursorAdapter
{
    public double detail_match_id = 0;
    private String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";

    public scoresAdapter(Context context,Cursor cursor,int flags)
    {
        super(context,cursor,flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        View mItem = LayoutInflater.from(context).inflate(R.layout.scores_list_item, parent, false);
        ViewHolder mHolder = new ViewHolder(mItem);
        mItem.setTag(mHolder);
        //Log.v(FetchScoreTask.LOG_TAG,"new View inflated");
        return mItem;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor)
    {
        //UGG - using column indices to go into the database. Fix up by using the
        //native column names...

        final ViewHolder mHolder = (ViewHolder) view.getTag();
        mHolder.home_name.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL)));
        mHolder.away_name.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL)));

        //TODO - convert time format string to local (note - don't modify time which is set to venue time) - FIXED
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", java.util.Locale.US);
        Date date = null;
        try {
            date = inputFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.TIME_COL)));
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
            mHolder.date.setText(outputFormat.format(date));
        } catch (ParseException e) {
            //if there was an error in parsing, just put back in original
            mHolder.date.setText(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.TIME_COL)));
        }

        //Note - on below, we put the RtoL swap in the routine itself - no need to swap here
        mHolder.score.setText(Utilies.getScores(cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL)),cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL)), context));

        mHolder.match_id = cursor.getDouble(cursor.getColumnIndex(DatabaseContract.scores_table.MATCH_ID));
        mHolder.home_crest.setImageResource(Utilies.getTeamCrestByTeamName(
                cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL))));
        mHolder.away_crest.setImageResource(Utilies.getTeamCrestByTeamName(
                cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL))
        ));
        //Log.v(FetchScoreTask.LOG_TAG,mHolder.home_name.getText() + " Vs. " + mHolder.away_name.getText() +" id " + String.valueOf(mHolder.match_id));
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(detail_match_id));
        LayoutInflater vi = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.detail_fragment, null);
        ViewGroup container = (ViewGroup) view.findViewById(R.id.details_fragment_container);
        if(mHolder.match_id == detail_match_id)
        {
            //Log.v(FetchScoreTask.LOG_TAG,"will insert extraView");

            container.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));
            TextView match_day = (TextView) v.findViewById(R.id.matchday_textview);
            match_day.setText(Utilies.getMatchDay(cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.MATCH_DAY)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.LEAGUE_COL)), context));
            TextView league = (TextView) v.findViewById(R.id.league_textview);
            league.setText(Utilies.getLeague(cursor.getInt(cursor.getColumnIndex(DatabaseContract.scores_table.LEAGUE_COL)), context));
            Button share_button = (Button) v.findViewById(R.id.share_button);
            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //add Share Action
                    //TODO - fixup for mirror locales - FIXED
                    if (context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                        context.startActivity(createShareForecastIntent(mHolder.away_name.getText() + " "
                                + mHolder.score.getText() + " " + mHolder.home_name.getText() + " "));
                    } else {
                        context.startActivity(createShareForecastIntent(mHolder.home_name.getText() + " "
                                + mHolder.score.getText() + " " + mHolder.away_name.getText() + " "));
                    }
                }
            });
        }
        else
        {
            container.removeAllViews();
        }

    }
    public Intent createShareForecastIntent(String ShareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ShareText + FOOTBALL_SCORES_HASHTAG);
        return shareIntent;
    }

}
