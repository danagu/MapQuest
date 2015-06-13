package app.mapquest.com.mapquest.api;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.TreeMap;

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
        int currentScore = ParseUser.getCurrentUser().getInt("SCORE");
        return currentScore;
    }

    public static TreeMap<Integer, String> getScoreBoardSorted(String gameName) throws ParseException {
        TreeMap<Integer, String> scoreBoard = new TreeMap<Integer, String>();
        scoreBoard.descendingKeySet(); //?
        List<ParseUser> allUsersInGame = Getting.getAllUsersOfGame(gameName);
        ParseObject.pinAllInBackground(allUsersInGame);
        for(ParseUser user : allUsersInGame) {
            int userScore = getUserScoreByUser(user);
            scoreBoard.put(userScore, user.getUsername());
        }
        return scoreBoard;
    }

    private static Integer getUserScoreByUser(ParseUser user) {
        return user.getInt("SCORE");
    }
}
