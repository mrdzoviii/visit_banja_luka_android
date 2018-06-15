package etf.unibl.org.bltv;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.task.NewsTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences pref=getSharedPreferences("org.unibl.etf.bltv.db",MODE_PRIVATE);
        if(pref.getBoolean("first_run",true)){
            System.out.println("DATABASE_CLEAR");
            pref.edit().putBoolean("first_run",false).apply();
            AppDatabase.firstRun=true;

        }
        final Activity activity=this;
        new Thread(() -> {
            MainActivity.favItems.clear();
            MainActivity.favItems.addAll(AppDatabase.getAppDatabase(activity).itemDao().getAllLiked());
        }).start();
        if(AppController.mainActivity!=null){
            AppController.mainActivity.finish();
        }
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}
