package app.mapquest.com.mapquest.api;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;

/**
 * Created by daniellag on 6/8/15.
 */
public class Getting {

    // If exception thrown - game wasn't found.
    public static Game getGame(String gameName) throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, gameName);
        Game resultGame = query.getFirst(); // TODO: handle a lot of!
        return resultGame;
    }

    public static LocationInfo getLocationInfo(String gameName, double lat, double lon) throws ParseException {
        Game game = getGame(gameName);
        // fetch if needed?!?!?!
        List<LocationInfo> locationInfos = game.getAllGameLocationsInfo();
        ParseObject.fetchAll(locationInfos);
        for(LocationInfo location: locationInfos) {
            if(location.getLat() == lat && location.getLon() == lon) {
                return location;
            }
        }

        return null; // maybe throw exception?!
    }

    public static boolean isEndPointOfGame(String gameName, double lat, double lon) throws ParseException {
        Game game = getGame(gameName);
//        game.fetchInBackground();
        EndPoint gamesEndPoint = game.getEndPoint();
        gamesEndPoint.fetchInBackground();
        LocationInfo locationInfoOfEndPoint = gamesEndPoint.getLocationInfo();
        locationInfoOfEndPoint.fetchInBackground();
        if(locationInfoOfEndPoint.getLat() == lat && locationInfoOfEndPoint.getLon() == lon) {
            return true;
        }
        return false;
    }

    // NOT TESTED!!

    public static EndPoint getGamesEndPoint(String gameName) throws ParseException {
        Game game = getGame(gameName);
        return game.getEndPoint();
    }

    public static LocationInfo getGamesEndPointLocationInfo(String gameName) throws ParseException {
        Game game = getGame(gameName);
        EndPoint endPoint = game.getEndPoint();
        endPoint.fetchInBackground();
        return endPoint.getLocationInfo();
    }
}


