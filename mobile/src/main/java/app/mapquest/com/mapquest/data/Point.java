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


    public static final String KEY = "location";

    ////////////////////////

    public void createNewPoint(double lat, double lon) {
        ParseGeoPoint parseGeoPoint = new ParseGeoPoint();
        parseGeoPoint.setLatitude(lat);
        parseGeoPoint.setLongitude(lon);
        put(KEY, parseGeoPoint);
    }


    public void setLocation(ParseGeoPoint newLocation) {
        put(KEY, newLocation);

    }


    public void setLocation(double lat, double lon) {
        ParseGeoPoint parseGeoPoint = new ParseGeoPoint();
        parseGeoPoint.setLatitude(lat);
        parseGeoPoint.setLongitude(lon);
        put(KEY, parseGeoPoint);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(KEY);
    }

    public static ParseQuery<Point> getQuery() {
        return ParseQuery.getQuery(Point.class);
    }

}
