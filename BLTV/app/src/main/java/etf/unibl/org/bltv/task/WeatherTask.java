package etf.unibl.org.bltv.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.model.weather.CurrentWeather;
import etf.unibl.org.bltv.util.GlideApp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherTask extends AsyncTask<Activity,Void,CurrentWeather> {
    private final String TAG=WeatherTask.class.getSimpleName();
    private Activity context;
    private final Integer kelvin=273;
    @Override
    protected CurrentWeather doInBackground(Activity... contexts) {
        context = contexts[0];
        Log.e(TAG, "start aynctask to get coupons");
        return getWeatherFromServer();
    }

    @Override
    protected void onPostExecute(CurrentWeather currentWeather) {
        super.onPostExecute(currentWeather);
        if(currentWeather!=null){
            ImageView icon=context.findViewById(R.id.icon);
            GlideApp.with(AppController.cacheContext).load(context.getString(R.string.pull_icon)+currentWeather.getWeather().get(0).getIcon()+".png")
                    .into(icon);
            TextView temp=context.findViewById(R.id.current_temp);
            temp.setText(getCelsius(currentWeather.getMain().getTemp())+context.getString(R.string.celsius));
            TextView humidity=context.findViewById(R.id.humidity);
            humidity.setText(currentWeather.getMain().getHumidity()+"%");
            TextView windSpeed=context.findViewById(R.id.windspeed);
            windSpeed.setText(currentWeather.getWind().getSpeed()+" m/s");
            TextView pressure=context.findViewById(R.id.pressure);
            pressure.setText(currentWeather.getMain().getPressure()+ " Mbar");
        }
    }
    private Integer getCelsius(String temp){
        Double cTemp= Double.valueOf(temp);
        Integer currtemp= (int)(Math.round(cTemp - kelvin));
        return  currtemp;
    }
    public CurrentWeather convertJsonToObject(String response){
        //instantiate Gson
        final Gson gson = new Gson();

        //pass root element type to fromJson method along with input stream
        CurrentWeather weather= gson.fromJson(response, CurrentWeather.class);

        //Log.e(TAG, "number of coupons from json response after gson parsing"+cpnlst.size());

        return weather;
    }
    public CurrentWeather getWeatherFromServer() {
        String serviceUrl = context.getString(R.string.pull_weather);
        URL url = null;
        try {
            Log.d(TAG, "call rest service to get json response");
            /*url = new URL(serviceUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setConnectTimeout(4000);
            connection.setReadTimeout(4000);
            connection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response=new StringBuffer();
            String line="";
            while((line=bufferedReader.readLine())!=null){
                response.append(line);
            }
            bufferedReader.close();
            System.out.println(response.toString());
            //pass buffered reader to convert json to java object using gson
            return convertJsonToObject(response.toString());*/
            OkHttpClient client=new OkHttpClient();
            Request request = new Request.Builder()
                    .url(context.getString(R.string.pull_weather))
                    .build();
            Response response = client.newCall(request).execute();
            Gson gson=new Gson();
            return (gson.fromJson(response.body().string(),CurrentWeather.class));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error in getting and parsing response");
        }
        return null;
    }
}
