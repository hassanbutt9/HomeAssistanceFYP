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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PendingJobs extends AppCompatActivity {
    String email;

    FrameLayout fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_jobs);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email=null;
            } else {
                email =extras.getString("email");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
        }
        fl =(FrameLayout)findViewById(R.id.fl);
        fl.setBackgroundColor(Color.WHITE);
        db d=new db();
        d.execute(email);
    }

    class db extends AsyncTask<String,Void,String>
    {
        String loc;
        String result="";
        String stime="";
        int z=0;
        int y=0;
        Button[] bt=new Button[10];
        Button[] btn=new Button[10];
        protected void onPostExecute(String result)
        {try {
            final String[] separated = result.split(":");
            LinearLayout rootView = new LinearLayout(PendingJobs.this);
            rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.setOrientation(LinearLayout.VERTICAL);
            fl.addView(rootView);
            y=0;
            z=0;
            for (int i = 0; i <= separated.length - 3; i++) {
                LinearLayout rootView2 = new LinearLayout(PendingJobs.this);
                rootView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView2.setOrientation(LinearLayout.VERTICAL);

                TextView b = new TextView(PendingJobs.this);
                stime = separated[i];
                b.setText(stime);
                b.setPadding(50, 30, 0, 0);
                b.setTextColor(Color.BLACK);
                b.setTextSize(28);
                rootView2.addView(b);
                i++;
                TextView b1 = new TextView(PendingJobs.this);
                b1.setPadding(30, 15, 30, 0);
                b1.setTextSize(20);
                String stime1 = separated[i];
                b1.setText(stime1);
                b1.setTextColor(Color.BLACK);
                rootView2.addView(b1);
                i++;
                TextView b2 = new TextView(PendingJobs.this);
                b2.setPadding(30, 15, 0, 0);
                b2.setTextSize(20);
                String stime2 = separated[i];
                b2.setText(stime2);
                b2.setTextColor(Color.BLACK);
                rootView2.addView(b2);

                LinearLayout rootView3 = new LinearLayout(PendingJobs.this);
                rootView3.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView3.setOrientation(LinearLayout.HORIZONTAL);




                btn[y]=new Button(PendingJobs.this);
                btn[y].setPadding(10, 15, 0, 20);
                btn[y].setTextSize(20);
                btn[y].setText("Accept");
                btn[y].setTextColor(Color.BLACK);
                btn[y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Acceptpending ap=new Acceptpending();
                                ap.execute(separated[0],email,separated[2]);
                            }
                        });
                        if(btn[1]!=null)
                            btn[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Acceptpending ap=new Acceptpending();
                                    ap.execute(separated[3],email,separated[5]);;
                                }
                            });
                        if(btn[2]!=null)
                            btn[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Acceptpending ap=new Acceptpending();
                                    ap.execute(separated[6],email,separated[8]);
                                }
                            });
                        if(btn[3]!=null)
                            btn[3].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Acceptpending ap=new Acceptpending();
                                    ap.execute(separated[9],email,separated[11]);
                                }
                            });
                        if(btn[4]!=null)
                            btn[4].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Acceptpending ap=new Acceptpending();
                                    ap.execute(separated[12],email,separated[14]);
                                }
                            });


                    }
                });
                rootView3.addView(btn[y]);

                bt[z]=new Button(PendingJobs.this);
                bt[z].setPadding(10, 15, 0, 20);
                bt[z].setTextSize(20);
                bt[z].setText("Reject");
                bt[z].setTextColor(Color.BLACK);
                bt[z].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bt[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            Rejectpending rp=new Rejectpending();
                            rp.execute(separated[0],email,separated[2]);

                            }
                        });
                        if(bt[1]!=null)
                            bt[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Rejectpending rp=new Rejectpending();
                                    rp.execute(separated[3],email,separated[5]);
                                }
                            });
                        if(bt[2]!=null)
                            bt[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Rejectpending rp=new Rejectpending();
                                    rp.execute(separated[6],email,separated[8]);
                                }
                            });
                        if(bt[3]!=null)
                            bt[3].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Rejectpending rp=new Rejectpending();
                                    rp.execute(separated[9],email,separated[11]);
                                }
                            });

                    }
                });
                rootView3.addView(bt[z]);
                z++;
                y++;
                i++;
                rootView2.addView(rootView3);
                rootView.addView(rootView2);
                final View vline1 = new View(PendingJobs.this);
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

            String connectionString = "http://172.20.10.3/FYPHomeASsitant/PJ2.php";

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
    public class Acceptpending extends AsyncTask<String, Void, String> {

        String WEmail,Job,CName;



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Log.e("log_tag", "Rejected");
                Toast.makeText(PendingJobs.this, "Job Accepted Successfully. You can see it in Upcoming Jobs", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(PendingJobs.this, "Sory .Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            CName = voids[0];
            WEmail = voids[1];
            Job=voids[2];
            Log.e("log_tagaaaaaaaaa", CName +" "+WEmail+" "+Job);
            String connectionString = "http://172.20.10.3/FYPHomeASsitant/AcceptPJ.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("CName", "UTF-8")+"="+URLEncoder.encode(CName, "UTF-8")
                        +"&&"+URLEncoder.encode("WEmail", "UTF-8")+"="+URLEncoder.encode(WEmail, "UTF-8")
                        +"&&"+URLEncoder.encode("Status", "UTF-8")+"="+URLEncoder.encode("Accepted", "UTF-8")
                        +"&&"+URLEncoder.encode("Job", "UTF-8")+"="+URLEncoder.encode(Job, "UTF-8");
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
        }
    } public class Rejectpending extends AsyncTask<String, Void, String> {

        String WEmail,Job,CName;



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Log.e("log_tag", "Rejected");
                Toast.makeText(PendingJobs.this, "Job Rejected Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(PendingJobs.this, "Sory .Some Thing Went Wrong", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            CName = voids[0];
            WEmail = voids[1];
            Job=voids[2];
            Log.e("log_tagaaaaaaaaa", CName +" "+WEmail+" "+Job);
            String connectionString = "http://172.20.10.3/FYPHomeASsitant/RejectPJ.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("CName", "UTF-8")+"="+URLEncoder.encode(CName, "UTF-8")
                        +"&&"+URLEncoder.encode("WEmail", "UTF-8")+"="+URLEncoder.encode(WEmail, "UTF-8")
                        +"&&"+URLEncoder.encode("Status", "UTF-8")+"="+URLEncoder.encode("Rejected", "UTF-8")
                        +"&&"+URLEncoder.encode("Job", "UTF-8")+"="+URLEncoder.encode(Job, "UTF-8");
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
        }
    }
}
