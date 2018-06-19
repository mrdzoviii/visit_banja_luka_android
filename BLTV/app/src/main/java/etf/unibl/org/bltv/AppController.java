package etf.unibl.org.bltv;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import etf.unibl.org.bltv.adapter.TabPager;
import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.fragment.EventsFragment;
import etf.unibl.org.bltv.fragment.FavouritesFragment;
import etf.unibl.org.bltv.fragment.HotelsFragment;
import etf.unibl.org.bltv.fragment.InstitutionsFragment;
import etf.unibl.org.bltv.fragment.MonumentsFragment;
import etf.unibl.org.bltv.fragment.NewsFragment;

public class AppController extends Application {
    public static final String TAG= AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    public static Context cacheContext;
    public static boolean languageChanged=false;
    public static TabPager tabPager;

    //optimization
    public static Activity mainActivity;


    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }
    public boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;
        }
            return false;

    }
    public static synchronized AppController getmInstance(){
        return mInstance;
    }

    public RequestQueue getmRequestQueue() {
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }



    public <T> void addToRequesQueue(Request<T> request){
        request.setTag(TAG);
        getmRequestQueue().add(request);
    }
}
