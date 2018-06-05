package etf.unibl.org.bltv.model.weather;

public class Wind {
    private String speed;

    public Wind() {
    }

    public String getSpeed() {

        return speed;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed='" + speed + '\'' +
                '}';
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
