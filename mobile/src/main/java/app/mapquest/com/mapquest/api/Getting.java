package app.mapquest.com.mapquest.api;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;

/**
 * Created by daniellag on 6/8/15.
 */
public class Getting {

    private static List<Game> allGamesFromLocal;

    // If exception thrown - game wasn't found.
    public static Game getGame(String gameName) throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        query.whereEqualTo(Game.GAME_NAME_KEY, gameName);

        Game resultGame = query.getFirst(); // TODO: handle a lot of!
        return resultGame;
    }

    public static LocationInfo getLocationInfo(String gameName, double lat, double lon) throws ParseException {
        Game game = getGame(gameName);
        List<LocationInfo> locationInfos = game.getAllGameLocationsInfo();
        // Fetches to local datastore?
        ParseObject.pinAllInBackground(locationInfos);
        for(LocationInfo location: locationInfos) {
            if(location.getLat() == lat && location.getLon() == lon) {
                return location;
            }
        }

        return null; // maybe throw exception?!
    }

    public static List<LocationInfo> getAllGamesLocationInfos(String gameName) throws ParseException {
        // TODO: USE A QUERY?
        Game game = getGame(gameName);
        List<LocationInfo> locationsList = game.getAllGameLocationsInfo();
        ParseObject.pinAllInBackground(locationsList);
        ParseObject.fetchAllIfNeeded(locationsList); //!?
        return locationsList;
    }
    // NOT TESTED!!

    public static String getAllGamesLocationInfosString(String gameName) throws ParseException {
        String strRepresentations = "";
        List<LocationInfo> locationInfos = getAllGamesLocationInfos(gameName);
        for(LocationInfo loc: locationInfos) {
            strRepresentations += "lat: " + loc.getLat() + "\n";
            strRepresentations += "lon: " + loc.getLon() + "\n";
        }
        return strRepresentations;
    }



    public static boolean isEndPointOfGame(String gameName, double lat, double lon) throws ParseException {
        Game game = getGame(gameName);
        EndPoint gamesEndPoint = game.getEndPoint();
//        gamesEndPoint.fetchInBackground();
        gamesEndPoint.pinInBackground();
        LocationInfo locationInfoOfEndPoint = gamesEndPoint.getLocationInfo();
//        locationInfoOfEndPoint.fetchInBackground();
        locationInfoOfEndPoint.pinInBackground();
        if(locationInfoOfEndPoint.getLat() == lat && locationInfoOfEndPoint.getLon() == lon) {
            return true;
        }
        return false;
    }

    public static EndPoint getGamesEndPoint(String gameName) throws ParseException {
        Game game = getGame(gameName);
        game.fetchIfNeeded(); // does return subclasses?
        return game.getEndPoint();  //get in bg?
    }

    public static LocationInfo getGamesEndPointLocationInfo(String gameName) throws ParseException {
        Game game = getGame(gameName);
        EndPoint endPoint = game.getEndPoint();
        endPoint.pinInBackground();
        return endPoint.getLocationInfo();
    }

    public static void syncLocalDatastoreWithServer() {
        ParseQuery<Game> gameParseQuery = Game.getQuery();
        gameParseQuery.findInBackground(new FindCallback<Game>() {
            @Override
            public void done(List<Game> games, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(games);
                }
            }
        });

        ParseQuery<LocationInfo> locationInfoParseQuery = LocationInfo.getQuery();
        locationInfoParseQuery.findInBackground(new FindCallback<LocationInfo>() {
            @Override
            public void done(List<LocationInfo> locationInfos, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(locationInfos);
                }
            }
        });

        ParseQuery<EndPoint> endPointParseQuery = EndPoint.getEndPointQuery();
        endPointParseQuery.findInBackground(new FindCallback<EndPoint>() {
            @Override
            public void done(List<EndPoint> endPoints, ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground(endPoints);
                }
            }
        });
    }

        public static LocationInfo getLocationInfoByID(String id) throws ParseException {
            ParseQuery<LocationInfo> locationInfoParseQuery = LocationInfo.getQuery();
            LocationInfo locationInfo = locationInfoParseQuery.get(id);
            return locationInfo;
        }

    public static List<ParseUser> getAllUsersOfGame(String gameName) throws ParseException {
        // TODO: USE A QUERY?
        Game game = getGame(gameName);
        List<ParseUser> usersList = game.getAllUsersOfGame();
        ParseObject.pinAllInBackground(usersList);
        ParseObject.fetchAllIfNeeded(usersList); //!?
        return usersList;
    }

    public static String getAllUsersOfGameString(String gameName) throws ParseException {
        String strRepresentations = "";
        List<ParseUser> usersInGame = getAllUsersOfGame(gameName);
        for(ParseUser user: usersInGame) {
            strRepresentations += user.getUsername() + "\n";
        }
        return strRepresentations;
    }

    public static List<Game> getAllGames() throws ParseException {
        ParseQuery<Game> query = ParseQuery.getQuery(Game.class);
        List<Game> gamesList = query.find();
        return gamesList;

    }

    public static String getGameId(String gameName) {
        try {
            Game game = getGame(gameName);
            return game.getObjectId();
        } catch (ParseException e) {
            return "-1";
        }
    }

}


