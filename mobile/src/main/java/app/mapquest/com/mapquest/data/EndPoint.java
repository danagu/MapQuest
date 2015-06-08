package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("EndPoint")
public class EndPoint extends ParseObject{

    public EndPoint() {

    }

    ////////////////////////
    public static final String KEY_LOCATION_INFO = "endpoint_location_info";

    public void setPointInfo(LocationInfo locationInfo) {
        put(KEY_LOCATION_INFO, locationInfo);
    }

    public static ParseQuery<EndPoint> getEndPointQuery() {
        return ParseQuery.getQuery(EndPoint.class);
    }

    public LocationInfo getLocationInfo() {
        return (LocationInfo) getParseObject(KEY_LOCATION_INFO);
    }
}
