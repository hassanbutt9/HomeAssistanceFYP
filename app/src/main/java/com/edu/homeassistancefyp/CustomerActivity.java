package com.edu.homeassistancefyp;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ProfileFragment.OnFragmentInteractionListener,IndexFragment.OnFragmentInteractionListener{
    private DrawerLayout drawerLayout;
    String newString;
    String CustomerName;
    String PID,Job,Status;
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
                db d=new db();
                d.execute(newString);
                break;
            case R.id.nav_Logout:
                Intent login  = new Intent(this,new LoginInterface().getClass());
                startActivity(login);
                finish();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }

    class db extends AsyncTask<String,Void,String>
    {
        String loc;
        String result="";


        protected void onPostExecute(String result)
        {

            try {
            if(result!=null)
            {

                final String[] separated = result.split(":");
                PID=separated[0];
                Job=separated[1];
                Status=separated[2];

                if(Status.equals("Accepted"))
                {
                Intent i=new Intent(CustomerActivity.this,new ViewJob().getClass());
                i.putExtra("email",newString);
                i.putExtra("name",CustomerName);
                i.putExtra("user","customer");
                startActivity(i);
                }
                else if(Status.equals("Started"))
                {
                    Intent i=new Intent(CustomerActivity.this,new timer().getClass());
                    i.putExtra("email",newString);
                    i.putExtra("name",CustomerName);
                    i.putExtra("user","customer");
                    startActivity(i);
                }
                else if(Status.equals("Completed"))
                {
                    Intent i=new Intent(CustomerActivity.this,new Payment().getClass());
                    i.putExtra("email",newString);
                    i.putExtra("name",CustomerName);
                    i.putExtra("user","customer");
                    i.putExtra("PID",PID);
                    startActivity(i);

                }
            }
            else
            {

            }
        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }

        }
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/CustomerActivity.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(newString, "UTF-8");
                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                Log.e("log_tag", result);
                reader.close();
                ips.close();
                http.disconnect();
                return result;


            } catch (Exception e) {
                result = e.getMessage();
            }
            return result;
        }
    }
}
