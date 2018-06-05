package etf.unibl.org.bltv.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.task.WeatherTask;

public class CurrentWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.tab_weather);
        new WeatherTask().execute(this);
    }
}
