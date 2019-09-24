package com.edu.homeassistancefyp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUpInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_interface);
    }
    public  void languageChangeButton(View v){
        new Language(this).ShowChangeLanguage();
    }


    public void signupClicked(View view){
        EditText emailEditText = (EditText)findViewById(R.id.email);
        EditText passEditText =(EditText)findViewById(R.id.password);
        EditText nameEdit = (EditText)findViewById(R.id.name);
        EditText phoneNo = (EditText)findViewById(R.id.phoneno);
        String id = emailEditText.getText().toString();
        String pass = passEditText.getText().toString();
        String name = nameEdit.getText().toString();
        String phoneNoValue = phoneNo.getText().toString();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioSignupCheck);
        int Rid=radioGroup.getCheckedRadioButtonId();
        RadioButton rb=(RadioButton) findViewById(Rid);
        String cOrW = (String) rb.getText();






        SignuConnection LC = new SignuConnection(this);
        LC.execute(id, pass,name,phoneNoValue,cOrW);

    }
}
