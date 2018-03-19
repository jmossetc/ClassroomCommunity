package paci.iut.classroomcommunity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.UserProfile;

import paci.iut.classroomcommunity.utils.CredentialsManager;

import static paci.iut.classroomcommunity.utils.CredentialsManager.saveCredentials;


public class LoginActivity extends AppCompatActivity {

    private Auth0 auth0;
    private Lock lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);

        final Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });


        String accessToken = CredentialsManager.getCredentials(this).getAccessToken();
        if (accessToken == null) {
            return;
        }

        //If the token exists, try to fetch the associated user info
        loginButton.setEnabled(false);
        AuthenticationAPIClient aClient = new AuthenticationAPIClient(auth0);
        aClient.userInfo(accessToken)
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(final UserProfile payload) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Automatic Login Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        runOnUiThread(new Runnable() {

                            public void run() {
                                loginButton.setEnabled(true);
                                Toast.makeText(LoginActivity.this, "Session Expired, please Log In", Toast.LENGTH_SHORT).show();
                            }
                        });
                        CredentialsManager.deleteCredentials(LoginActivity.this);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your own Activity codes
        if (lock != null) {
            lock.onDestroy(this);
        }
        lock = null;
        auth0 = null;
    }

    private void doLogin() {
        lock = Lock.newBuilder(auth0, callback)
                .withScheme("demo")
                .withAudience(String.format("https://%s/userinfo", getString(R.string.com_auth0_domain)))
                // Add parameters to the Lock Builder
                .build(this);
        startActivity(lock.newIntent(this));
    }

    private final LockCallback callback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(@NonNull Credentials credentials) {
            Toast.makeText(getApplicationContext(), "Log In - Success", Toast.LENGTH_SHORT).show();
            CredentialsManager.saveCredentials(LoginActivity.this, credentials);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        @Override
        public void onCanceled() {
            Toast.makeText(getApplicationContext(), "Log In - Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(LockException error) {
            Toast.makeText(getApplicationContext(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
        }

    };


}