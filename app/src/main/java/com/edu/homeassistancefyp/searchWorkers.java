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
    String Custname;
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
                Custname=null;
            } else {
                email =extras.getString("email");
                categorie = extras.getString("categorie");
                location=extras.getString("location");
                Custname=extras.getString("name");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
            categorie= (String) savedInstanceState.getSerializable("categorie");
            location=(String) savedInstanceState.getSerializable("location");
            Custname=(String) savedInstanceState.getSerializable("name");
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
        String stime="";
        int z=0;
        int y=0;
        Button[] bt=new Button[10];
        Button[] btn=new Button[10];
        protected void onPostExecute(String result)
        {try {
            final String[] separated = result.split(":");
            LinearLayout rootView = new LinearLayout(searchWorkers.this);
            rootView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            rootView.setOrientation(LinearLayout.VERTICAL);
            fl.addView(rootView);
            y=0;
            z=0;
            for (int i = 0; i <= separated.length - 4; i++) {
                LinearLayout rootView2 = new LinearLayout(searchWorkers.this);
                rootView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                rootView2.setOrientation(LinearLayout.VERTICAL);

                TextView b = new TextView(searchWorkers.this);
                 stime = separated[i];
                b.setText(stime);
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




                btn[y]=new Button(searchWorkers.this);
                btn[y].setPadding(10, 15, 0, 20);
                btn[y].setTextSize(20);
                btn[y].setText("BOOK");
                btn[y].setTextColor(Color.BLACK);
                btn[y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn[0].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                        pending p=new pending();
                        p.execute(Custname,email,location,separated[0],separated[3],separated[1],separated[2],categorie);
                            }
                        });
                        if(btn[1]!=null)
                            btn[1].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                        if(btn[2]!=null)
                            btn[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                        if(btn[3]!=null)
                            btn[3].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                        if(btn[4]!=null)
                            btn[4].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });


                    }
                });
                rootView3.addView(btn[y]);

                bt[z]=new Button(searchWorkers.this);
                bt[z].setPadding(10, 15, 0, 20);
                bt[z].setTextSize(20);
                bt[z].setText("PROFILE");
                bt[z].setTextColor(Color.BLACK);
                bt[z].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       bt[0].setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent pa=new Intent(searchWorkers.this,new profileActivity().getClass());
                               pa.putExtra("email",separated[3]);
                               pa.putExtra("name",separated[0]);
                               startActivity(pa);

                           }
                       });
                        if(bt[1]!=null)
                       bt[1].setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent pa=new Intent(searchWorkers.this,new profileActivity().getClass());
                               pa.putExtra("email",separated[7]);
                               pa.putExtra("name",separated[4]);
                               startActivity(pa);
                           }
                       });
                        if(bt[2]!=null)
                            bt[2].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent pa=new Intent(searchWorkers.this,new profileActivity().getClass());
                                    pa.putExtra("email",separated[11]);
                                    pa.putExtra("name",separated[8]);
                                    startActivity(pa);
                                }
                            });
                        if(bt[3]!=null)
                            bt[3].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent pa=new Intent(searchWorkers.this,new profileActivity().getClass());
                                    pa.putExtra("email",separated[15]);
                                    pa.putExtra("name",separated[12]);
                                    startActivity(pa);
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

            String connectionString = "http://192.168.10.6/FYPHomeASsitant/relatedWorker.php";

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

    public class pending extends AsyncTask<String, Void, String> {

        String CName, CEmail, CLocation,WName,WEmail,WLocation,PerHourRate,Job;



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){
                Log.e("log_tag", "SUCCFULLL");
            }
            else
                Log.e("log_tagaaaaaaaaa", message);
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            CName = voids[0];
            CEmail = voids[1];
            CLocation = voids[2];
            WName =voids[3];
             WEmail = voids[4];
             WLocation=voids[5];
             PerHourRate=voids[6];
             Job=voids[7];
            Log.e("log_tagaaaaaaaaa", CName +" "+CEmail+" "+ CLocation+" "+WName+" "+WEmail+" "+WLocation+" "+PerHourRate+" "+Job);
            String connectionString = "http://192.168.10.6/FYPHomeASsitant/pendingJobs.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("CName", "UTF-8")+"="+URLEncoder.encode(CName, "UTF-8")
                        +"&&"+URLEncoder.encode("CEmail", "UTF-8")+"="+URLEncoder.encode(CEmail, "UTF-8")
                        +"&&"+URLEncoder.encode("CLocation", "UTF-8")+"="+URLEncoder.encode(CLocation, "UTF-8")
                        +"&&"+URLEncoder.encode("WName", "UTF-8")+"="+ URLEncoder.encode(WName, "UTF-8")
                        +"&&"+URLEncoder.encode("WEmail", "UTF-8")+"="+URLEncoder.encode(WEmail, "UTF-8")
                        +"&&"+URLEncoder.encode("WLocation", "UTF-8")+"="+URLEncoder.encode(WLocation, "UTF-8")
                        +"&&"+URLEncoder.encode("PerHourRate", "UTF-8")+"="+URLEncoder.encode(PerHourRate, "UTF-8")
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
