package com.example.user.tugasbesarpab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected FragmentManager fragmentManager;
    protected MainMenuFragment mainMenuFragment;
    protected SettingsFragment settingsFragment;
    protected HighScoreFragment highScoreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.mainMenuFragment=MainMenuFragment.newInstance();
        this.settingsFragment=SettingsFragment.newInstance();
        this.highScoreFragment=HighScoreFragment.newInstance();

        this.fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction ft=this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container,this.mainMenuFragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if(this.settingsFragment.isVisible()){
            FragmentTransaction ft=this.fragmentManager.beginTransaction();
            ft.show(this.mainMenuFragment);
            ft.hide(this.settingsFragment);
            ft.commit();
            getSupportActionBar().setTitle("TugasBesarPAB");
        }
        else if(this.highScoreFragment.isVisible()){
            FragmentTransaction ft=this.fragmentManager.beginTransaction();
            ft.show(this.mainMenuFragment);
            ft.hide(this.highScoreFragment);
            ft.commit();
            getSupportActionBar().setTitle("TugasBesarPAB");
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        FragmentTransaction ft=this.fragmentManager.beginTransaction();

        if (id == R.id.nav_highscore) {
            if(this.highScoreFragment.isAdded()){
                ft.show(this.highScoreFragment);
            }
            else{
                ft.add(R.id.fragment_container,this.highScoreFragment);
            }

            if(this.settingsFragment.isAdded()){
                ft.hide(this.settingsFragment);
            }
            ft.hide(this.mainMenuFragment);

            getSupportActionBar().setTitle("High Score");

            ft.commit();
        } else if (id == R.id.nav_settings) {
            if(this.settingsFragment.isAdded()){
                ft.show(this.settingsFragment);
            }
            else{
                ft.add(R.id.fragment_container,this.settingsFragment);
            }

            if(this.highScoreFragment.isAdded()){
                ft.hide(this.highScoreFragment);
            }

            ft.hide(this.mainMenuFragment);
            getSupportActionBar().setTitle("Settings");
            ft.commit();
        } else if (id == R.id.nav_exit) {
            super.onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
