package etf.unibl.org.bltv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.Map;

import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.util.GlideApp;

public class ItemScrollingActivity extends AppCompatActivity  {
    private Item item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        RatingBar rating = findViewById(R.id.rating);
        if (item.getCategory() == Item.HOTEL) {
            rating.setVisibility(RatingBar.VISIBLE);
            rating.setNumStars(item.getHotelRate());
            rating.setRating(rating.getNumStars());

        } else {
            rating.setVisibility(RatingBar.GONE);
        }
        WebView description = findViewById(R.id.description);
        ImageView image = findViewById(R.id.image);
        System.out.println(item.getDescription());
        description.loadData("<p align=\"justify\">" + item.getDescription() + "</p>", "text/html", "UTF-8");
        //image.setImageResource(item.getUrl());
        GlideApp.with(this).load(item.getPath()).placeholder(R.drawable.error404).into(image);
        setTitle(item.getTitle());


    }
}
