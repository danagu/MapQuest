package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.LinkedList;

import app.mapquest.com.mapquest.api.Creating;
import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.api.QuizAnswerScoreUtils;
import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        initializeParse();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initializeParse() {
        Parse.initialize(this, "GM7cHc32GFCgAwthzFbpc3X1iSZBbXfYzQrLMgbP", "UK7QTonPXE3j6IBt5DIOd4E10KWBAc64l3XYTz9l");

        // Can save in local datastore: pinInBackground()
        ParseObject.registerSubclass(EndPoint.class);
        ParseObject.registerSubclass(Game.class);
        ParseObject.registerSubclass(LocationInfo.class);
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
        if (id == R.id.action_settings) {
            testParseData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Button functions


    /** Called when the user clicks the Send button */
    public void goToGame(View view) {
        Intent intent = new Intent(this, MapDisplay.class);
        int gameID = 0x415;
        intent.putExtra("gameID", gameID);
        startActivity(intent);

    }

    private void testParseData() {
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("daniella", "test");
        testObject.saveInBackground();
    }

    public void testButton(View view) throws ParseException {
        LinkedList<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
        LocationInfo locationInfo1 = Creating.createNewLocationInfo(2, 2, "2?", "2!");
        locationInfos.add(locationInfo1);

        EndPoint endPointOfGame2 = Creating.createNewEndPoint(2.9, 2.9, "EndPointQ", "EndPointA");

        // Test get Game
        Game game = Creating.createNewGame("Game", locationInfos, endPointOfGame2);
        game.setEndPoint(endPointOfGame2);
        testGetGameFromGetting();
    }

    /**
     * TESTS AND USAGE ****
     */


    private void testGetGameFromGetting() {
        try {
            Game resultGame = Getting.getGame("Game");
            Toast.makeText(MainActivity.this, "Got game: " + resultGame.getGameName(), Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Got points: ",Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, Getting.getAllGamesLocationInfosString(resultGame.getGameName()) ,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Got questions: " ,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, QuizAnswerScoreUtils.getAllQuizesOfGameAsString(resultGame.getGameName()) ,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Got answers: " ,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, QuizAnswerScoreUtils.getAllAnswersOfGameAsString(resultGame.getGameName()) ,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, "Got end point: " ,Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, Getting.getGamesEndPoint(resultGame.getGameName()).toString() ,Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            System.out.println("##### GOT EXCEPTION !!!!!!! ############# ");
            e.printStackTrace();
        }

    }


//    private void testGetLocationInfo(String gameName, double lat, double lon) {
//        try {
//            LocationInfo locationInfo = Getting.getLocationInfo(gameName, lat, lon);
//            Toast.makeText(MainActivity.this, "#LOCATION INFO# ACCEPTED: " + locationInfo.getLat() + "," + locationInfo.getLon() + " ", Toast.LENGTH_LONG).show();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String getPointsAnswers(Game a) {
//        List<LocationInfo> locationsList = a.getAllGameLocationsInfo();
//        String strRepresentations = "";
//        for(LocationInfo loc: locationsList) {
//            loc.fetchInBackground();
//            System.out.println("Answer: " + loc.getAnswer());
//            strRepresentations += loc.getAnswer() + "\n";
//        }
//
//        return strRepresentations;
//    }
//
//    private String getPointsQuizes(Game a) {
//        List<LocationInfo> locationsList = a.getAllGameLocationsInfo();
//        String strRepresentations = "";
//        for(LocationInfo loc: locationsList) {
//            loc.fetchInBackground();
//            System.out.println("Quiz: " + loc.getQuiz());
//                strRepresentations += loc.getQuiz() + "\n";
//        }
//
//        return strRepresentations;
//    }

}
