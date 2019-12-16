package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class AdminIndex extends AppCompatActivity {
TextView cus,wor,ear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);
        cus=(TextView) findViewById(R.id.cus);
        wor=(TextView) findViewById(R.id.wor);
        ear=(TextView) findViewById(R.id.ear);



db d =new db();
d.execute("abc");

    }
    public  void languageChangeButton(View v){
        new Language(this).ShowChangeLanguage();
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

                cus.setText(separated[0]);
                wor.setText(separated[1]);
                ear.setText(separated[2]);

        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}
        @Override
        protected String doInBackground(String... strings) {
            loc=strings[0];

            String connectionString = "http://172.20.10.3/FYPHomeASsitant/admin.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode("admin", "UTF-8");
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
