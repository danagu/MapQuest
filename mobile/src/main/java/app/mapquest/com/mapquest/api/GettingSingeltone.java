package app.mapquest.com.mapquest.api;

import com.parse.ParseException;

import app.mapquest.com.mapquest.data.Game;

/**
 * Created by daniellag on 6/9/15.
 */
public class GettingSingeltone {

    private Game game = null;

    public GettingSingeltone(String gameName) throws ParseException {
        initGame(gameName);

    }

    private void initGame(String gameName) throws ParseException {
        if(game == null) {
            this.game = Getting.getGame(gameName);
        }
    }

    public Game getGame() {
        return game;
    }
}
