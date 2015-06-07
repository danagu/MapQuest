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

    ////////////////////////
    public static final String LOCATION_INFO_KEY = "location_info";
    public static final String POINT_KEY = "location_info_point";
    public static final String QUIZ_KEY = "location_info_quiz";
    public static final String ANSWER_KEY = "location_info_answer";
    public static final String SCORE_KEY = "location_info_score";


    public void setLocationInfo(Point point, Quiz quiz, Answer answer, Score score) {
        put(POINT_KEY, point);
        put(QUIZ_KEY, quiz);
        put(ANSWER_KEY, answer);
        put(SCORE_KEY, score);
    }

    public void setPoint(Point point) {
        put(POINT_KEY, point);
    }

    public void setQuiz(Quiz quiz) {
        put(QUIZ_KEY, quiz);
    }

    public void setAnswer(Answer answer) {
        put(ANSWER_KEY, answer);
    }

    public void setScore(Score score) {
        put(SCORE_KEY, score);
    }

    @Deprecated
    public ParseObject getLocationInfo() {
        return getParseObject(LOCATION_INFO_KEY); // TODO: BUT THE KEY IS NOT SAVED
    }

    public static ParseQuery<LocationInfo> getQuery() {
        return ParseQuery.getQuery(LocationInfo.class);
    }

    public Answer getAnswer() {
        return (Answer) get(ANSWER_KEY);
    }

    public Quiz getQuiz() {
        return (Quiz) get(QUIZ_KEY);
    }
}