package app.mapquest.com.mapquest.api;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by daniellag on 6/13/15.
 */
public class ScoresUtils {

    private static int currentUsersScore;

    public static void addScoreToUser(final int score) throws ParseException {
        final int currentScore = getCurrentUsersScore();
        String userId = ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseUser> query = ParseUser.getQuery();

// Retrieve the object by id
        query.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                parseUser.put("SCORE", score + currentScore);
                parseUser.saveInBackground();
            }
        });

    }

    public static int getCurrentUsersScore() {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        int currentScore = ParseUser.getCurrentUser().getInt("SCORE");
        return currentScore;
    }
}
