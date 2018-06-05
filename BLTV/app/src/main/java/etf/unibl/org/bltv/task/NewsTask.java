package etf.unibl.org.bltv.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.db.News;
import etf.unibl.org.bltv.model.news.NewsModel;
import etf.unibl.org.bltv.util.GlideApp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsTask extends AsyncTask<Activity,Void,List<NewsModel>> {
    private final static String TAG=NewsTask.class.getSimpleName();
    private Activity context;
    private Boolean database=false;
    private List<NewsModel> news;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout srl;
    private ProgressBar bar;

    public NewsTask(List<NewsModel> news, RecyclerView.Adapter mAdapter, SwipeRefreshLayout srl) {
        this.context = context;
        this.news = news;
        this.mAdapter = mAdapter;
        this.srl=srl;

    }
    public NewsTask(List<NewsModel> news, RecyclerView.Adapter mAdapter, SwipeRefreshLayout srl, ProgressBar bar) {
        this.context = context;
        this.news = news;
        this.mAdapter = mAdapter;
        this.srl=srl;
        this.bar=bar;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(bar!=null){
            if(news.size()==0){
                bar.setVisibility(ProgressBar.VISIBLE);
            }
        }
    }

    @Override
    protected List<NewsModel> doInBackground(Activity... contexts) {
        context = contexts[0];
        List<NewsModel> serverNews=null;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean caching = sharedPref.getBoolean("caching", true);
        if(AppController.getmInstance().isOnline()) {
            Log.e(TAG, "start aynctask to retrieve news");
            serverNews = getNewsFromServer(context);
            database=false;
            if (serverNews != null) {
                if (serverNews.size() > 0) {
                    if (caching) {
                        System.out.println(GlideApp.getPhotoCacheDir(AppController.cacheContext).toString());
                        AppDatabase.getAppDatabase(context).newsDao().clear();
                        List<News> newsDb = new ArrayList<>();
                        for (NewsModel item : serverNews) {
                            newsDb.add(new News(item));
                        }
                        AppDatabase.getAppDatabase(context).beginTransaction();
                        try {
                            for (News item:newsDb) {
                                AppDatabase.getAppDatabase(context).newsDao().insert(item);
                            }
                            AppDatabase.getAppDatabase(context).setTransactionSuccessful();
                        } finally {
                            AppDatabase.getAppDatabase(context).endTransaction();
                        }
                    }else {
                        GlideApp.getPhotoCacheDir(AppController.cacheContext).delete();
                        AppDatabase.getAppDatabase(context).newsDao().clear();
                        System.out.println("CLEARRR CACHEEE");
                    }
                }
            }
        }else{
            if(caching) {
                Log.e(TAG, "start aynctask to retrieve news from db");
                database=true;
                serverNews = new ArrayList<>();
                for (News item : AppDatabase.getAppDatabase(context).newsDao().getAll()) {
                    serverNews.add(new NewsModel(item));
                }
                Collections.reverse(serverNews);
            }else{
                GlideApp.getPhotoCacheDir(AppController.cacheContext).delete();
                AppDatabase.getAppDatabase(context).newsDao().clear();
                System.out.println("CLEAR CACHEEE");
            }
        }
        return serverNews;
    }

    @Override
    protected void onPostExecute(List<NewsModel> data) {
        super.onPostExecute(data);
        if(data!=null){

            boolean change=false;
                for (NewsModel info : data) {
                    if (!news.contains(info)) {
                        news.add(info);
                        change = true;
                    }
                }
            if(change)
                mAdapter.notifyDataSetChanged();
        }
        if(bar!=null && news.size()>0){
            bar.setVisibility(ProgressBar.GONE);
        }
        srl.setRefreshing(false);
    }
    public static List<NewsModel> getNewsFromServer(Context context) {
        String serviceUrl = context.getString(R.string.pull_news);
        URL url = null;
        try {
            Log.d(TAG, "call rest service to get json response");
            OkHttpClient client=new OkHttpClient();
            Request request = new Request.Builder()
                    .url(context.getString(R.string.pull_news))
                    .build();
            Response response = client.newCall(request).execute();
            Gson gson=new Gson();
            return Arrays.asList(gson.fromJson(response.body().string(),NewsModel[].class));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "error in getting and parsing response");
        }
        return null;
    }

}
