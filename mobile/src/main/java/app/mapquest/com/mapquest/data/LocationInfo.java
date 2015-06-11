package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * This class should contain the information:
 * Point, Quiz, Answer, Score
 */
@ParseClassName("LocationInfo")
public class LocationInfo extends ParseObject {

    public static final String POINT_KEY_LAT = "location_info_lat";
    public static final String POINT_KEY_LON = "location_info_lon";
    public static final String QUIZ_KEY = "location_info_quiz";
    public static final String ANSWER_KEY = "location_info_answer";
    public static final String SCORE_KEY = "location_info_score";


    public void setLocationInfo(double lat, double lon, String quiz, String answer, int score) {
        put(POINT_KEY_LAT, lat);
        put(POINT_KEY_LON, lon);
        put(QUIZ_KEY, quiz);
        put(ANSWER_KEY, answer);
        put(SCORE_KEY, score);
        saveInBackground();
    }

    public void setPoint(double lat, double lon) {
        put(POINT_KEY_LAT, lat);
        put(POINT_KEY_LON, lon);
        saveInBackground();
    }

    public void setQuiz(String quiz){
        put(QUIZ_KEY, quiz);
        saveInBackground();
    }

    public void setAnswer(String answer) {
        put(ANSWER_KEY, answer);
        saveInBackground();
    }

    public void setScore(int score) {
        put(SCORE_KEY, score);
        saveInBackground();
    }

    public static ParseQuery<LocationInfo> getQuery() {
        return ParseQuery.getQuery(LocationInfo.class);
    }

    public String getAnswer() {
        return getString(ANSWER_KEY); // fetchInBackground?!
    }

    public double getLat() {
        return getDouble(POINT_KEY_LAT);
    }

    public double getLon() {
        return getDouble(POINT_KEY_LON);
    }

    public String getQuiz() {
        return getString(QUIZ_KEY);
    }

    @Override
    public String toString() {
        String strRepresentation = "End point: \n";
        strRepresentation += "lat: " + getLat() + " lon: " + getLon() + " quiz: " + getQuiz() + " answer: " + getAnswer();
        return strRepresentation;

    }
}