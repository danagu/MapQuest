package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/5/15.
 */
@ParseClassName("Answer")
public class Answer extends ParseObject {

    public void createNewAnswer(String answer) {
        put(KEY, answer);
    }

    ////////////////////////
    public static final String KEY = "answer";

    public String getAnswerString() {
        return (String) get(KEY);
    }

    public void setAnswer(ParseObject newAnswer) {
        put(KEY, newAnswer);
    }

    public static ParseQuery<Answer> getAnswerQuery() {
        return ParseQuery.getQuery(Answer.class);
    }
}
