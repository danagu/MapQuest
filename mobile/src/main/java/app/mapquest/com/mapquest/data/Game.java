package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.LinkedList;
import java.util.List;

import app.mapquest.com.mapquest.api.Getting;

/**
 * All put and get methods are to local datastore.
 *
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("Game")
public class Game extends ParseObject {

    public static final String GAME_NAME_KEY = "game_name";
    public static final String LOCATION_INFO_LIST_KEY = "points_list";
    public static final String END_POINT_KEY = "end_point";

    public void setGameName(String newName) {
        put(GAME_NAME_KEY, newName);
        saveInBackground();
    }

    public void addPointToGame(LocationInfo newLocationInfo) {
        Object currentListObj = get(LOCATION_INFO_LIST_KEY);
        List<LocationInfo> currentList;
        if(currentListObj == null) {
            currentList = new LinkedList<LocationInfo>();
        } else {
            currentList = (List<LocationInfo>) currentListObj;
        }

        currentList.add(newLocationInfo);

        put(LOCATION_INFO_LIST_KEY, currentList);
        pinAllInBackground(currentList); //?
        saveInBackground();
    }

    public String getGameName() {
        return getString(GAME_NAME_KEY);
    }

    public void setEndPoint(EndPoint newEndPoint) {
        put(END_POINT_KEY, newEndPoint);
        saveInBackground();
    }

    public List<LocationInfo> getAllGameLocationsInfo() {
        return (List<LocationInfo>) get(LOCATION_INFO_LIST_KEY);
    }

    public EndPoint getEndPoint() {
        return (EndPoint) getParseObject(END_POINT_KEY);
    }

    public static ParseQuery<Game> getQuery() {
        return ParseQuery.getQuery(Game.class);
    }

    @Override
    public String toString() {
        String strRepresentation = "End point: \n";
        try {
            strRepresentation += "Game: " + getGameName() + "\n" + "Locations In Game: " + Getting.getAllGamesLocationInfosString(getGameName()) + " End point: " + getEndPoint().toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strRepresentation;
    }
}
