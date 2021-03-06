package com.adgvit.iosfusion2017;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NavDraActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public boolean viewIsAtHome;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_dra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navView=navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        displayview(R.id.time);

        SharedPreferences sp = getSharedPreferences("key", 0);
        String partname = sp.getString("Name", "");
        String partreg = sp.getString("Reg_Num", "");
        Log.d("Name", partname);
        Log.d("Reg_Num", partreg);
        TextView textView= (TextView) navView.findViewById(R.id.pname);
        textView.setText(partname);
        TextView textView2= (TextView) navView.findViewById(R.id.preg);
        textView2.setText(partreg);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } if (!viewIsAtHome) {
            displayview(R.id.time); //display the time fragment
        } else {
            moveTaskToBack(true);  //If view is in time fragment, exit application
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_dra, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem=menu.findItem(R.id.attend);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fragment frag=new Attendance();
                viewIsAtHome = false;
                getSupportActionBar().setTitle("Attendance");
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame,frag);
                ft.commit();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayview(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void displayview(int viewId){

        Fragment fragment=null;
        String title = getString(R.string.app_name);
        if (viewId == R.id.time) {
            navigationView.setCheckedItem(viewId);
            getSupportActionBar().setTitle("Timeline");
            fragment=new Timeline();
            viewIsAtHome = true;
        } else if (viewId == R.id.wifi) {
            getSupportActionBar().setTitle("Wi-fi");
            fragment=new Wifi();
            viewIsAtHome = false;
        } else if (viewId == R.id.refresh) {
            viewIsAtHome = false;
            getSupportActionBar().setTitle("Refreshments");
            fragment = new Refreshments();
        } else if (viewId == R.id.speak) {
            viewIsAtHome = false;
            getSupportActionBar().setTitle("Speaker");
            fragment = new Speaker();
        } else if (viewId == R.id.forum) {
            viewIsAtHome = false;
            getSupportActionBar().setTitle("Forum");
            fragment=new Forum();
        }
        else if (viewId == R.id.aboutus) {
            viewIsAtHome = false;
            getSupportActionBar().setTitle("About Us");
            fragment=new AboutUs();
        }
        else if (viewId == R.id.develop) {
            viewIsAtHome = false;
            getSupportActionBar().setTitle("Developers");
            fragment = new Developers();
        }
        if (fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame,fragment);
            ft.commit();
        }
    }
}
