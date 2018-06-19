package etf.unibl.org.bltv.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.util.Objects;

import etf.unibl.org.bltv.model.news.NewsModel;

@Entity(tableName = "news")
public class News implements Comparable<News> {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return getNewsID() == news.getNewsID();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNewsID());
    }

    public static final String TABLE_NAME="news";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_IMAGE="image";
    public static final String COLUMN_CONTENT="content";
    public static final String COLUMN_IMAGE_URL="image_url";
    public static final String COLUMN_DATE="date";
    @PrimaryKey
    @ColumnInfo(name=COLUMN_ID)
    private int newsID;
    @ColumnInfo(name=COLUMN_TITLE)
    private String title;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name =COLUMN_IMAGE)
    private byte[] image;
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT,name=COLUMN_CONTENT)
    private String content;
    @ColumnInfo(name=COLUMN_IMAGE_URL)
    private String url;
    public News(int newsID) {
        this.newsID = newsID;
    }

    public static News fromContentValues(ContentValues values) {
        final News news = new News();
        if (values.containsKey(COLUMN_ID)) {
            news.setNewsID(values.getAsInteger(COLUMN_ID));
        }
        if (values.containsKey(COLUMN_TITLE)) {
            news.setTitle(values.getAsString(COLUMN_TITLE));
        }
        if (values.containsKey(COLUMN_CONTENT)) {
            news.setContent(values.getAsString(COLUMN_CONTENT));
        }
        if (values.containsKey(COLUMN_IMAGE)) {
            news.setImage(values.getAsByteArray(COLUMN_IMAGE));
        }
        if (values.containsKey(COLUMN_IMAGE_URL)) {
            news.setUrl(values.getAsString(COLUMN_IMAGE_URL));
        }
        return news;
    }
    public News() {
    }

    public News(Integer newsID, String title, byte[] image, String content, String url) {
        this.newsID = newsID;
        this.title = title;
        this.image = image;
        this.content = content;
        this.url = url;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public News(NewsModel model){
        this.setNewsID(model.getNewsId());
        setUrl(model.getImageUrl());
        setTitle(model.getTitle());
    }

    @Override
    public int compareTo(@NonNull News o) {
        return o.getNewsID()-newsID;
    }
}
