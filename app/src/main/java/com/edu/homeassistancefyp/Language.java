package com.edu.homeassistancefyp;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Locale;

public class Language {
    private Context context;
    Language(Context context){
        this.context =context;
    }

    public  void ShowChangeLanguage() {
        final String [] list = {"english","URDU"};
        final AlertDialog.Builder mBuilder  = new AlertDialog.Builder(context);
        mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){

                    setLocate("en");
                    dialog.dismiss();
                    recreateApp((AppCompatActivity) context);

                }
                else{
                    setLocate("ur");
                    dialog.dismiss();
                    recreateApp((AppCompatActivity) context);

                }

            }
        });
        AlertDialog d = mBuilder.create();
        d.show();
    }
    public void setLocate(String language){

        
        Locale locale = new Locale(language.toLowerCase());
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        SharedPreferences.Editor editor = context.getSharedPreferences("setting",Context.MODE_PRIVATE).edit();
        editor.putString("my_lang",language);
        editor.apply();
    }

    public static void recreateApp(AppCompatActivity app){
        app.recreate();
    }
}
