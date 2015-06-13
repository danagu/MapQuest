package app.mapquest.com.mapquest.api;

import com.parse.ParseException;
import com.parse.ParseUser;

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
}
