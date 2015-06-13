package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
                // TODO! RANDOM CHASE
                intent = new Intent(MenuActivity.this, SearchActivity.class);
                startActivity(intent);
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
