package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
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

public class Payment extends AppCompatActivity {
String name,email,user,PID;
FrameLayout fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name=null;
                email=null;
                user=null;
                PID=null;
            } else {
                email =extras.getString("email");
                name = extras.getString("name");
                user = extras.getString("user");
                PID = extras.getString("PID");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
            name= (String) savedInstanceState.getSerializable("name");
            user= (String) savedInstanceState.getSerializable("user");
            PID= (String) savedInstanceState.getSerializable("PID");
        }
        fl=(FrameLayout) findViewById(R.id.frameLayout6);
            db d=new db();
            d.execute(PID);
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
            LinearLayout rootView = new LinearLayout(Payment.this);
            rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.setOrientation(LinearLayout.VERTICAL);
            fl.addView(rootView);

            z=0;
            for (int i = 0; i <= separated.length - 5; i++) {

                LinearLayout rootView2 = new LinearLayout(Payment.this);
                rootView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView2.setOrientation(LinearLayout.VERTICAL);


                TextView b = new TextView(Payment.this);
                stime = separated[i];
                b.setText(stime);
                b.setPadding(30, 30, 0, 0);
                b.setTextColor(Color.BLACK);
                b.setTextSize(24);
                rootView2.addView(b);
                i++;

                TextView b5 = new TextView(Payment.this);
                b5.setPadding(30, 15, 30, 0);
                b5.setTextSize(25);
                String stime5 = separated[i];
                b5.setText("Per Hour Rate: "+stime5);
                b5.setTextColor(Color.BLACK);
                rootView2.addView(b5);
                i++;


                TextView b1 = new TextView(Payment.this);
                b1.setPadding(30, 15, 30, 0);
                b1.setTextSize(22);
                String stime1 = separated[i]+":"+separated[i+1];
                b1.setText("Start Time: "+stime1);
                b1.setTextColor(Color.BLACK);
                rootView2.addView(b1);
                i++;
                i++;
                final View vline1 = new View(Payment.this);
                vline1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                vline1.setBackgroundColor(Color.RED);
                rootView2.addView(vline1);


                TextView b2 = new TextView(Payment.this);
                b2.setPadding(30, 15, 30, 0);
                b2.setTextSize(22);
                String stime2 = separated[i]+":"+separated[i+1];
                b2.setText("End Time: "+stime2);
                b2.setTextColor(Color.BLACK);
                rootView2.addView(b2);
                i++;

                i++;

                final View vline2 = new View(Payment.this);
                vline2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                vline2.setBackgroundColor(Color.RED);
                rootView2.addView(vline2);

                TextView b4 = new TextView(Payment.this);
                b4.setPadding(30, 30, 30, 0);
                b4.setTextSize(25);

                b4.setText("Estimated Time : ");
                b4.setTextColor(Color.BLACK);
                rootView2.addView(b4);



                TextView b6 = new TextView(Payment.this);
                b6.setPadding(30, 15, 30, 0);
                b6.setTextSize(25);

                b6.setText("Total Payment: ");
                b6.setTextColor(Color.BLACK);
                rootView2.addView(b6);




                if(user.equals("customer"))
                {
                    Button start=new Button(Payment.this);
                    start.setPadding(30, 30, 30, 30);
                    start.setTextSize(25);
                    start.setText("Pay By PayPAL");
                    start.setTextColor(Color.BLACK);
                    rootView2.addView(start);


                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


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

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/payment.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("PID", "UTF-8")+"="+URLEncoder.encode(PID, "UTF-8");
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
