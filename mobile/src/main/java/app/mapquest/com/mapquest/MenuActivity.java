package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseException;

import app.mapquest.com.mapquest.api.RandomGetter;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.GameTypes;

/**
 * Created by daniellag on 6/13/15.
 */
public class MenuActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.searchGamesButton).setOnClickListener(createGamesSearchButtonListener());
        findViewById(R.id.randomChasesButton).setOnClickListener(createRandomGamesButtonListener());


    }

    private View.OnClickListener createRandomGamesButtonListener() {
        return new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent;
                try {
                    Game randomGame = RandomGetter.getRandomGameByType(GameTypes.randomLetter());
                    intent = new Intent(MenuActivity.this, MapDisplay.class);
                    intent.putExtra("GAME", randomGame.getGameName());
                    startActivity(intent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private View.OnClickListener createGamesSearchButtonListener() {
        return new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent;
                intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        };
    }
}
