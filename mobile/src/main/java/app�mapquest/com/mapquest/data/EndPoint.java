package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("EndPoint")
public class EndPoint extends ParseObject{ // TODO: SHould extend LocationInfo

    ////////////////////////
    public static final String KEY = "endpoint"; // TODO: not necessary
//    public static final String KEY_GAME = "endpoint_game";
    public static final String KEY_LOCATION_INFO = "endpoint_location_info";

//    public void setGame(Game game) {
//        put(KEY_GAME, game);
//    }

    public void setPointInfo(LocationInfo locationInfo) {
        put(KEY_LOCATION_INFO, locationInfo);
    }

    public ParseObject getEndPoint() {
        return getParseObject(KEY);
    }

    public void setEndPoint(ParseObject newEndPoint) {
        put(KEY, newEndPoint);
    }

    public static ParseQuery<EndPoint> getEndPointQuery() {
        return ParseQuery.getQuery(EndPoint.class);
    }
}
