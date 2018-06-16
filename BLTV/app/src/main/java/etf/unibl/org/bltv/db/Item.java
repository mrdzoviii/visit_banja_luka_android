package etf.unibl.org.bltv.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "item")
public class Item implements Serializable{
    public static final String TABLE_NAME="item";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_IMAGE="image";
    public static final String COLUMN_DESCRIPTION="description";
    public static final String COLUMN_IMAGE_URL="image_url";
    public static final String COLUMN_IS_LIKED="is_liked";
    public static final String COLUMN_CATEGORY="category";
    public static final String COLUMN_HOTEL_RATE="hotel_rate";
    public static final String COLUMN_LONGITUDE="longitude";
    public static final String COLUMN_LATITUDE="latitude";
    public static final String COLUMN_PATH="path";
    public static final Integer HOTEL=3;
    public static final Integer INSTITUTION=1;
    public static final Integer MONUMENT=2;
    public static final Integer EVENT=4;


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=COLUMN_ID)
    private Integer id;
    @ColumnInfo(name=COLUMN_TITLE)
    private String title;
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT,name=COLUMN_DESCRIPTION)
    private String description;
    @ColumnInfo(name=COLUMN_IMAGE_URL)
    private String url;
    @ColumnInfo(typeAffinity = ColumnInfo.REAL,name=COLUMN_LATITUDE)
    private float latitude;
    @ColumnInfo(typeAffinity = ColumnInfo.REAL,name=COLUMN_LONGITUDE)
    private float longitude;
    @ColumnInfo(name=COLUMN_IS_LIKED)
    private boolean isLiked;
    @ColumnInfo(name=COLUMN_CATEGORY)
    private int category;
    @ColumnInfo(name=COLUMN_HOTEL_RATE)
    private int hotelRate;
    @ColumnInfo(name=COLUMN_PATH)
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Item fromContentValues(ContentValues values) {
        final Item item = new Item();
        if (values.containsKey(COLUMN_ID)) {
            item.setId(values.getAsInteger(COLUMN_ID));
        }
        if (values.containsKey(COLUMN_TITLE)) {
            item.setTitle(values.getAsString(COLUMN_TITLE));
        }
        if (values.containsKey(COLUMN_DESCRIPTION)) {
            item.setDescription(values.getAsString(COLUMN_DESCRIPTION));
        }
        if (values.containsKey(COLUMN_IMAGE_URL)) {
            item.setUrl(values.getAsString(COLUMN_IMAGE_URL));
        }
        if (values.containsKey(COLUMN_CATEGORY)) {
            item.setCategory(values.getAsInteger(COLUMN_CATEGORY));
        }
        if (values.containsKey(COLUMN_LONGITUDE)) {
            item.setLongitude(values.getAsFloat(COLUMN_LONGITUDE));
        }
        if (values.containsKey(COLUMN_LATITUDE)) {
            item.setLatitude(values.getAsFloat(COLUMN_LATITUDE));
        }
        if (values.containsKey(COLUMN_HOTEL_RATE)) {
            item.setHotelRate(values.getAsInteger(COLUMN_HOTEL_RATE));
        }
        if (values.containsKey(COLUMN_IS_LIKED)) {
            item.setLiked(values.getAsBoolean(COLUMN_IS_LIKED));
        }
        return item;
    }
    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getHotelRate() {
        return hotelRate;
    }

    public void setHotelRate(int hotelRate) {
        this.hotelRate = hotelRate;
    }

    public Item(Integer id, String title, String description, String url, float latitude, float longitude, boolean isLiked, int category, int hotelRate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLiked = isLiked;
        this.category = category;
        this.hotelRate = hotelRate;
    }

    public Item(Integer id, String title, String description, String url, float latitude, float longitude, boolean isLiked, int category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isLiked = isLiked;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", isLiked=" + isLiked +
                ", category=" + category +
                ", hotelRate=" + hotelRate +
                '}';
    }
}
