package app.mapquest.com.mapquest.api;

import com.parse.ParseGeoPoint;

import java.util.LinkedList;

import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;
import app.mapquest.com.mapquest.data.Point;

/**
 * Created by daniellag on 6/7/15.
 */
public class CreatingTest {

    public void createNewGameTestButton() {
        LinkedList<LocationInfo> locationInfos = new LinkedList<LocationInfo>();
        LocationInfo locationInfo1 = new LocationInfo();
        ParseGeoPoint parseGeoPoint1 = new ParseGeoPoint();
        parseGeoPoint1.setLatitude(32.101243);
        parseGeoPoint1.setLatitude(34.788439);

        Point point1 = new Point();
        point1.setLocation(parseGeoPoint1);
        point1.saveInBackground();

        locationInfo1.setPoint(point1);

        Game game1 = new Game();
        game1.addPointToGame(locationInfo1);
        game1.saveInBackground();


//        Creating.createNewGame("Test1");
    }

}
