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

    public void createNewGameTestButton(View view) throws ParseException {
        LinkedList<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
        LocationInfo locationInfo1 = Creating.createNewLocationInfo(32.101243, 34.788439, "Ahuh?", "This my shit");
        locationInfos.add(locationInfo1);

        // TODO: ParseObject.saveAllInBackground(mygames);

        EndPoint endPointOfGame2 = Creating.createNewEndPoint(32.101116, 34.777485, "EndPointQ", "EndPointA");

        // Test get Game
        Game game = Creating.createNewGame("Game2", locationInfos, endPointOfGame2);
        String gameId = game.getObjectId();
        testGetGame(gameId);
    }

    private void testGetGame(final String gameId) {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, "Game2");

        query.findInBackground(new FindCallback<Game>() {
            @Override
            public void done(List<Game> results, ParseException e) {
                for (Game a : results) {
                    Toast.makeText(MainActivity.this, "Got game: " + a.getGameName() ,Toast.LENGTH_LONG).show();
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
        List<LocationInfo> locationsList = a.getAllGameLocationsInfo();
        String strRepresentations = "";
        for(LocationInfo loc: locationsList) {
            loc.fetchInBackground();
            System.out.println("Answer: " + loc.getAnswer());
            strRepresentations += loc.getAnswer() + "\n";
        }

        return strRepresentations;
    }

    private String getPointsQuizes(Game a) {
        List<LocationInfo> locationsList = a.getAllGameLocationsInfo();
        String strRepresentations = "";
        for(LocationInfo loc: locationsList) {
            loc.fetchInBackground();
            System.out.println("Quiz: " + loc.getQuiz());
                strRepresentations += loc.getQuiz() + "\n";
        }

        return strRepresentations;
    }

    private String getPointsStr(Game a) {
        List<LocationInfo> locationsList = a.getAllGameLocationsInfo();
        String strRepresentations = "";
        for(LocationInfo loc: locationsList) {
            loc.fetchInBackground();
             System.out.println("lat: " + loc.getLat());
             System.out.println("lon: " + loc.getLon());

             strRepresentations += "lat: " + loc.getLat() + "\n";
             strRepresentations += "lon: " + loc.getLon() + "\n";
        }

        ParseQuery<Game> query = a.getQuery();
        query.whereEqualTo(Game.GAME_NAME_KEY, "Game2");
        try {
            Game res = query.get(a.getObjectId());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strRepresentations;
    }
}
