package app.mapquest.com.mapquest.api;

import com.parse.ParseException;
import com.parse.ParseQuery;

import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.GameTypes;

/**
 * Created by daniellag on 6/13/15.
 */
public class RandomGetter {

    private static Game bykeGame;

    public static Game getRandomGameByType(GameTypes gameType) throws ParseException {
        switch (gameType) {
            case BY_BIKE:
                return getBykeGame();
            case BY_CAR:
                return getCarGame();
            case BY_FEET:
                return getFeetGame();
            default:
                return null; // TODO: Return something else!
        }
    }

    public static Game getBykeGame() throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, "BykeGame");

        Game resultGame = query.getFirst();
        resultGame.pinInBackground();
        return resultGame;
    }

    public static Game getCarGame() throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, "CarGame");

        Game resultGame = query.getFirst();
        resultGame.pinInBackground();
        return resultGame;
    }

    public static Game getFeetGame() throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, "FeetGame");

        Game resultGame = query.getFirst();
        resultGame.pinInBackground();
        return resultGame;
    }
}
