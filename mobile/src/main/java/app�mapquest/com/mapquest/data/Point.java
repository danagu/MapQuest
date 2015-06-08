package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/5/15.
 */
@ParseClassName("Point")
public class Point extends ParseObject {


    public static final String KEY_LAT = "point_lat";
    public static final String KEY_LON = "point_lon";

    public Point() {

    }

    public void createNewPoint(double lat, double lon) {
        put(KEY_LAT, lat);
        put(KEY_LON, lon);
        saveInBackground();
    }


    public void setLocation(double lat, double lon) {
        put(KEY_LAT, lat);
        put(KEY_LON, lon);
        saveInBackground();
    }

    public static ParseQuery<Point> getQuery() {
        return ParseQuery.getQuery(Point.class);
    }

    public double getLongitude() {
        return getDouble(KEY_LON);

    }
    public double getLatitude() {
        return getDouble(KEY_LAT);
    }
}
