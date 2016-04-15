package com.rushbox.android.rushboxapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rushbox.android.rushboxapp.fragments.MapFragment;
import com.rushbox.android.rushboxapp.fragments.WelcomeFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView firstText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_first_text);
        //firstText.setText("Probando");

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            Fragment fragment = null;
            SharedPreferences preferences =
                    getSharedPreferences(getString(R.string.preference_app_name), Context.MODE_PRIVATE);

            if (!preferences.getBoolean(getString(R.string.preference_registered), false)) {
                // Create an instance of ExampleFragment
                toolbar.setVisibility(View.GONE);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                fragment = new WelcomeFragment();
            } else {
                if (!preferences.getBoolean(getString(R.string.preference_logged), false)) {
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                } else {
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    fragment = new MapFragment();
                    //User user = (User) getIntent().getExtras().getSerializable(getString(R.string.user_passed));
                    //startActivity(new Intent(MainActivity.this, NavActivity.class));
                    //finish();
                }
                //startActivity(new Intent(MainActivity.this, MapsActivity.class));
                //finish();
            }      // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments


            if (fragment != null) {

                fragment.setArguments(getIntent().getExtras());
                // Add the fragment to the 'fragment_container' FrameLayout
                //getSupportFragmentManager().beginTransaction()
                //.add(R.id.fragment_container, secondFragment).commit();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, fragment).commit();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
