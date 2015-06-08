package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * This class should contain the information:
 * Point, Quiz, Answer, Score
 */
@ParseClassName("LocationInfo")
public class LocationInfo extends ParseObject {

    ////////////////////////
//    public static final String LOCATION_INFO_KEY = "location_info";
    public static final String POINT_KEY = "location_info_point";
    public static final String QUIZ_KEY = "location_info_quiz";
    public static final String ANSWER_KEY = "location_info_answer";
    public static final String SCORE_KEY = "location_info_score";


    public void setLocationInfo(Point point, Quiz quiz, Answer answer, Score score) throws ParseException {
        put(POINT_KEY, point);
        put(QUIZ_KEY, quiz);
        put(ANSWER_KEY, answer);
        put(SCORE_KEY, score);
        save();
    }

    public void setPoint(Point point) throws ParseException {
        put(POINT_KEY, point);
        save();
    }

    public void setQuiz(Quiz quiz) throws ParseException {
        put(QUIZ_KEY, quiz);
        save();
    }

    public void setAnswer(Answer answer) throws ParseException {
        put(ANSWER_KEY, answer);
        save();
    }

    public void setScore(Score score) throws ParseException {
        put(SCORE_KEY, score);
        save();
    }

    public static ParseQuery<LocationInfo> getQuery() {
        return ParseQuery.getQuery(LocationInfo.class);
    }

    public Answer getAnswer() {
        try {
            return (Answer) getParseObject(ANSWER_KEY).fetchIfNeeded();
        } catch (ParseException e) {
            return null;
        }
    }
    public Point getPoint() {
        return (Point) getParseObject(POINT_KEY);
    }

    public Quiz getQuiz() {
        return (Quiz) getParseObject(QUIZ_KEY);
    }
}