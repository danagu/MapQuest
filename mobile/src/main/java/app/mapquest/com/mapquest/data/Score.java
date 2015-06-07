package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/7/15.
 */
@ParseClassName("Score")
public class Score extends ParseObject {

    public static final String KEY = "score";

    ////////////////////////

    public ParseObject getScore() {
        return getParseObject(KEY);
    }

    public void setScore(ParseObject newScore) {
        put(KEY, newScore);
    }

    public static ParseQuery<Score> getScoreQuery() {
        return ParseQuery.getQuery(Score.class);
    }
}
