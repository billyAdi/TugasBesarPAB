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
        implements NavigationView.OnNavigationItemSelectedListener,FragmentListener {
    protected FragmentManager fragmentManager;
    protected MainMenuFragment mainMenuFragment;
    protected SettingsFragment settingsFragment;
    protected HighScoreFragment highScoreFragment;
    protected CanvasFragment canvasFragment;

    protected DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;

    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(toggle);
        this.toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.mainMenuFragment=MainMenuFragment.newInstance();
        this.settingsFragment=SettingsFragment.newInstance();
        this.highScoreFragment=HighScoreFragment.newInstance();
        this.canvasFragment=CanvasFragment.newInstance();

        this.fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction ft=this.fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container,this.mainMenuFragment);
        ft.add(R.id.fragment_container,this.highScoreFragment);
        ft.hide(this.highScoreFragment);
        ft.commit();

        this.presenter=new Presenter(this);

    }

    public Presenter getPresenter(){
        return this.presenter;
    }

    public void updateListView(){
       this.highScoreFragment.updateListView();
    }

    @Override
    public void onBackPressed() {

        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        }
        else if(this.settingsFragment.isVisible()){
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
        else if(this.canvasFragment.isVisible()){
           this.changePage(1);
           this.canvasFragment.stopTimer();
        }
        else {
            super.onBackPressed();
        }
    }

    public void setDrawerState(boolean isEnabled) {


        if ( isEnabled ) {
            this.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            this.toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            this.toggle.setDrawerIndicatorEnabled(true);
            this.toggle.syncState();

        }
        else {
            this.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            this.toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            this.toggle.setDrawerIndicatorEnabled(false);
            this.toggle.syncState();
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

        this.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void changePage(int i) {
        FragmentTransaction ft=this.fragmentManager.beginTransaction();
        if(i==1){
            ft.show(this.mainMenuFragment);
            ft.hide(this.canvasFragment);
            ft.commit();
            this.setDrawerState(true);
        }
        else if(i==2){

            ft.remove(this.canvasFragment);
            this.canvasFragment=CanvasFragment.newInstance();
            ft.add(R.id.fragment_container,this.canvasFragment);
            ft.hide(this.mainMenuFragment);
            ft.commit();
            this.setDrawerState(false);
        }

    }

    @Override
    public void resetCanvasFragment() {
        FragmentTransaction ft=this.fragmentManager.beginTransaction();
        ft.remove(this.canvasFragment);
        this.canvasFragment=CanvasFragment.newInstance();
        ft.add(R.id.fragment_container,this.canvasFragment);
        ft.commit();
    }
}
