package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
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

public class profileActivity extends AppCompatActivity implements workersServices.OnFragmentInteractionListener,Reviews.OnFragmentInteractionListener {
RatingBar RB;
TextView locat;
TextView star;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String newString;
        String WorkerName;
       // String location;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString=null;
                WorkerName=null;
               // location=null;
            } else {
                newString =extras.getString("email");
                WorkerName = extras.getString("name");
                //location= extras.getString("location");

            }
        } else {
            newString= (String) savedInstanceState.getSerializable("email");
            WorkerName= (String) savedInstanceState.getSerializable("name");
            //location= (String) savedInstanceState.getSerializable("location");
        }
        RB=(RatingBar)findViewById(R.id.ratingBar);
        locat=(TextView) findViewById(R.id.loc);
        star=(TextView) findViewById(R.id.star);
        db d=new db();
        d.execute(newString);
        TextView loc=(TextView) findViewById(R.id.loc);
        //loc.setText(location);
        TextView name=(TextView) findViewById(R.id.textView8);
        name.setText(WorkerName);


        final Bundle bundle = new Bundle();
        bundle.putString("ARG_PARAM1", newString);
        bundle.putString("ARG_PARAM2",WorkerName);
// set MyFragment Arguments
      final workersServices  ws = new workersServices();
        ws.setArguments(bundle);
           getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,ws).commit();

           Button service=(Button)findViewById(R.id.services);
           service.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,ws).commit();
               }
           });

           Button review=(Button) findViewById(R.id.review);
           review.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                Reviews rv =new Reviews() ;
               rv.setArguments(bundle);
                   getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,rv).commit();
               }
           });

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    class db extends AsyncTask<String,Void,String>
    {
        String loc;
        String result="";
        protected void onPostExecute(String result)
        {try {
            String[] separated = result.split(":");
            RB.setRating(Float.parseFloat(separated[0]));
            star.setText(separated[0].substring(0,3));
            locat.setText(separated[1]);
        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/profileAvg.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(loc, "UTF-8");
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
