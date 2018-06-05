package etf.unibl.org.bltv.model.news;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class Article implements Serializable {
    @SerializedName("Tjelo")
    private String body;

    public Article(String body) {
        this.body = body;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
