package app.mapquest.com.mapquest.api;

import com.parse.ParseObject;

import java.util.List;

import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;

/**
 * Created by daniellag on 6/7/15.
 */
public class Creating {

    public static void createNewGame(String gameName, List<LocationInfo> locationsInfo, EndPoint endPoint) {
        ParseObject gameObj = new ParseObject("Game");
        gameObj.put(Game.GAME_NAME_KEY, gameName); // TODO: handle same keys inserted.
        gameObj.put(Game.LOCATION_INFO_LIST_KEY, locationsInfo);
        gameObj.put(Game.END_POINT_KEY, endPoint);
        gameObj.saveInBackground();
    }
}
