package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

public class ViewJob extends AppCompatActivity {
String name,email,user;
    FrameLayout fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job);
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
        fl=(FrameLayout) findViewById(R.id.frameLayout4);
        db d=new db();
        d.execute(email);
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
            LinearLayout rootView = new LinearLayout(ViewJob.this);
            rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.setOrientation(LinearLayout.VERTICAL);
            fl.addView(rootView);
            TextView c=new TextView(ViewJob.this);
            c.setText("CUSTOMER");
            c.setPadding(15, 10, 0, 0);
            c.setTextColor(Color.BLACK);
            c.setTextSize(28);
            rootView.addView(c);
            z=0;
            for (int i = 0; i <= separated.length - 5; i++) {
                LinearLayout rootView2 = new LinearLayout(ViewJob.this);
                rootView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView2.setOrientation(LinearLayout.VERTICAL);

                TextView b = new TextView(ViewJob.this);
                stime = separated[i];
                b.setText(stime);
                b.setPadding(50, 30, 0, 0);
                b.setTextColor(Color.BLACK);
                b.setTextSize(22);
                rootView2.addView(b);
                i++;
                TextView b1 = new TextView(ViewJob.this);
                b1.setPadding(30, 15, 30, 0);
                b1.setTextSize(20);
                String stime1 = separated[i];
                b1.setText(stime1);
                b1.setTextColor(Color.BLACK);
                rootView2.addView(b1);
                i++;
                final View vline1 = new View(ViewJob.this);
                vline1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                vline1.setBackgroundColor(Color.RED);
                rootView2.addView(vline1);

                TextView d=new TextView(ViewJob.this);
                d.setText("WORKER");
                d.setPadding(15, 50, 0, 0);
                d.setTextColor(Color.BLACK);
                d.setTextSize(29);
                rootView2.addView(d);

                TextView b2 = new TextView(ViewJob.this);
                b2.setPadding(50, 30, 0, 0);
                b2.setTextSize(22);
                String stime2 = separated[i];
                b2.setText(stime2);
                b2.setTextColor(Color.BLACK);
                rootView2.addView(b2);
                i++;

                TextView b3 = new TextView(ViewJob.this);
                b3.setPadding(30, 15, 30, 0);
                b3.setTextSize(20);
                String stime3 = separated[i];
                b3.setText(stime1);
                b3.setTextColor(Color.BLACK);
                rootView2.addView(b3);
                i++;

                final View vline2 = new View(ViewJob.this);
                vline2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                vline2.setBackgroundColor(Color.RED);
                rootView2.addView(vline2);

                TextView b4 = new TextView(ViewJob.this);
                b4.setPadding(30, 30, 30, 0);
                b4.setTextSize(25);
                String stime4 = separated[i];
                b4.setText(stime4);
                b4.setTextColor(Color.BLACK);
                rootView2.addView(b4);
                i++;

                TextView b5 = new TextView(ViewJob.this);
                b5.setPadding(30, 15, 30, 0);
                b5.setTextSize(25);
                String stime5 = separated[i];
                b5.setText("Per Hour Rate"+stime5);
                b5.setTextColor(Color.BLACK);
                rootView2.addView(b5);
                if(user.equals("customer"))
                {
                    Button start=new Button(ViewJob.this);
                    start.setPadding(30, 30, 30, 0);
                    start.setTextSize(25);
                    start.setText("Start Job");
                    start.setTextColor(Color.BLACK);
                    rootView2.addView(start);


                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        StartJob sj=new StartJob();
                        sj.execute(email);
                        }
                    });
                }


                rootView.addView(rootView2);


            }
        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/viewJob.php";

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
    public class StartJob extends AsyncTask<String, Void, String> {

        String WEmail,Job,CName;



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Log.e("log_tag", "Rejected");
                Toast.makeText(ViewJob.this, "Job Started.See Status in My Jobs", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(ViewJob.this,new timer().getClass());
                i.putExtra("email",email);
                i.putExtra("name",name);
                i.putExtra("user",user);
                startActivity(i);
                finish();


            }
            else
                Toast.makeText(ViewJob.this, "Sory .Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";

            Log.e("log_tagaaaaaaaaa", CName +" "+WEmail+" "+Job);
            String connectionString = "http://192.168.10.6/FYPHomeASsitant/StartJob.php";

            try {
                Date d=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
                String currentDateTimeString = sdf.format(d);

                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")
                        +"&&"+URLEncoder.encode("startTime", "UTF-8")+"="+URLEncoder.encode(currentDateTimeString, "UTF-8");;
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
