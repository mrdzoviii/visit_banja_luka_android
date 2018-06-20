package etf.unibl.org.bltv.task;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.MainActivity;
import etf.unibl.org.bltv.db.AppDatabase;

import static android.content.Context.MODE_PRIVATE;

public class SplashTask extends AsyncTask<Activity,Void,Void> {
    private Activity activity;
    @Override
    protected Void doInBackground(Activity... voids) {
        this.activity=voids[0];
        MainActivity.favItems.clear();
        MainActivity.favItems.addAll(AppDatabase.getAppDatabase(activity).itemDao().getAllLiked());
        SharedPreferences pref=activity.getSharedPreferences("org.unibl.etf.bltv.db",MODE_PRIVATE);
        if(pref.getBoolean("images_not_downloaded",true)){

            if(AppDatabase.getAppDatabase(activity).downloadImages()){
                pref.edit().putBoolean("image_not_downloaded",false).apply();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(AppController.mainActivity!=null){
            AppController.mainActivity.finish();
        }
        Intent intent=new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
       activity.finish();
    }
}
