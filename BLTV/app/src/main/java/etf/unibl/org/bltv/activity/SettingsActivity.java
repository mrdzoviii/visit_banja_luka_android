package etf.unibl.org.bltv.activity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.SplashActivity;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

        String lang=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("language","sr");
        if("sr".equals(lang)) {
            Locale locale = new Locale("sr");
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }else{
            Locale locale=new Locale("en");
            Configuration config = getBaseContext().getResources().getConfiguration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        PreferenceManager.getDefaultSharedPreferences(AppController.cacheContext).registerOnSharedPreferenceChangeListener(this);
        setTitle(getString(R.string.action_settings));
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreferenceManager.getDefaultSharedPreferences(AppController.cacheContext).unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("language")) {
            String lang=sharedPreferences.getString("language","sr");
            System.out.println("LANG:"+lang);
            if("sr".equals(lang)) {
                Locale locale = new Locale("sr");
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            }else{
                Locale locale=new Locale("en");
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            }
            restartActivity();
        }
    }


    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
    private void restartActivity() {
        /*Intent intent = new Intent(this,SplashActivity.class);
        finish();
        startActivity(intent);*/
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(i);
    }
}
