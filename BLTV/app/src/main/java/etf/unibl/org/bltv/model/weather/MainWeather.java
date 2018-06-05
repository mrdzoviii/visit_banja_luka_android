package etf.unibl.org.bltv.model.weather;

import com.google.gson.annotations.SerializedName;

public class MainWeather {
    public MainWeather() {
    }
    private String temp;
    private String humidity;
    private String pressure;

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @SerializedName("temp_max")
    private String tempMax;
    @SerializedName("temp_min")
    private String tempMin;

    @Override
    public String toString() {
        return "MainWeather{" +
                "temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                '}';
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

}
