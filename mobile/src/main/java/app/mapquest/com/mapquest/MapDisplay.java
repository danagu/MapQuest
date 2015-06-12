package app.mapquest.com.mapquest;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.mapquest.com.mapquest.api.Getting;
import app.mapquest.com.mapquest.data.Game;
import app.mapquest.com.mapquest.data.LocationInfo;
import app.mapquest.com.mapquest.geofencing.GeofenceMessages;

public class MapDisplay extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener,
        ResultCallback<Status> {

    private static final String TAG = "MapDisplayActivity";

    //Map stuff
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //List of markers to display

    //Location sensing
    private Location mLastLocation;
    LocationRequest mLocationRequest;
    private Boolean mRequestingLocationUpdates = true;
    private Location mCurrentLocation;

    //Geofences
    private ArrayList mGeofenceList = new ArrayList<>(50);
    private PendingIntent mGeofencePendingIntent = null;

    /*
        shit for google API
     */
    private GoogleApiClient mGoogleApiClient;


    //Parse object
    Game mCurrentGame;

    //region basicUIhookups
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);
        buildGoogleApiClient();

        //Get parse data
        try {
            String GameName = this.getIntent().getExtras().getString("GAME");
            mCurrentGame = Getting.getGame(GameName);
        } catch (ParseException e) {
             e.printStackTrace();
        }

        createGeoFences();
    }

    @Override
    protected void onStart()
    {

        super.onStart();
        Log.i(TAG, "Connecting to Google API client");
        mGoogleApiClient.connect();
        setUpMapIfNeeded();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume happened");
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        //removeGeofences();
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onResult(Status status) {
        if (status.isSuccess()) {


            Toast.makeText(
                    this,
                    "Added or removed geofences",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceMessages.getErrorString(this,
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Google API client is connected");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                    13);
            mMap.moveCamera(cu);
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
        loadGeoFences();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateLocationUI();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        Log.i(TAG, "Connection suspended");

        // onConnected() will be called again automatically when the service reconnects
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    //endregion

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    //region MAPDISPLAY
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call  once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            Log.i(TAG,"Obtaining Gmap");
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.i(TAG, "Map is ready");
        if (null == mMap) {
            //We're in the first call
            setUpMap(map);
        }

    }

    public void setUpMap(GoogleMap map) {
        mMap = map;
        mMap.setMyLocationEnabled(true);



        Log.i(TAG, "Adding game markers");
        //Iterate over all locations
        List<LocationInfo> locationList = mCurrentGame.getAllGameLocationsInfo();
        for( LocationInfo l:locationList)
        {
            //add each marker
            map.addMarker(new MarkerOptions()
                        .position((new LatLng(l.getLat(),l.getLon())))
                        .title("Map point"));
        }

        //Add end location
        map.addMarker(new MarkerOptions()
                .position((new LatLng(mCurrentGame.getEndPoint().getLocationInfo().getLat(),
                            mCurrentGame.getEndPoint().getLocationInfo().getLon())))
                .title("End point"));

    }
    //endregion

    //region LOCATIONSENSING
    protected void createLocationRequest() {
        Log.i(TAG, "Creating sensing request for 10s interval high accuracy");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        createLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.i(TAG, "Starting location updates");
    }

    protected void stopLocationUpdates() {
        Log.i(TAG, "Stopping location updates");
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
    private void updateLocationUI() {
        //double latitude = mLastLocation.getLatitude();
        //double longitude = mLastLocation.getLongitude();


    }


    //endregion


    //region Geofences


    //Create the geofences in google format, from our storage.
    private void createGeoFences()
    {
        Log.i(TAG,"Adding geofences");
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId("1")
                        //Daniels home 32.142552, 34.793374
                        //AIS school 32.265064, 34.877173
                .setCircularRegion(
                        32.265064,
                        34.877173,
                        1000
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setLoiteringDelay(500)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build());


        List<LocationInfo> locationList = mCurrentGame.getAllGameLocationsInfo();
        int counter = 1;
        for( LocationInfo l:locationList)
        {
            //add each marker
            mGeofenceList.add(new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId(String.valueOf(counter))
                            //Daniels home 32.142552, 34.793374
                            //AIS school 32.265064, 34.877173
                    .setCircularRegion(
                            l.getLat(),
                            l.getLon(),
                            250
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setLoiteringDelay(500)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build());
            counter++;
        }

        //Add end location
        mGeofenceList.add(
                new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this
                        // geofence.
                        .setRequestId("endpoint")
                                //Daniels home 32.142552, 34.793374
                                //AIS school 32.265064, 34.877173
                                .setCircularRegion(
                                        mCurrentGame.getEndPoint().getLocationInfo().getLat(),
                                        mCurrentGame.getEndPoint().getLocationInfo().getLon(),
                                        250
                                )
                                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                                .setLoiteringDelay(500)
                                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                                .build());

    }

    private void loadGeoFences()
    {
//        LocationServices.GeofencingApi.addGeofences(
//                mGoogleApiClient,
//                getGeofencingRequest(),
//                getGeofencePendingIntent()
//        ).setResultCallback(this);
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER
        );
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private void removeGeofences()
    {
        // Remove geofences.
        LocationServices.GeofencingApi.removeGeofences(
                mGoogleApiClient,
                // This is the same pending intent that was used in loadGeoFences().
                getGeofencePendingIntent()
        ).setResultCallback(this); // Result processed in onResult().
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        //Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        //return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return null;
    }

    //endregion
}