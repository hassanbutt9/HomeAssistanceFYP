package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import java.io.FileReader;

public class timer extends AppCompatActivity {
String name,email,user;
FrameLayout fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name=null;
                email=null;
                user=null;
            } else {
                email =extras.getString("email");
                name = extras.getString("name");
                user = extras.getString("user");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
            name= (String) savedInstanceState.getSerializable("name");
            user= (String) savedInstanceState.getSerializable("user");
        }
        fl=(FrameLayout) findViewById(R.id.frameLayout5);

    }
    
}
