package etf.unibl.org.bltv.task;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.db.Item;


public class MapTask extends AsyncTask<Activity, Void, List<Item>> {
    private final String TAG=MapTask.class.getSimpleName();
    private Activity context;
    private GoogleMap mMap;
    private List<Item> items;
    public MapTask(List<Item> items,GoogleMap mMap) {
        this.items = items;
        this.mMap=mMap;
    }

    @Override
    protected List<Item> doInBackground(Activity... contexts) {
        context = contexts[0];
        Log.e(TAG, "start asynctask to retrieve items");
        AppDatabase database=AppDatabase.getAppDatabase(context);
        List<Item> dbItems=database.itemDao().getAll();
        return dbItems;
    }
    @Override
    protected void onPostExecute(List<Item> dbItems) {
        super.onPostExecute(items);
        if(items!=null){
            for(Item i:dbItems){
                if(!items.contains(i)){
                    items.add(i);
                    Marker marker=mMap.addMarker(new MarkerOptions().position(new LatLng(i.getLatitude(),i.getLongitude())).title(i.getTitle()));
                    marker.setTag(i);
                }
            }
        }

    }

}
