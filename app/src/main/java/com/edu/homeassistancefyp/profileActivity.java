package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class profileActivity extends AppCompatActivity implements workersServices.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String newString;
        String WorkerName;
       // String location;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString=null;
                WorkerName=null;
               // location=null;
            } else {
                newString =extras.getString("email");
                WorkerName = extras.getString("name");
                //location= extras.getString("location");

            }
        } else {
            newString= (String) savedInstanceState.getSerializable("email");
            WorkerName= (String) savedInstanceState.getSerializable("name");
            //location= (String) savedInstanceState.getSerializable("location");
        }
        TextView loc=(TextView) findViewById(R.id.loc);
        //loc.setText(location);
        TextView name=(TextView) findViewById(R.id.textView8);
        name.setText(WorkerName);


        Bundle bundle = new Bundle();
        bundle.putString("ARG_PARAM1", newString);
        bundle.putString("ARG_PARAM2",WorkerName);
// set MyFragment Arguments
       workersServices  ws = new workersServices();
        ws.setArguments(bundle);
        if(savedInstanceState==null) {
           getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,ws).commit();
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
