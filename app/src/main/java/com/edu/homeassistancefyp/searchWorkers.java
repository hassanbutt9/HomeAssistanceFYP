package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

public class searchWorkers extends AppCompatActivity {
    String email;
    String categorie;
    String location;
    FrameLayout fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_workers);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email=null;
                categorie=null;
                location=null;
            } else {
                email =extras.getString("email");
                categorie = extras.getString("categorie");
                location=extras.getString("location");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
            categorie= (String) savedInstanceState.getSerializable("categorie");
            location=(String) savedInstanceState.getSerializable("location");
        }
        fl =(FrameLayout)findViewById(R.id.frameLayout2);
        fl.setBackgroundColor(Color.WHITE);
        db d=new db();
        d.execute(categorie);

    }
    class db extends AsyncTask<String,Void,String>
    {
        String loc;
        String result="";
        protected void onPostExecute(String result)
        {try {
            String[] separated = result.split(":");
            LinearLayout rootView = new LinearLayout(searchWorkers.this);
            rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.setOrientation(LinearLayout.VERTICAL);
            fl.addView(rootView);

            for (int i = 0; i <= separated.length - 3; i++) {
                LinearLayout rootView2 = new LinearLayout(searchWorkers.this);
                rootView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView2.setOrientation(LinearLayout.VERTICAL);

                TextView b = new TextView(searchWorkers.this);
                String stime = separated[i];
                b.setText(stime);
                Log.e("log_tagname", stime);
                b.setPadding(50, 30, 0, 0);
                b.setTextColor(Color.BLACK);
                b.setTextSize(28);
                rootView2.addView(b);
                i++;
                TextView b1 = new TextView(searchWorkers.this);
                b1.setPadding(30, 15, 30, 0);
                b1.setTextSize(20);
                String stime1 = separated[i];
                b1.setText(stime1);
                b1.setTextColor(Color.BLACK);
                rootView2.addView(b1);
                i++;
                TextView b2 = new TextView(searchWorkers.this);
                b2.setPadding(30, 15, 0, 0);
                b2.setTextSize(20);
                String stime2 = separated[i];
                b2.setText("Per Hour Rate : "+stime2);
                b2.setTextColor(Color.BLACK);
                rootView2.addView(b2);

                LinearLayout rootView3 = new LinearLayout(searchWorkers.this);
                rootView3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView3.setOrientation(LinearLayout.HORIZONTAL);
                Button b7=new Button(searchWorkers.this);
                b7.setPadding(10, 15, 0, 20);
                b7.setTextSize(20);
                b7.setText("BOOK");
                b7.setTextColor(Color.BLACK);
                rootView3.addView(b7);

                Button b8=new Button(searchWorkers.this);
                b8.setPadding(10, 15, 0, 20);
                b8.setTextSize(20);
                b8.setText("PROFILE");
                b8.setTextColor(Color.BLACK);
                rootView3.addView(b8);
                rootView2.addView(rootView3);
                rootView.addView(rootView2);
                final View vline1 = new View(searchWorkers.this);
                vline1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                vline1.setBackgroundColor(Color.RED);
                rootView.addView(vline1);

            }
        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://192.168.10.7/FYPHomeASsitant/relatedWorker.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("categorie", "UTF-8")+"="+URLEncoder.encode(categorie, "UTF-8");
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
