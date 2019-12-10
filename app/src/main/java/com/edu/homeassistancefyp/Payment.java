package com.edu.homeassistancefyp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment extends AppCompatActivity {
    private static final String TAG = "paymentExample";
    public static final String PAYPAL_KEY ="Ae94Ph_1PueUDCwaANT3VHpcQWvFnQpMY2XN2tIHlfBnsd5lWY7DzJcNVmFduHWQEydCysDMXGDBYCKK";
    private static final int REQUEST_CODE_PAYMENT =1;
    private static final int REQUEST_FUTURE_PAYMENT=2;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static PayPalConfiguration config;
    PayPalPayment thingstoBuy;
String name,email,user,PID;
  String payed;
    String amount;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE_PAYMENT){
            if(resultCode== Activity.RESULT_OK){
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirm!=null){
                    try{
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));
                        Log.i("work","working");
                        Toast.makeText(Payment.this, "payment sucessfull", Toast.LENGTH_SHORT).show();
                        Paid p=new Paid();
                        p.execute(PID);
                    }
                    catch (JSONException e){
                        Toast.makeText(Payment.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else if(resultCode== Activity.RESULT_CANCELED)
                    Toast.makeText(Payment.this, "payment has been cancelled", Toast.LENGTH_SHORT).show();
                else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
                    Toast.makeText(Payment.this, "error occurred", Toast.LENGTH_SHORT).show();
                else if(requestCode==REQUEST_FUTURE_PAYMENT){
                    if(resultCode==Activity.RESULT_OK){
                        PayPalAuthorization authorization = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                        if(authorization!=null){
                            try{
                                Log.i("FuturePayment",authorization.toJSONObject().toString(4));
                                String authorization_code = authorization.getAuthorizationCode();
                                Log.i("futur payment ",authorization_code);
                            }
                            catch (Exception e){

                            }
                        }
                    }
                }
                else
                    Log.i("fail","fail");
            }

        }
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
                String h1=separated[i];
                String m1=separated[i+1];
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
                String h2=separated[i];
                String m2=separated[i+1];
                String stime2 = separated[i]+":"+separated[i+1];
                b2.setText("End Time: "+stime2);
                b2.setTextColor(Color.BLACK);
                rootView2.addView(b2);



                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
                Date date1 = simpleDateFormat.parse(stime1);
                Date date2 = simpleDateFormat.parse(stime2);

                long difference = date2.getTime() - date1.getTime();
                int days = (int) (difference / (1000*60*60*24));
                int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
                int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);
                hours = (hours < 0 ? -hours : hours);
                Log.i("======= Hours"," :: "+hours);


                i++;

                i++;

                final View vline2 = new View(Payment.this);
                vline2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1));
                vline2.setBackgroundColor(Color.RED);
                rootView2.addView(vline2);
                int hourd=Integer.valueOf(h2)-Integer.valueOf(h1);
                int mind=Integer.valueOf(m2.substring(0,2))-Integer.valueOf(m1.substring(0,2));

                TextView b4 = new TextView(Payment.this);
                b4.setPadding(30, 30, 30, 0);
                b4.setTextSize(25);

                b4.setText("Estimated Time : 0"+hours+" Hours "+String.valueOf(mind)+ " Minutes");
                b4.setTextColor(Color.BLACK);
                rootView2.addView(b4);



                TextView b6 = new TextView(Payment.this);
                b6.setPadding(30, 15, 30, 0);
                b6.setTextSize(25);
                int pay;
                    if(hours<01)
                    {
                        pay=Integer.valueOf(stime5);
                    }
                    else
                        pay=hours*Integer.valueOf(stime5);

                b6.setText("Total Payment: "+pay);
                amount=String.valueOf(pay);
                b6.setTextColor(Color.BLACK);
                rootView2.addView(b6);





                if(user.equals("customer"))
                {
                    Button paypal=new Button(Payment.this);
                    paypal.setPadding(30, 30, 30, 30);
                    paypal.setTextSize(25);
                    paypal.setText("Pay By PayPAL");
                    paypal.setTextColor(Color.BLACK);
                    rootView2.addView(paypal);


                    paypal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payed="Paid By PayPal";
                            configPaypal();

                            MakePayment();


                        }
                    });
                    Button cash=new Button(Payment.this);
                    cash.setPadding(30, 30, 30, 30);
                    cash.setTextSize(25);
                    cash.setText("Pay in Cash");
                    cash.setTextColor(Color.BLACK);
                    rootView2.addView(cash);
                    cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            payed="Paid By Cash";
                            Paid p=new Paid();
                            p.execute(PID);

                        }
                    });
                }


                rootView.addView(rootView2);


            }
        } catch (Exception e) {
            Log.e("log_tag", "Error parsing data reviews data" + e.toString());

        }}

        private void MakePayment(){
            Intent intent = new Intent(Payment.this, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
            startService(intent);
            thingstoBuy = new PayPalPayment(new BigDecimal(amount),"USD","payment",PayPalPayment.PAYMENT_INTENT_SALE);
            Intent payment =new Intent(Payment.this, PaymentActivity.class);
            payment.putExtra(PaymentActivity.EXTRA_PAYMENT,thingstoBuy);
            payment.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
            startActivityForResult(payment,REQUEST_CODE_PAYMENT);

        }
        private void configPaypal(){
            config = new PayPalConfiguration()
                    .environment(CONFIG_ENVIRONMENT)
                    .clientId(PAYPAL_KEY)
                    .merchantName("PAYPAL LOGIN");

        }

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
    public class Paid extends AsyncTask<String, Void, String> {

        String WEmail,Job,CName;



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Transaction t=new Transaction();
                t.execute(PID);


            }
            else
                Toast.makeText(Payment.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";


            String connectionString = "http://192.168.10.6/FYPHomeASsitant/Paid.php";

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
                String data = URLEncoder.encode("PID", "UTF-8")+"="+URLEncoder.encode(PID, "UTF-8");
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

        String WEmail,Job,CName;



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String message) {

            if(message.equals("insert successfully")){

                Log.e("log_tag", "success");
                Toast.makeText(Payment.this, "Thank You. Please Give Reviews..", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Payment.this,new GiveReview().getClass());
                i.putExtra("email",email);
                i.putExtra("name",name);
                i.putExtra("user",user);
                startActivity(i);
                finish();


            }
            else
                Toast.makeText(Payment.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... voids) {
            String result = "";


            String connectionString = "http://192.168.10.6/FYPHomeASsitant/Transaction.php";

            try {

                URL url = new URL(connectionString);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("PID", "UTF-8")+"="+URLEncoder.encode(PID, "UTF-8")
                        + "&&" + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8")
                        + "&&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(payed, "UTF-8");
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
