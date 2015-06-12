package app.mapquest.com.mapquest.geofencing;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingEvent;
import com.parse.ParseException;

import java.util.List;

import app.mapquest.com.mapquest.MapDisplay;
import app.mapquest.com.mapquest.R;
import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.api.QuizAnswerScoreUtils;
import app.mapquest.com.mapquest.data.LocationInfo;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p
 * helper methods.
 */
public class GeofenceTransitionsIntentService extends IntentService {
    private static final String TAG = "geofence-transition-svc";

    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "app.mapquest.com.mapquest.geofencing.action.FOO";
    private static final String ACTION_BAZ = "app.mapquest.com.mapquest.geofencing.action.BAZ";

    private static final String EXTRA_PARAM1 = "app.mapquest.com.mapquest.geofencing.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "app.mapquest.com.mapquest.geofencing.extra.PARAM2";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context, String param1, String param2) {
        Log.i(TAG, "StartActionFoo");
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionBaz(Context context, String param1, String param2) {
        Log.i(TAG, "startActionBaz");
        Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public GeofenceTransitionsIntentService() {
        super("GeofenceTransitionsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent");
        if (intent == null) {
            Log.e(TAG,"Broken intent!");
        }
        String gameName = intent.getStringExtra("GameName");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            //If we have more than one, we're doing it wrong.
            if (triggeringGeofences.size() != 1) {
                Log.e(TAG,"Too many fences hit");
            }

            handleHitLocation(gameName,triggeringGeofences.get(0).getRequestId());



            // Send notification and log the transition details.
            //sendNotification(geofenceTransitionDetails);
            //Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
//

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleHitLocation(String gameName,String locationID) {
        String currentQuestion;

//        try {
//            LocationInfo currentLocationInfo = Getting.getGameLocationInfoByID(locationID);
//            currentQuestion = currentLocationInfo.getQuiz();
//        } catch (ParseException e) {
//            throw new IllegalArgumentException("Missing game wtf");
//        }
        sendNotification("BLAHBLAHBLAH");
    }



    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(String notificationDetails) {
        Notification notification = new NotificationCompat.Builder(this)//.setSmallIcon(R.drawable.shopli)
                .setContentTitle("You chased it")
                .setContentText(notificationDetails)
                .setSmallIcon(R.drawable.chest_icon) // addAction
                .build();

        NotificationManager notificationManger =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManger.notify(0, notification);
    }
}
