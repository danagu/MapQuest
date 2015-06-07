package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/5/15.
 */
@ParseClassName("Point")
public class Point extends ParseObject {
//    public void createNewPoint(double lat, double lon) {
//        put("lat", lat);
//        put("lon", lon);
//    }

    ////////////////////////

    public static final String KEY = "location";

    public void setLocation(ParseGeoPoint newLocation) {
        put(KEY, newLocation);

    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(KEY);
    }

    public static ParseQuery<Point> getQuery() {
        return ParseQuery.getQuery(Point.class);
    }

}
