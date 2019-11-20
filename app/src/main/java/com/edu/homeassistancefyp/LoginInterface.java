package com.edu.homeassistancefyp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginInterface extends AppCompatActivity {
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
        if(emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),"enter email address",Toast.LENGTH_SHORT).show();
        }else {
            if (emailEditText.getText().toString().trim().matches(emailPattern)) {
                EditText passEditText =(EditText)findViewById(R.id.enterPassword);
                String id = emailEditText.getText().toString();
                String pass = passEditText.getText().toString();

                if (id.equals("admin@admin.com")&& pass.equals("123")){
                    Intent admin = new Intent(this,AdminIndex.class);
                    startActivity(admin);
                }
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioCheck);
                int Rid=radioGroup.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton) findViewById(Rid);
                String cOrW = (String) rb.getText();

                LoginConnection LC = new LoginConnection(this);
                LC.execute(id, pass,cOrW);
            } else {
                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
            }
        }











    }
}
