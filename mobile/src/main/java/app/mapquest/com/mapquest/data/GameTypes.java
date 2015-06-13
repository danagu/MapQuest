package app.mapquest.com.mapquest.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by daniellag on 6/13/15.
 */
public enum GameTypes {

    BY_CAR, BY_BIKE, BY_FEET;

    private static final List<GameTypes> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static GameTypes randomLetter()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
