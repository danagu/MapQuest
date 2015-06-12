package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.LinkedList;

import app.mapquest.com.mapquest.api.Creating;
import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;

import static app.mapquest.com.mapquest.api.Getting.*;


public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        initializeParse();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Getting.syncLocalDatastoreWithServer();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Button functions


    /** Called when the user clicks the Send button */
    public void goToGame(View view) {
        Intent intent = new Intent(this, MapDisplay.class);
        Game currentGame;
        try {
            currentGame = Getting.getGame("Game");
        } catch (ParseException e) {
            Toast.makeText(this.getApplicationContext(),R.string.parse_data_failed,Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(TAG,"TEST");
        Log.i(TAG, currentGame.getGameName());
        intent.putExtra("GAME", currentGame.getGameName());
        startActivity(intent);

    }

    public void testButton(View view) throws ParseException {
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


}
