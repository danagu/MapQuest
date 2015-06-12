package app.mapquest.com.mapquest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;

import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.Game;


public class MainActivity extends Activity {


    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Getting.syncLocalDatastoreWithServer();
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


                /* ---------- ANDROID WEAR ------------- */
    /**
     * Handles the button to launch a notification.
     */
    public void showNotification(View view) {
        Notification notification = new NotificationCompat.Builder(MainActivity.this)//.setSmallIcon(R.drawable.shopli)
                .setContentTitle("MAPCHA")
                .setContentText("You have just chased it !! Now solve the quiz :) ")
                .setSmallIcon(R.drawable.ic_launcher) // addAction
                .build();

        NotificationManager notificationManger =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManger.notify(0, notification);

        // User pushes on notification
    }


    /* -- Deprecated server notification -- */
//    public void createPushNotification(View view) {
//        Log.i(TAG, "Inside get push");
//        ParsePush push = new ParsePush();
//        push.setChannel("");
//        push.setMessage("You have just chased it !! Now solve the quiz :) ");
//        try {
//            Log.i(TAG, "SENT THE MESSAGE");
//            push.send(); // TODO: Send immediately for demo!
//        } catch (ParseException e) {
//            Log.e(TAG, "COULDN'T SEND THE MESSAGE!");
//            e.printStackTrace();
//        }
//    }


}
