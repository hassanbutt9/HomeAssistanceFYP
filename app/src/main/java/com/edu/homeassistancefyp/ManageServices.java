package com.edu.homeassistancefyp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ManageServices extends AppCompatActivity {
    CheckBox []cb=new CheckBox[12];
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_services);
        update=(Button) findViewById(R.id.update);
        update.setEnabled(false);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cb[0]=(CheckBox) findViewById(R.id.checkBox1);
        cb[0].setOnClickListener(mListener);

        cb[1]=(CheckBox) findViewById(R.id.checkBox2);
        cb[1].setOnClickListener(mListener);

        cb[2]=(CheckBox) findViewById(R.id.checkBox3);
        cb[2].setOnClickListener(mListener);

        cb[3]=(CheckBox) findViewById(R.id.checkBox4);
        cb[3].setOnClickListener(mListener);

        cb[4]=(CheckBox) findViewById(R.id.checkBox5);
        cb[4].setOnClickListener(mListener);

        cb[5]=(CheckBox) findViewById(R.id.checkBox6);
        cb[5].setOnClickListener(mListener);

        cb[6]=(CheckBox) findViewById(R.id.checkBox7);
        cb[6].setOnClickListener(mListener);

        cb[7]=(CheckBox) findViewById(R.id.checkBox8);
        cb[7].setOnClickListener(mListener);

        cb[8]=(CheckBox) findViewById(R.id.checkBox9);
        cb[8].setOnClickListener(mListener);

        cb[9]=(CheckBox) findViewById(R.id.checkBox10);
        cb[9].setOnClickListener(mListener);

        cb[10]=(CheckBox) findViewById(R.id.checkBox11);
        cb[10].setOnClickListener(mListener);

        cb[11]=(CheckBox) findViewById(R.id.checkBox12);
        cb[11].setOnClickListener(mListener);

    }
    View.OnClickListener mListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            for (int i = 0; i < 12; i++)
            {
                if(cb[i].isChecked())
                {
                    update.setEnabled(true);
                    for (int z=0;z<12;z++)
                    {
                        if(z==i)
                        {
                        }
                        else if(cb[z].isChecked())
                        {
                            for(int c=0;c<12;c++)
                            {
                                if(i==c || z==c)
                                {
                                    if(c==11)
                                        return;
                                }
                                else
                                {
                                    cb[c].setEnabled(false);
                                    if(c==11)
                                        return;
                                }
                            }
                        }
                        else
                        {
                            cb[z].setEnabled(true);
                            if(z==11)
                                return;
                        }
                    }
                }
                else
                    update.setEnabled(false);
            }

        }
    };

}
