package app.mapquest.com.mapquest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.Game;

import static app.mapquest.com.mapquest.R.id.special_button_login;

/**
 * Created by daniellag on 6/13/15.
 */
public class SearchActivity extends ActionBarActivity {

    AutoCompleteTextView textView;
//    SharedPreferences sharedPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //special button click handler
        FrameLayout specialButtonLogin = (FrameLayout) findViewById(R.id.special_button_search);
        specialButtonLogin.setOnClickListener(createGoButtonListener());

        // Get a reference to the AutoCompleteTextView in the layout
        textView = (AutoCompleteTextView) findViewById(R.id.searchGame);



        // Get the string array
        String[] gamesAutoComplete = getAllGamesForAutoComplete();
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gamesAutoComplete);


        textView.setAdapter(adapter);

    }

    private String[] getAllGamesForAutoComplete() {
        List<Game> allGames = new LinkedList<>();
        try {
            allGames = Getting.getAllGames();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<String> gamesNames = new HashSet<>();
        for(Game game: allGames) {
            gamesNames.add(game.getGameName());
        }
        String[] finalGamesNames = new String[gamesNames.size()];
        Iterator<String> itr = gamesNames.iterator();
        int i = 0;
        while (itr.hasNext()) {
            String nextGame = itr.next();
            finalGamesNames[i] = nextGame;
            i++;
        }
        return finalGamesNames;
    }



    /**
     * Returns click listener on update minor button.
     * Triggers update minor value on the beacon.
     */
    private View.OnClickListener createGoButtonListener() {
        return new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent;
                intent = new Intent(SearchActivity.this,MapDisplay.class);
                String finalSearchTerm = textView.getText().toString().trim();
                String gameID = Getting.getGameId(finalSearchTerm);
                if (gameID.equals("-1")) {
                    //invalid gameID, show toast and do not move on.
                    Toast.makeText(SearchActivity.this, "Non existent game, we're very sorry.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                intent.putExtra(MapDisplay.GAME_ARG,finalSearchTerm);
                startActivity(intent);

            }
        };
    }
}
