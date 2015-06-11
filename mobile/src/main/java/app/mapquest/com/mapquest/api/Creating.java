package app.mapquest.com.mapquest.api;

import com.parse.ParseException;

import java.util.List;

import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;

/**
 * Created by daniellag on 6/7/15.
 */
public class Creating {

    public static Game createNewGame(String gameName, List<LocationInfo> locationsInfo, EndPoint endPoint) throws ParseException {
        Game newGame = new Game(); // TODO: make sure that the name is unique.
        newGame.setGameName(gameName);
        for(LocationInfo locationInfo: locationsInfo) {
            newGame.addPointToGame(locationInfo);
        }
        newGame.setEndPoint(endPoint);
//        newGame.pinInBackground(); SHOULDN'T BE HERE CAUSE ALL OTHER ARE PINNED AND THE GAME IS FETCHED BY NAME
        return newGame;
    }

    public static LocationInfo createNewLocationInfo(double lat, double lon, String quiz, String answer) throws ParseException {
        LocationInfo newLocation = new LocationInfo();
        newLocation.setLocationInfo(lat, lon, quiz, answer, 0); // TODO: This is default score!
        newLocation.pinInBackground();
        return newLocation;

    }

    public static EndPoint createNewEndPoint(double lat, double lon, String quiz, String answer) throws ParseException {
        LocationInfo newLocation = createNewLocationInfo(lat, lon, quiz, answer);

        EndPoint endPoint = new EndPoint();
        endPoint.setEndPoint(newLocation);

        endPoint.pinInBackground();

        return endPoint;
    }
}
