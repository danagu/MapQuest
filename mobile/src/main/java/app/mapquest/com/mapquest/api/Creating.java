package app.mapquest.com.mapquest.api;

import com.parse.ParseException;

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

    public static Game createNewGame(String gameName, List<LocationInfo> locationsInfo, EndPoint endPoint) {
        Game newGame = new Game(); // TODO: make sure that the name is unique.
        newGame.setGameName(gameName);
        for(LocationInfo locationInfo: locationsInfo) {
            newGame.addPointToGame(locationInfo);
        }
        newGame.setEndPoint(endPoint);
        newGame.saveInBackground();
        return newGame;
    }

    public static LocationInfo createNewLocationInfo(double lat, double lon, String quiz, String answer) throws ParseException {
        Point newPoint = new Point();
        newPoint.setLocation(lat, lon);
        Quiz newQuiz = new Quiz();
        newQuiz.createNewQuiz(quiz);
        Answer newAnswer = new Answer();
        newAnswer.createNewAnswer(answer);

        LocationInfo newPointInfo = new LocationInfo();
        newPointInfo.setPoint(newPoint);
        newPointInfo.setQuiz(newQuiz);
        newPointInfo.setAnswer(newAnswer);

        newPointInfo.saveInBackground();
        return newPointInfo;

    }

    //TODO: Should save the temp objects used?!

    public static EndPoint createNewEndPoint(double lat, double lon, String quiz, String answer) throws ParseException {
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
//        endPoint.setGame(game);
        endPoint.setPointInfo(ePointInfo);

        endPoint.saveInBackground();

        return endPoint;
    }
}
