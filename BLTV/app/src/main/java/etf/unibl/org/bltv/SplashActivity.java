package etf.unibl.org.bltv;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
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
        final Activity activity=this;
        AsyncTask<Void,Void,Void> task=new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.favItems.clear();
                MainActivity.favItems.addAll(AppDatabase.getAppDatabase(activity).itemDao().getAllLiked());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if(AppController.mainActivity!=null){
                    AppController.mainActivity.finish();
                }
                Intent intent=new Intent(activity,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        task.execute(null,null);


    }



}
