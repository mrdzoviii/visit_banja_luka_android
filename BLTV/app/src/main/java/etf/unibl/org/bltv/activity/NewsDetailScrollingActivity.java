package etf.unibl.org.bltv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.adapter.TabPager;
import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.model.news.Article;
import etf.unibl.org.bltv.model.news.NewsModel;
import etf.unibl.org.bltv.util.GlideApp;

public class NewsDetailScrollingActivity extends AppCompatActivity {
    private WebView content;
    private ImageView image;
    private NewsModel news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = findViewById(R.id.image);
        content = findViewById(R.id.content);
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        TabPager pager=AppController.tabPager;
        if (id != 0 && pager!=null) {
            for (NewsModel tmp : pager.getNewsFragment().getNews()) {
                if (tmp.getNewsId().equals(id)) {
                    news = tmp;
                }
            }
        }
        if (news != null) {
            if (news.getBody() == null) {
                pullArticle(news.getNewsId());
            } else {
                setContent(news);
            }
            GlideApp.with(AppController.cacheContext).load(news.getImageUrl()).placeholder(R.drawable.error404).into(image);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pullArticle(int id) {
        JsonObjectRequest request = new JsonObjectRequest(getString(R.string.pull_article) + id, null, response -> {
            Gson gson = new Gson();
            Article article = gson.fromJson(response.toString(), Article.class);
            news.setBody(article.getBody());
            //dodajemo opis
            if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("caching",true)==true)
            new Thread(() -> {
                AppDatabase.getAppDatabase(this).newsDao().update(id, news.getBody());
                System.out.println(AppDatabase.getAppDatabase(this).newsDao().findByID(id).getTitle()+"  added to db");
            }).start();
            setContent(news);
        }, error -> {
        });
        AppController.getmInstance().addToRequesQueue(request);

    }

    private void setContent(NewsModel news) {
        content.getSettings().setJavaScriptEnabled(true);
        String html = "<h1>" + news.getTitle() + "</h1><p>" + news.getBody();
        content.loadDataWithBaseURL("", html, "text/html", "UTF-8", "");
    }

}
