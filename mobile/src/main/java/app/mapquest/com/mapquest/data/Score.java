package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("Score")
public class Score extends ParseObject {

    public Score() {

    }

    public static final String KEY = "score";

    ////////////////////////

    public int getScore() {
        return getInt(KEY);
    }

    public void setScore(int newScore) {
        put(KEY, newScore);
    }

    public static ParseQuery<Score> getScoreQuery() {
        return ParseQuery.getQuery(Score.class);
    }
}
