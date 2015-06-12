package app.mapquest.com.mapquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;

/**
 * Created by daniellag on 6/13/15.
 */
public class DispatchActivity extends Activity {
    /**
     * Activity which starts an intent for either the logged in (MainActivity) or logged out
     * (SignUpOrLoginActivity) activity.
     */
        public DispatchActivity() {
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Check if there is current user info
            if (ParseUser.getCurrentUser() != null) {
                // Start an intent for the logged in activity
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // Start and intent for the logged out activity
                startActivity(new Intent(this, WelcomeActivity.class));
            }
        }

    }