package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminIndex extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);
    }
    public  void languageChangeButton(View v){
        new Language(this).ShowChangeLanguage();
    }

}
