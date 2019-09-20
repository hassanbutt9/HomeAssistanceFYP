package com.edu.homeassistancefyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_interface);
    }
    public  void languageChangeButton(View v){
        new Language(this).ShowChangeLanguage();
    }
    public void signUpButton(View v){
        Intent signup = new Intent(this,SignUpInterface.class);
        startActivity(signup);
    }
    public void Login(View v){

        EditText emailEditText = (EditText)findViewById(R.id.enterEmail);
        EditText passEditText =(EditText)findViewById(R.id.enterPassword);
        String id = emailEditText.getText().toString();


        String pass = passEditText.getText().toString();

        if(id.equals("customer@gmail.com") && pass.equals("123"))
        {
            Intent customer = new Intent(this,CustomerActivity.class);
            startActivity(customer);
        }
        else if (id.equals("admin@admin.com")&& pass.equals("123")){
            Intent admin = new Intent(this,AdminIndex.class);
            startActivity(admin);
        }
        else if(id.equals("worker@worker.com")&&pass.equals("123")){
            Intent work = new Intent(this,new WorkerActivity().getClass());
            startActivity(work);
        }
        else{
            Toast.makeText(this, "Wrong pw", Toast.LENGTH_SHORT).show();
        }


    }
}
