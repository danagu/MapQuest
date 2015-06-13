package app.mapquest.com.mapquest.api;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import app.mapquest.com.mapquest.data.Game;

/**
 * Created by daniellag on 6/13/15.
 */
public class Updating {

    // The user is the current - join only in the app!
    public static void addUserToGame(String gameName) throws ParseException {
        Game game = Getting.getGame(gameName);
        game.addUserToGame(ParseUser.getCurrentUser());

    }

    public static void addAllUsersToGame(String gameName) throws ParseException {
        final Game game = Getting.getGame(gameName);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> allUsers, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for(ParseUser user: allUsers) {
                        game.addUserToGame(user);

                    }
                } else {
                    // Something went wrong.
                    Log.e("USERS", "### GOT ERROR!!!! #### ");
                }
            }
        });
    }

}
