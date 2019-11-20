package com.edu.homeassistancefyp;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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

public class LoginConnection extends AsyncTask<String, Void, String> {
    //AlertDialog alertDialog;
    Context context;
    String user, pass,phoneNo,name;
    String checkcustomerOrWorker;

    public LoginConnection(Context context){
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        //alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Error");
        //Toast.makeText(getApplicationContext(), context, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String message) {
        Log.i("checking ",message);
        if (message.equals("login successfull")) {
                SearchData s = new SearchData();
                s.execute(user);
        }
        else{
            Log.i("check","fail");
            Toast.makeText(context, "Login Failed..Wrong email or passwrord ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        user = voids[0];
        pass = voids[1];

        String c =voids[2];

        if(c.equals("customers")){
            checkcustomerOrWorker = "customers";
        }
        else{
            checkcustomerOrWorker="workers";
        }
    Log.i("worker/customer",checkcustomerOrWorker);
        String connectionString = "http://192.168.10.15/FYPHomeASsitant/login.php";

        try {
            URL url = new URL(connectionString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user, "UTF-8")
                    +"&&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(pass, "UTF-8")+"&&"+URLEncoder.encode("emails", "UTF-8")+"="+URLEncoder.encode(checkcustomerOrWorker, "UTF-8");
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
    public class SearchData extends AsyncTask<String, Void, String> {

    String CusName;

    public String getCusName(){
        return CusName;
    }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {
            Log.i("checkT",message);
            if(checkcustomerOrWorker.equals("workers")){
                String[] separated = message.split(":");
                Toast.makeText(context, separated[0], Toast.LENGTH_SHORT).show();
                CusName= separated[0];
                phoneNo=separated[1];
                Intent i = new Intent(context,WorkerActivity.class);
                i.putExtra("email",user);
                i.putExtra("name",CusName);
                i.putExtra("phoneNO",phoneNo);
                context.startActivity(i);

            }
            else if(checkcustomerOrWorker.equals("customers")){
            String[] separated = message.split(":");
            Toast.makeText(context, separated[0], Toast.LENGTH_SHORT).show();
            CusName= separated[0];
            phoneNo=separated[1];
            Intent i = new Intent(context,CustomerActivity.class);
            i.putExtra("email",user);
            i.putExtra("name",CusName);
            i.putExtra("phoneNO",phoneNo);
            context.startActivity(i);}

        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";
            String user2 = voids[0];

            String connectionString = "http://192.168.10.15/FYPHomeASsitant/search.php";

            try {
                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user2, "UTF-8")
                        +"&&"+URLEncoder.encode("emails", "UTF-8")+"="+URLEncoder.encode(checkcustomerOrWorker, "UTF-8");
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

