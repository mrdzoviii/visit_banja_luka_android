package etf.unibl.org.bltv.model.weather;

import java.util.List;

public class CurrentWeather {
    private MainWeather main;
    private List<Weather> weather;
    private Wind wind;

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "main=" + main +
                ", weather=" + weather +
                ", wind=" + wind +
                '}';
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

    public CurrentWeather() {
    }

    public MainWeather getMain() {
        return main;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
