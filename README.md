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
- add general date/change listener to update widget
- fixed no internet operation
- note, did not change colors or UI approach of this app
- add data change event on day changing
- added polling service to update backing data (yes - not the most power efficient) and update date changes on widget.

- there is a hole here. If user keeps tablet alive and date switches while app is in foreground, main screen won't update. But I think
this is okay. Weird to have the screen in front of you refresh with no action. To make this app truly production worthy, have a refresh
method on pulling down main screen. In addition, have a tickle for the widget for power reasons.


## History

First iteration for phase 3 of nanodegree.

## Credits

The original version of this app was created by Yahia Khalid Ibn El Walid Ahmed
Adaptation from LAAPTU listview widget example - https://laaptu.wordpress.com/2013/07/19/android-app-widget-with-listview/
Previous projects for android nanodegree
