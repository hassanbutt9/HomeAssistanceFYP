package com.edu.homeassistancefyp;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import androidx.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class WorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ProfileFragment.OnFragmentInteractionListener,WorkerFragmentIndex.OnFragmentInteractionListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(myToolbar);
        drawerLayout = findViewById(R.id.drawer_Layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WorkerFragmentIndex()).commit();
            navigationView.setCheckedItem(R.id.nav_Home);


        }

        /*Intent i=new Intent(this,MapsActivity.class);
        i.putExtra("msg", "worker");
        startActivity(i);
        TextView mTextView = (TextView) findViewById(R.id.location);
        Intent intent = getIntent();
        String message = intent.getStringExtra("message");
        mTextView.setText("");
        mTextView.setText(message);*/
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_myProfile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
                break;
            case R.id.nav_Home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WorkerFragmentIndex()).commit();

                break;
            case R.id.nav_Logout:
                Intent login2  = new Intent(this,new LoginInterface().getClass());
                startActivity(login2);
                break;
            case R.id.nav_myJobs:
                startActivity(new Intent(this,new ManageServices().getClass()));
            case R.id.nav_Location:
                startActivity(new Intent(this,new MapsActivity().getClass()));


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onFragmentInteraction(Uri uri){

    }


}
