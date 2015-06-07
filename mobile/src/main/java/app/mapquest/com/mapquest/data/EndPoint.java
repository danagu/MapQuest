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
    public static final String KEY = "endpoint";

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
