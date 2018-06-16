package etf.unibl.org.bltv.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.images.internal.ImageUtils;

import java.sql.SQLOutput;
import java.util.List;
import java.util.concurrent.ExecutionException;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.activity.ItemScrollingActivity;
import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.fragment.FavouritesFragment;
import etf.unibl.org.bltv.fragment.IFragment;
import etf.unibl.org.bltv.util.GlideApp;
import etf.unibl.org.bltv.util.ImageSaver;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private final List<Item> items;
    private Activity activity;
    private IFragment fragment;
    public ItemAdapter(List<Item> items, Activity activity,IFragment fragment) {
        this.items = items;
        this.activity = activity;
        this.fragment=fragment;
    }
    public ItemAdapter(List<Item> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.card_tour, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Item item = items.get(position);
        if (item != null) {
            holder.txtTitle.setText(item.getTitle());
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideApp.with(activity).load(item.getPath()).centerCrop().into(holder.imageView);
            if (item.getCategory()==Item.HOTEL) {
                holder.ratingBar.setVisibility(RatingBar.VISIBLE);
                holder.ratingBar.setNumStars(item.getHotelRate());
                holder.ratingBar.setRating(item.getHotelRate());
            }else{
                holder.ratingBar.setVisibility(RatingBar.GONE);
            }
            if (!item.isLiked()) {
                holder.button.setBackgroundResource(R.drawable.ic_like_48dp);
            } else {
                holder.button.setBackgroundResource(R.drawable.ic_liked_48dp);
            }


            holder.imageView.setOnClickListener(v -> {
                Intent intent=new Intent(activity, ItemScrollingActivity.class);
                intent.putExtra("item",item);
                activity.startActivity(intent);
            });
            holder.txtTitle.setOnClickListener(v -> {
                Intent intent=new Intent(activity, ItemScrollingActivity.class);
                intent.putExtra("item",item);
                activity.startActivity(intent);
            });

            holder.button.setOnClickListener(v -> {

                if (item.isLiked()) {
                    item.setLiked(false);
                    new Thread(() -> {
                        AppDatabase.getAppDatabase(activity).itemDao().update(item);
                    }).start();
                    holder.button.setBackgroundResource(R.drawable.ic_like_48dp);
                    IFragment fragment= (IFragment) AppController.tabPager.getItem(item.getCategory());
                    ((ItemAdapter)fragment.getAdapter()).update(item);
                    ((ItemAdapter)AppController.tabPager.getFavouritesFragment().getAdapter()).remove(item);
                    Toast.makeText(activity, activity.getString(R.string.item)+" "+item.getTitle() + " "+activity.getString(R.string.removed_from_fav), Toast.LENGTH_LONG).show();
                } else {
                    item.setLiked(true);
                    new Thread(() -> {
                        AppDatabase.getAppDatabase(activity).itemDao().update(item);
                    }).start();
                    holder.button.setBackgroundResource(R.drawable.ic_liked_48dp);
                    ((ItemAdapter)AppController.tabPager.getFavouritesFragment().getAdapter()).add(item);
                    Toast.makeText(activity, activity.getString(R.string.item)+" "+item.getTitle() + " "+activity.getString(R.string.added_to_fav), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    public void update(Item item){
        int index=items.indexOf(item);
        if(index>=0) {
            Item item1=items.get(index);
            item1.setLiked(item.isLiked());
            notifyItemChanged(index);
        }
    }

    public void add(int position, Item item) {

        items.add(position, item);
        notifyItemInserted(position);
    }
    public void add(Item item){
        items.add(item);
        int indeks=items.indexOf(item);
        if(indeks>=0) {
            notifyDataSetChanged();
        }

    }
    public void remove(Item item){
        int indeks=items.indexOf(item);
        if(indeks>=0) {
            items.remove(indeks);
            notifyItemRemoved(indeks);
        }
    }
    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public ImageView imageView;
        public View layout;
        public ImageButton button;
        public RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTitle = (TextView) v.findViewById(R.id.title);
            imageView = v.findViewById(R.id.imageView);
            button = (ImageButton) v.findViewById(R.id.likeButton);
            ratingBar = v.findViewById(R.id.ratingBar);
            ratingBar.setVisibility(RatingBar.GONE);
        }


    }
}
