package app.mapquest.com.mapquest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
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

    public void getByIdTest(View view) throws ParseException {
        Toast.makeText(MainActivity.this, "Got location: " + Getting.getLocationInfoByID("Lx5ruREoOn").toString() , Toast.LENGTH_LONG).show();
    public void testFragment(View view) {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.activity_question_answer_popup, null);
        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        TextView chestDescription = (TextView)popupView.findViewById(R.id.chest_description_lbl);
        chestDescription.setText("chest Description");
        TextView questionDescription = (TextView)popupView.findViewById(R.id.quizTextLbl);
        chestDescription.setText("Count the internet");

        Button sendBtn = (Button)popupView.findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                TextView answerTxtView = (EditText) popupView.findViewById(R.id.answerTxtView);
                String answerText = answerTxtView.getText().toString().trim();
                if (answerText.equalsIgnoreCase("45")) {
                    Log.i(TAG, "Right answer");
                    //Toast.makeText(getCallingActivity(), "YOU WIN", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(TAG, "wrong answer");
                    //Toast.makeText(getCallingActivity(), R.string.wrong_answer, Toast.LENGTH_LONG).show();
                }
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(view.getRootView(), Gravity.CENTER,0,0);

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
    }





}
