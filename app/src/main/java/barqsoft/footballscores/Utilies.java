package barqsoft.footballscores;

import android.content.Context;
import android.view.View;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final int SERIE_A = 357;
    public static final int PREMIER_LEGAUE = 354;
    public static final int CHAMPIONS_LEAGUE = 362;
    public static final int PRIMERA_DIVISION = 358;
    public static final int BUNDESLIGA = 351;
    public static String getLeague(int league_num, Context ctx)
    {
        switch (league_num)
        {
            //TODO - put strings into resource file (not translatable for proper league names) - FIXED
            case SERIE_A : return ctx.getResources().getString(R.string.seriaa);                //"Seria A"
            case PREMIER_LEGAUE : return ctx.getResources().getString(R.string.premierleague);  //"Premier League"
            case CHAMPIONS_LEAGUE : return ctx.getResources().getString(R.string.champions_league); //"UEFA Champions League"
            case PRIMERA_DIVISION : return ctx.getResources().getString(R.string.primeradivison);   //"Primera Division"
            case BUNDESLIGA : return ctx.getResources().getString(R.string.bundesliga);         //"Bundesliga"
            default: return ctx.getResources().getString(R.string.unknown_league);              //"Not known League Please report"
        }
    }
    public static String getMatchDay(int match_day,int league_num, Context ctx)
    {
        if(league_num == CHAMPIONS_LEAGUE)
        {
            //TODO - put strings into resource file - FIXED
            if (match_day <= 6)
            {
                return ctx.getResources().getString(R.string.group_stagematchday_text);
            }
            else if(match_day == 7 || match_day == 8)
            {
                return ctx.getResources().getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return ctx.getResources().getString(R.string.quarter_final);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return ctx.getResources().getString(R.string.semi_final);
            }
            else
            {
                return ctx.getResources().getString(R.string.final_text);
            }
        }
        else
        {
            //And check RTL
            if (ctx.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                return String.valueOf(match_day) + " : " + ctx.getResources().getString(R.string.matchday_text);
            } else {
                return ctx.getResources().getString(R.string.matchday_text) + " : " + String.valueOf(match_day);
            }
        }
    }

    public static String getScores(int home_goals,int awaygoals,Context ctx)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            //TODO - right to left language fixup... - FIXED
            if (ctx.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                return String.valueOf(awaygoals) + " - " + String.valueOf(home_goals);
            } else {
                return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
            }
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            //rclark - Literal constants here should be okay. We could put these in final string
            //constants but it would not make code any more readable (or easier to modify, add)
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Crystal Palace FC" : return R.drawable.crystal_palace_fc;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            default: return R.drawable.no_icon;
        }
    }
}
