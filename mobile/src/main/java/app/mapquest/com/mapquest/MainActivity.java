package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.LinkedList;

import app.mapquest.com.mapquest.api.Creating;
import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//        createNewGame();
        Getting.syncLocalDatastoreWithServer();
        printGameInfo();
    }


    /**
     * TESTS AND USAGE ****
     */

    private void printGameInfo() throws ParseException {
        Game game = Getting.getGame("Game");
        Toast.makeText(MainActivity.this, "Got game: " + game.toString(), Toast.LENGTH_LONG).show();
    }

    private void createNewGame() throws ParseException {
        LinkedList<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
        LocationInfo locationInfo1 = Creating.createNewLocationInfo(2, 2, "2?", "2!");
        locationInfos.add(locationInfo1);

        EndPoint endPointOfGame2 = Creating.createNewEndPoint(2.9, 2.9, "EndPointQ", "EndPointA");

        Game game = Creating.createNewGame("Game", locationInfos, endPointOfGame2);
        game.setEndPoint(endPointOfGame2);

    }

}
