package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity
{
    public static int selected_match_id;
    public static int selected_widget_position;
    public static int current_fragment = 2;
    public static String LOG_TAG = "MainActivity";
    private final String save_tag = "Save Test";
    public static final String ARG_CURRENT_PAGE = "Pager_Current";
    public static final String ARG_SELECTED_MATCH = "Selected_match";
    public static final String INTENTARG_WIDGET_SELECTED_MATCHID = "widgetIdSelection";
    public static final String INTENTARG_WIDGET_SELECTED_POSITION = "widgetPoaSelection";

    private PagerFragment my_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Reached MainActivity onCreate");
        selected_widget_position = -1;     //initialize value and only use if invoked by widget
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(INTENTARG_WIDGET_SELECTED_MATCHID)) {
                //invoked from widget!
                //Get the match ID and set it to be selected
                double arg = getIntent().getDoubleExtra(INTENTARG_WIDGET_SELECTED_MATCHID, 0);
                if (arg > 0) selected_match_id = (int) arg;
                selected_widget_position = getIntent().getIntExtra(INTENTARG_WIDGET_SELECTED_POSITION, -1);
            }
            my_main = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, my_main)
                    .commit();
        }
    }

    @Override
    public void onResume() {
        //make sure dates are correct in fragments
        super.onResume();
        my_main.updateDates();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Intent start_about = new Intent(this,AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.v(save_tag,"will save");
        Log.v(save_tag,"fragment: "+String.valueOf(my_main.mPagerHandler.getCurrentItem()));
        Log.v(save_tag,"selected id: "+selected_match_id);
        outState.putInt(ARG_CURRENT_PAGE,my_main.mPagerHandler.getCurrentItem());
        outState.putInt(ARG_SELECTED_MATCH,selected_match_id);
        getSupportFragmentManager().putFragment(outState,"my_main",my_main);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.v(save_tag,"will retrive");
        Log.v(save_tag,"fragment: "+String.valueOf(savedInstanceState.getInt(ARG_CURRENT_PAGE)));
        Log.v(save_tag,"selected id: "+savedInstanceState.getInt(ARG_SELECTED_MATCH));
        current_fragment = savedInstanceState.getInt(ARG_CURRENT_PAGE);
        selected_match_id = savedInstanceState.getInt(ARG_SELECTED_MATCH);
        my_main = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState,"my_main");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
