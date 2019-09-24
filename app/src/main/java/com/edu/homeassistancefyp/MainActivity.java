package com.edu.homeassistancefyp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocate();
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this,LoginInterface.class);
        startActivity(i);
    }

    public void loadLocate(){
        SharedPreferences pref = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        String language =pref.getString("my_lang","");
        new Language(this).setLocate(language);
    }

}
