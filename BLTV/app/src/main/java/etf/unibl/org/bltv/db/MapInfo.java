package etf.unibl.org.bltv.db;

import android.arch.persistence.room.ColumnInfo;

public class MapInfo {
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="latitude")
    private float latitude;
    @ColumnInfo(name="longitude")
    private float longitude;

    public MapInfo() {
    }

    public MapInfo(String title, float latitude, float longitude) {

        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
