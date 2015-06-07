package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("Game")
public class Game extends ParseObject {
    // Should contain: list of LocationInfo's, EndPoint
    public static final String GAME_NAME_KEY = "game_name";
    public static final String POINTS_LIST_KEY = "points_list";
    public static final String END_POINT_KEY = "end_point";

    ////

}
