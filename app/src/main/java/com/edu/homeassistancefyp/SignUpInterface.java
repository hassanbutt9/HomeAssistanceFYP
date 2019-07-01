package com.edu.homeassistancefyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_interface);
    }
    public  void languageChangeButton(View v){
        new Language(this).ShowChangeLanguage();
    }
}
