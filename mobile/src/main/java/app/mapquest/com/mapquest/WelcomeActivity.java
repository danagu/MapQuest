package app.mapquest.com.mapquest;

/**
 * Created by daniellag on 6/13/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.FrameLayout;

import static app.mapquest.com.mapquest.R.id.special_button_login;

/**
 * Activity which displays a registration screen to the user.
 */
public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //special button click handler
        FrameLayout specialButtonLogin = (FrameLayout) findViewById(special_button_login);
        specialButtonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            }
        });

        //special button click handler
        FrameLayout specialButtonLogout = (FrameLayout) findViewById(R.id.special_button_logout);
        specialButtonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
            }
        });
    }
}
