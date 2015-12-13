# Football Scores

A Udacity android nanodegree application which needs some debugging/enhancement. Loads football scores from a public database 
(http://api.football-data.org) and displays past 2 days, next 2 days of match results and times.
This application was *not* written by me but instead downloaded for a lesson.

## Installation

First, clone this repo. Second, this project developed with Android Studio 1.5.1 and is set up to support API 17 or later android OS.

Request an API key from api.football-data.org. Set resource string constant api_key to be this API key.

You should be able to build and run at this point.

## Usage

Tested on N5, N9. 

Udacity rubric issues solved:
- got API key
- added content descriptions for all buttons
- added RtL layout mirroring
- added collection widget for today's football scores
- moved literal strings to @string resource file (where appropriate)
- fixed date locale formatting

Other issues seen/resolved/changed:
- cleaned up some content provider code (use database contract names for index lookups)
- cleaned up some literal inline code strings for arg storage/restore into constants
- added a few football icons
- fixed some league number mismatches
- fix to update date/data/position on resume (in case of app being in the backstack)
- add comment to about box

TODO:
clean up widget - make look pretty and handle RtL, locale
Test dynamic updating of data from sync service
Final lint pass

## History

First iteration for phase 3 of nanodegree.

## Credits

The original version of this app was created by Yahia Khalid Ibn El Walid Ahmed
Adaptation from LAAPTU listview widget example - https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
Previous projects for android nanodegree
