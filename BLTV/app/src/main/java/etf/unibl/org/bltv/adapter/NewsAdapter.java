package etf.unibl.org.bltv.adapter;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.activity.ErrorActivity;
import etf.unibl.org.bltv.activity.NewsDetailScrollingActivity;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.model.news.NewsModel;
import etf.unibl.org.bltv.util.GlideApp;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final List<NewsModel> news;
    private Activity activity;
    private ImageLoader imageLoader;
    private NewsModel newsModel;
    public NewsAdapter(List<NewsModel> items, Activity activity) {
        this.news = items;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final NewsModel item = news.get(position);
        newsModel=item;
        holder.txtTitle.setText(item.getTitle());
        GlideApp.with(AppController.cacheContext).load(item.getImageUrl()).
                error(R.drawable.error404)
        .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true).into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
            showArticle(item.getNewsId());
        });
        holder.txtTitle.setOnClickListener(v -> {
            showArticle(item.getNewsId());
        });

    }
    private void showArticle(int id){
       if(AppController.getmInstance().isOnline()==false &&PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("caching",true)==true && newsModel.getBody()==null) {
                Intent intent = new Intent(activity, ErrorActivity.class);
                activity.startActivity(intent);
        }else {
            Intent intent = new Intent(activity, NewsDetailScrollingActivity.class);
            intent.putExtra("id", id);
            activity.startActivity(intent);
       }
    }
    public void add(int position, NewsModel item) {
        news.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public long getItemId(int position) {
        return news.get(position).getNewsId();
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public ImageView imageView;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTitle = (TextView) v.findViewById(R.id.title);
            imageView= (ImageView) v.findViewById(R.id.imageView);
        }



    }
}
