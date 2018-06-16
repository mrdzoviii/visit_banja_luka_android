package etf.unibl.org.bltv.model.news;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import etf.unibl.org.bltv.db.News;

public class NewsModel implements Serializable,Comparable<NewsModel> {
    @SerializedName("vijestID")
    private Integer newsId;
    @SerializedName("Naslov")
    private String title;
    @SerializedName("Slika")
    private String imageUrl;

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsModel)) return false;
        NewsModel news = (NewsModel) o;
        return Objects.equals(getNewsId(), news.getNewsId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNewsId());
    }

    public NewsModel() {
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public NewsModel(Integer newsId, String title, String date, String imageUrl) {
        this.newsId = newsId;
        this.title = title;
        this.imageUrl = imageUrl;
    }
    public NewsModel(News news){
        setNewsId(news.getNewsID());
        setBody(news.getContent());
        setImageUrl(news.getUrl());
        setTitle(news.getTitle());
    }

    @Override
    public int compareTo(@NonNull NewsModel o) {
        return o.getNewsId()-newsId;
    }
}
