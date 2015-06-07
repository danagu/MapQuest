package app.mapquest.com.mapquest.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by daniellag on 6/5/15.
 */
@ParseClassName("Quiz")
public class Quiz extends ParseObject {

    public static final String KEY = "quiz";

    public void createNewQuiz(String quiz) {
        put(KEY, quiz);
    }

    ////////////////////////

    public ParseObject getQuiz() {
        return getParseObject(KEY);
    }

    public void setQuiz(ParseObject newQuiz) {
        put(KEY, newQuiz);
    }

    public static ParseQuery<Quiz> getQuizQuery() {
        return ParseQuery.getQuery(Quiz.class);
    }
}
