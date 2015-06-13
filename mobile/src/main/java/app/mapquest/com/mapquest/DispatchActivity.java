package app.mapquest.com.mapquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.parse.ParseUser;

/**
 * Created by daniellag on 6/13/15.
 */
public class DispatchActivity extends ActionBarActivity {
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
                startActivity(new Intent(this, MenuActivity.class));
            } else {
                // Start and intent for the logged out activity
                startActivity(new Intent(this, WelcomeActivity.class));
            }
        }

    }