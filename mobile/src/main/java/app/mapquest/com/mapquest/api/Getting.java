package app.mapquest.com.mapquest.api;

import com.parse.ParseException;
import com.parse.ParseQuery;

import app.mapquest.com.mapquest.data.Game;

/**
 * Created by daniellag on 6/8/15.
 */
public class Getting {

    // If exception thrown - game wasn't found.
    public static Game getGame(String gameName) throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, gameName);
        System.out.println("#### " + query.toString());
        Game resultGame = query.getFirst(); // TODO: handle a lot of!
        return resultGame;
    }
}
