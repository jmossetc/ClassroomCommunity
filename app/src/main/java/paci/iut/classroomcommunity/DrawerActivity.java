package paci.iut.classroomcommunity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.result.Credentials;

import paci.iut.classroomcommunity.utils.CredentialsManager;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AuthenticationAPIClient authenticationClient;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment = (Fragment) getFragmentManager().findFragmentById(R.id.fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Auth0 auth0 = new Auth0(this);
        auth0.setOIDCConformant(true);
        authenticationClient = new AuthenticationAPIClient(auth0);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragAccueil accueil = new FragAccueil();
        ft.replace(R.id.fragment, accueil);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            FragAccueil accueil = new FragAccueil();
            ft.replace(R.id.fragment, accueil);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();

            Button refreshTokenButton = (Button) findViewById(R.id.refreshTokenButton);
            Button logoutButton = (Button) findViewById(R.id.logout);
            Button friendsButton = (Button) findViewById(R.id.friends_button);
            Button quizzButton = (Button) findViewById(R.id.quizzButton);

            quizzButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), QuizzActivity.class);
                    startActivity(intent);
                }
            });

            friendsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ListeAmis.class);
                    startActivity(intent);
                }
            });
            refreshTokenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    renewAuthentication();
                }
            });
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
    }


    private void logout() {
        CredentialsManager.deleteCredentials(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
