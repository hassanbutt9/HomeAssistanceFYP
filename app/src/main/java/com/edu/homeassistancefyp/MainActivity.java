package com.edu.homeassistancefyp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocate();
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this,AdminIndex.class);
        startActivity(i);
    }

    public void loadLocate(){
        SharedPreferences pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String language =pref.getString("my_lang","");
        new Language(this).setLocate(language);
    }

}
