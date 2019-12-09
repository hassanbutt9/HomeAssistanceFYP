package com.edu.homeassistancefyp;


import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ProfileFragment.OnFragmentInteractionListener,IndexFragment.OnFragmentInteractionListener{
    private DrawerLayout drawerLayout;
    String newString;
    String CustomerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString=null;
                CustomerName=null;
            } else {
                newString =extras.getString("email");
                CustomerName = extras.getString("name");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("email");
            CustomerName= (String) savedInstanceState.getSerializable("name");
        }
        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView2.getHeaderView(0);
        TextView navName = (TextView) headerView.findViewById(R.id.customerName);
        navName.setText(CustomerName);
        TextView cusEmail = (TextView) headerView.findViewById(R.id.customerEmail);
        cusEmail.setText(newString);

        Bundle bundle = new Bundle();
        bundle.putString("ARG_PARAM1", newString);
        bundle.putString("ARG_PARAM2",CustomerName);
// set MyFragment Arguments
        IndexFragment myObj = new IndexFragment();
        myObj.setArguments(bundle);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
       this.setSupportActionBar(myToolbar);
       drawerLayout = findViewById(R.id.drawer_Layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myObj).commit();
            navigationView.setCheckedItem(R.id.nav_Home);
        }
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new IndexFragment()).commit();
                break;
            case R.id.nav_myJobs:
                Intent viewJob  = new Intent(this,new timer().getClass());
                viewJob.putExtra("email",newString);
                viewJob.putExtra("name",CustomerName);
                viewJob.putExtra("user","customer");
                startActivity(viewJob);
                break;
            case R.id.nav_Logout:
                Intent login  = new Intent(this,new LoginInterface().getClass());
                startActivity(login);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
