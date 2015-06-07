package app.mapquest.com.mapquest.api;

import com.parse.ParseObject;

import java.util.List;

import app.mapquest.com.mapquest.data.Answer;
import app.mapquest.com.mapquest.data.EndPoint;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;
import app.mapquest.com.mapquest.data.Point;
import app.mapquest.com.mapquest.data.Quiz;

/**
 * Created by daniellag on 6/7/15.
 */
public class Creating {

    public static void createNewGame(String gameName, List<LocationInfo> locationsInfo, EndPoint endPoint) {
        ParseObject gameObj = new ParseObject("Game");
        gameObj.put(Game.GAME_NAME_KEY, gameName); // TODO: handle same keys inserted.
        gameObj.put(Game.LOCATION_INFO_LIST_KEY, locationsInfo);
        gameObj.put(Game.END_POINT_KEY, endPoint);
        gameObj.saveInBackground();
    }

    public static void createNewLocationInfo(double lat, double lon, String quiz, String answer) {
        new Point().createNewPoint(lat, lon);
        new Quiz().createNewQuiz(quiz);
        new Answer().createNewAnswer(answer);
    }

    public static void createNewEndPoint(Game game, double lat, double lon, String quiz, String answer) {
        Point point = new Point();
        point.setLocation(lat, lon);
        Quiz eQuiz = new Quiz();
        eQuiz.createNewQuiz(quiz);
        Answer eAnswer = new Answer();
        eAnswer.createNewAnswer(answer);

        LocationInfo ePointInfo = new LocationInfo();
        ePointInfo.setPoint(point);
        ePointInfo.setQuiz(eQuiz);
        ePointInfo.setAnswer(eAnswer);

        EndPoint endPoint = new EndPoint();
        endPoint.setGame(game);
        endPoint.setPointInfo(ePointInfo);
    }
}
