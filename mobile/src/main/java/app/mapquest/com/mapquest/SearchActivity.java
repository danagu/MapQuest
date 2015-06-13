package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.Game;

/**
 * Created by daniellag on 6/13/15.
 */
public class SearchActivity extends Activity {

    AutoCompleteTextView textView;
//    SharedPreferences sharedPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        findViewById(R.id.searchPageGoBtn).setOnClickListener(createGoButtonListener());
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
                intent = new Intent(SearchActivity.this,MainActivity.class);
                String finalSearchTerm = textView.getText().toString();
                String gameID = Getting.getGameId(finalSearchTerm);
                if (gameID.equals("-1")) {
                    //invalid gameID, show toast and do not move on.
                    Toast.makeText(SearchActivity.this, "Non existent game, we're very sorry.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                intent.putExtra("gameID",gameID);
                startActivity(intent);

            }
        };
    }
}