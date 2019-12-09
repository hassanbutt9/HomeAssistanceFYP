package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class ManageServices extends AppCompatActivity {
    CheckBox []cb=new CheckBox[12];
    int flag;
    String a;
    String b;
    String name,email;
    Button update;
    String arate,brate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email=null;
                name=null;
                // location=null;
            } else {
                email =extras.getString("email");
                name = extras.getString("name");
                //location= extras.getString("location");

            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
            name= (String) savedInstanceState.getSerializable("name");
            //location= (String) savedInstanceState.getSerializable("location");
        }
        update=(Button) findViewById(R.id.update);
        update.setEnabled(false);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            int i=0;
                for(i=0;i<12;i++)
                {
                    if(cb[i].isChecked())
                    {
                        switch (i){
                            case 0:
                                a="Car Wash";
                                break;
                            case 1:
                                a="Beauty";
                                break;
                            case 2:
                                a="Home Cleaning";
                                break;
                            case 3:
                                a="Doctor";
                                break;
                            case 4:
                                a="Tutor";
                                break;
                            case 5:
                                a="Plumber";
                                break;
                            case 6:
                                a="Electrician";
                                break;
                            case 7:
                                a="Massage";
                                break;
                            case 8:
                                a="Fitness";
                                break;
                            case 9:
                                a="Maid";
                                break;
                            case 10:
                                a="Carpenter";
                                break;
                            case 11:
                                a="Teacher";
                                break;

                        }
                        break;
                    }
                }

                for(int z=0;z<12;z++)
                {
                    if(z==i)
                    {

                    }

                    else if(cb[z].isChecked())
                    {
                        switch (z){
                            case 0:
                                b="Car Wash";
                                break;
                            case 1:
                                b="Beauty";
                                break;
                            case 2:
                                b="Home Cleaning";
                                break;
                            case 3:
                                b="Doctor";
                                break;
                            case 4:
                                b="Tutor";
                                break;
                            case 5:
                                b="Plumber";
                                break;
                            case 6:
                                b="Electrician";
                                break;
                            case 7:
                                b="Massage";
                                break;
                            case 8:
                                b="Fitness";
                                break;
                            case 9:
                                b="Maid";
                                break;
                            case 10:
                                b="Carpenter";
                                break;
                            case 11:
                                b="Teacher";
                                break;

                        }
                        break;
                    }
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(ManageServices.this);
                builder.setTitle("Enter Per Hour Rate For "+a);
                final EditText input = new EditText(ManageServices.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arate = input.getText().toString();


                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ManageServices.this);
                        builder2.setTitle("Enter Per Hour Rate For "+b);
                        final EditText input = new EditText(ManageServices.this);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER);
                        builder2.setView(input);
                        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                brate = input.getText().toString();
                                Toast.makeText(ManageServices.this, a+"   "+b+"  "+arate + "  "+ brate, Toast.LENGTH_SHORT).show();
                                addJob aj=new addJob();
                                aj.execute(email);
                            }
                        });
                        builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder2.show();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        cb[0]=(CheckBox) findViewById(R.id.checkBox1);
        cb[0].setOnClickListener(mListener);

        cb[1]=(CheckBox) findViewById(R.id.checkBox2);
        cb[1].setOnClickListener(mListener);

        cb[2]=(CheckBox) findViewById(R.id.checkBox3);
        cb[2].setOnClickListener(mListener);

        cb[3]=(CheckBox) findViewById(R.id.checkBox4);
        cb[3].setOnClickListener(mListener);

        cb[4]=(CheckBox) findViewById(R.id.checkBox5);
        cb[4].setOnClickListener(mListener);

        cb[5]=(CheckBox) findViewById(R.id.checkBox6);
        cb[5].setOnClickListener(mListener);

        cb[6]=(CheckBox) findViewById(R.id.checkBox7);
        cb[6].setOnClickListener(mListener);

        cb[7]=(CheckBox) findViewById(R.id.checkBox8);
        cb[7].setOnClickListener(mListener);

        cb[8]=(CheckBox) findViewById(R.id.checkBox9);
        cb[8].setOnClickListener(mListener);

        cb[9]=(CheckBox) findViewById(R.id.checkBox10);
        cb[9].setOnClickListener(mListener);

        cb[10]=(CheckBox) findViewById(R.id.checkBox11);
        cb[10].setOnClickListener(mListener);

        cb[11]=(CheckBox) findViewById(R.id.checkBox12);
        cb[11].setOnClickListener(mListener);

    }
    View.OnClickListener mListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            for (int i = 0; i < 12; i++)
            {
                if(cb[i].isChecked())
                {

                    for (int z=0;z<12;z++)
                    {
                        if(z==i)
                        {
                        }
                        else if(cb[z].isChecked())
                        {
                            update.setEnabled(true);
                            for(int c=0;c<12;c++)
                            {
                                if(i==c || z==c)
                                {
                                    if(c==11)
                                        return;
                                }
                                else
                                {
                                    cb[c].setEnabled(false);
                                    if(c==11)
                                        return;
                                }
                            }
                        }
                        else
                        {
                            cb[z].setEnabled(true);
                            update.setEnabled(false);
                            if(z==11)
                                return;
                        }
                    }
                }
                else
                    update.setEnabled(false);
            }

        }
    };


    public class addJob extends AsyncTask<String, Void, String> {




        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if (message.equals("insert successfully")) {
                Log.e("log_tag", "SUCCFULLL");
                Toast.makeText(ManageServices.this, "Job Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Log.e("log_tagaaaaaaaaa", message);
            Toast.makeText(ManageServices.this, "Job Added Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";


            String connectionString = "http://192.168.10.6/FYPHomeASsitant/manageService.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")
                        + "&&" + URLEncoder.encode("joba", "UTF-8") + "=" + URLEncoder.encode(a, "UTF-8")
                        + "&&" + URLEncoder.encode("jobb", "UTF-8") + "=" + URLEncoder.encode(b, "UTF-8")
                        + "&&" + URLEncoder.encode("jobaRate", "UTF-8") + "=" + URLEncoder.encode(arate, "UTF-8")
                        + "&&" + URLEncoder.encode("jobbRate", "UTF-8") + "=" + URLEncoder.encode(brate, "UTF-8");
                Log.e("log_tagaaaaaaaaa", data);

                writer.write(data);
                writer.flush();
                writer.close();
                ops.close();

                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null) {
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
