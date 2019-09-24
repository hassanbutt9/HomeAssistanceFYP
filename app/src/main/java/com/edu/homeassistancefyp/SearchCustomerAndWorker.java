package com.edu.homeassistancefyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
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

public class SearchCustomerAndWorker extends AsyncTask<String, Void, String> {
    //AlertDialog alertDialog;
    Context context;
    String user, pass;
    String checkcustomerOrWorker;

    public SearchCustomerAndWorker(Context context){
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(String message) {
        Customer cus = new Customer();


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
        String connectionString = "http://192.168.174.2/FYPHomeASsitant/login.php";

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
}
