package app.mapquest.com.mapquest.api;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;

/**
 * Created by daniellag on 6/9/15.
 */
public class QuizAnswerScoreUtils {

    public static final String NO_ANSWER_RESULT_STRING = "NO ANSWER FOR THIS QUIZ";
    public static final String LOCATION_INFO_LABEL = "LOCATION_INFO_LABEL";

    public static String getAnswerForQuizByLonLat(String gameName, double lat, double lon) throws ParseException {
        LocationInfo locationInfo = Getting.getLocationInfo(gameName, lat, lon);
        return locationInfo.getAnswer();
    }


    public static String getAnswerForQuizByQuiz(String gameName, String quiz) throws ParseException {
        Game game = Getting.getGame(gameName);
        List<LocationInfo> gamesLocations = game.getAllGameLocationsInfo();
        ParseObject.fetchAll(gamesLocations);
        for(LocationInfo locationInfo: gamesLocations) {
            if(locationInfo.getQuiz().equals(quiz)) {
                return locationInfo.getAnswer();
            }
        }
        return NO_ANSWER_RESULT_STRING;
    }

    public static String getQuizPerLocation(String gameName, double lat, double lon) throws ParseException {
        LocationInfo locationInfo = Getting.getLocationInfo(gameName, lat, lon);
        return locationInfo.getQuiz();
    }

    public static void loadLocationInfoToCache(String gameName, int lat, int lon) throws ParseException {
        Game game = Getting.getGame(gameName); // TODO: Load the game as singeltone

    }

}
