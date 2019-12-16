package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiveReview extends AppCompatActivity {
  RatingBar rb;
  TextView comment;
  Button submit;
  String name,email,user;
  String Wemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_review);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name=null;
                email=null;
                user=null;

            } else {
                email =extras.getString("email");
                name = extras.getString("name");
                user = extras.getString("user");

            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
            name= (String) savedInstanceState.getSerializable("name");
            user= (String) savedInstanceState.getSerializable("user");
        }
        rb=(RatingBar) findViewById(R.id.ratingBar2);
        comment=(TextView) findViewById(R.id.comment);
        submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rb.getRating()<=0.0)
                {
                    Toast.makeText(GiveReview.this, "Please Give appropriate Ratting", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(String.valueOf(comment.getText()).isEmpty())
                    {
                        Toast.makeText(GiveReview.this, "Please give some comments", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                       db a=new db();
                       a.execute(email);
                    }
                }
            }
        });


    }


    class db extends AsyncTask<String,Void,String>
    {
        String loc;
        String result="";
        String stime="";

        int z=0;
        int flag=0;
        Button[] bt=new Button[10];

        protected void onPostExecute(String result)
        {try {
            final String[] separated = result.split(":");
            Wemail=separated[0];
            Done d=new Done();
            d.execute(email);

        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://172.20.10.3/FYPHomeASsitant/getWemail.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8");
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

    public class Done extends AsyncTask<String, Void, String> {




        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Transaction t=new Transaction();
                t.execute(email);


            }
            else
                Toast.makeText(GiveReview.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";


            String connectionString = "http://172.20.10.3/FYPHomeASsitant/Done.php";

            try {

                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8");
                Log.e("log_tagaaaaaaaaa", data);

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (Exception e) {
                result = e.getMessage();
            }


            return result;
        }}


    public class Transaction extends AsyncTask<String, Void, String> {




        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Log.e("log_tag", "success");
                Toast.makeText(GiveReview.this, "Thank You. Job Completed", Toast.LENGTH_SHORT).show();

                finish();


            }
            else
                Toast.makeText(GiveReview.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";


            String connectionString = "http://172.20.10.3/FYPHomeASsitant/addReview.php";

            try {

                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("CEmail", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")
                        + "&&" + URLEncoder.encode("WEmail", "UTF-8") + "=" + URLEncoder.encode(Wemail, "UTF-8")
                        + "&&" + URLEncoder.encode("Ratting", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(rb.getRating()), "UTF-8")
                        + "&&" + URLEncoder.encode("Comment", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(comment.getText()), "UTF-8");
                Log.e("log_tagaaaaaaaaa", data);

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                http.disconnect();
                return result;

            } catch (Exception e) {
                result = e.getMessage();
            }


            return result;
        }}


}
