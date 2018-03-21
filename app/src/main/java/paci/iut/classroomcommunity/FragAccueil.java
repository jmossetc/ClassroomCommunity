package paci.iut.classroomcommunity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auth0.android.authentication.AuthenticationAPIClient;

/**
 * Created by Vincent
 * on 21/03/2018.
 */

public class FragAccueil extends Fragment {

    private AuthenticationAPIClient authenticationClient;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_main, container, false);
            return rootView;
        }

        /*
    private void renewAuthentication() {
        String refreshToken = CredentialsManager.getCredentials(this).getRefreshToken();
        authenticationClient.renewAuth(refreshToken).start(new BaseCallback<Credentials, AuthenticationException>() {
            @Override
            public void onSuccess(final Credentials payload) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(DrawerActivity.this, "New access_token: " + payload.getAccessToken(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(AuthenticationException error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(DrawerActivity.this, "Failed to get the new access_token", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }*/




    }

