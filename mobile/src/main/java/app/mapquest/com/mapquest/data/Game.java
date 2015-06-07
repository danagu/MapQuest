package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("Game")
public class Game extends ParseObject {
    // Should contain: list of LocationInfo's, EndPoint
    public static final String GAME_NAME_KEY = "game_name";
    public static final String LOCATION_INFO_LIST_KEY = "points_list"; // holds location infos
    public static final String END_POINT_KEY = "end_point";

    ////////////////////////

    public static final String KEY = "game";

    public void setGameName(String newName) {
        put(GAME_NAME_KEY, newName);
    }

    public void addPointToGame(LocationInfo newLocationInfo) {
        Object currentListObj = get(LOCATION_INFO_LIST_KEY);
        List<LocationInfo> currentList;
        if(currentListObj == null) {
            currentList = new LinkedList<LocationInfo>();
        } else {
            currentList = (List<LocationInfo>) currentListObj;
        }

        currentList.add(newLocationInfo);

        put(LOCATION_INFO_LIST_KEY, currentList);

        // TODO: Save in bg?

    }

    public void setEndPoint(EndPoint newEndPoint) {
        put(END_POINT_KEY, newEndPoint);
    }


    public static ParseQuery<Game> getQuery() {
        return ParseQuery.getQuery(Game.class);
    }

}
