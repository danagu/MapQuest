package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

import app.mapquest.com.mapquest.api.Creating;
import app.mapquest.com.mapquest.data.Answer;
import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;
import app.mapquest.com.mapquest.data.Point;
import app.mapquest.com.mapquest.data.Quiz;
import app.mapquest.com.mapquest.data.Score;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initializeParse();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initializeParse() {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "GM7cHc32GFCgAwthzFbpc3X1iSZBbXfYzQrLMgbP", "UK7QTonPXE3j6IBt5DIOd4E10KWBAc64l3XYTz9l");

        // Can save in local datastore: pinInBackground()
        ParseObject.registerSubclass(Answer.class);
        ParseObject.registerSubclass(EndPoint.class);
        ParseObject.registerSubclass(Game.class);
        ParseObject.registerSubclass(LocationInfo.class);
        ParseObject.registerSubclass(Point.class);
        ParseObject.registerSubclass(Quiz.class);
        ParseObject.registerSubclass(Score.class);
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

    public void createNewGameTestButton(View view) {
//        LinkedList<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
//        LocationInfo locationInfo1 = new LocationInfo();
//        ParseGeoPoint parseGeoPoint1 = new ParseGeoPoint();
//        parseGeoPoint1.setLatitude(32.101243);
//        parseGeoPoint1.setLatitude(34.788439);
//
//        Point point1 = new Point();
//        point1.setLocation(parseGeoPoint1);
//        point1.saveInBackground();
//
//        locationInfo1.setPoint(point1);
//
//        Game game1 = new Game();
//        game1.setGameName("Game1");
//        game1.addPointToGame(locationInfo1);
//       game1.saveInBackground();


        LinkedList<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
        LocationInfo locationInfo1 = Creating.createNewLocationInfo(32.101243, 34.788439, "Ahuh?", "This my shit");
        locationInfos.add(locationInfo1);

        EndPoint endPointOfGame2 = Creating.createNewEndPoint(32.101116, 34.777485, "EndPointQ", "EndPointA");

        Creating.createNewGame("Game2", locationInfos, endPointOfGame2);

        // Test get Game
        testGetGame();


    }

    private void testGetGame() {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, "Game2");

        query.findInBackground(new FindCallback<Game>() {
            @Override
            public void done(List<Game> results, ParseException e) {
                for (Game a : results) {
                    Toast.makeText(MainActivity.this, "Got game: " + (String) a.get(Game.GAME_NAME_KEY) ,Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Got points: " ,Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, getPointsStr(a) ,Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Got questions: " ,Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, getPointsQuizes(a) ,Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, "Got answers: " ,Toast.LENGTH_LONG).show();
                    Toast.makeText(MainActivity.this, getPointsAnswers(a) ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getPointsAnswers(Game a) {
        List<LocationInfo> locationsList = (List<LocationInfo>) a.get(Game.LOCATION_INFO_LIST_KEY);
        String strRepresentations = "";
        for(LocationInfo loc: locationsList) {
            strRepresentations += loc.getAnswer().getAnswerString() + "\n";
        }

        return strRepresentations;
    }

    private String getPointsQuizes(Game a) {
        List<LocationInfo> locationsList = (List<LocationInfo>) a.get(Game.LOCATION_INFO_LIST_KEY);
        String strRepresentations = "";
        for(LocationInfo loc: locationsList) {
            strRepresentations += loc.getQuiz().getQuizString() + "\n";
        }

        return strRepresentations;
    }

    private String getPointsStr(Game a) {
        List<LocationInfo> locationsList = (List<LocationInfo>) a.get(Game.LOCATION_INFO_LIST_KEY);
        String strRepresentations = "";
        for(LocationInfo loc: locationsList) {
            strRepresentations += "lat: " + loc.getParseGeoPoint(LocationInfo.POINT_KEY).getLatitude() +
            " lon: " + loc.getParseGeoPoint(LocationInfo.POINT_KEY).getLongitude() + "\n";
        }

        return strRepresentations;
    }
}
