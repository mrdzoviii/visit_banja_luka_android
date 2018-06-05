package etf.unibl.org.bltv.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.db.Item;


public class ItemsTask extends AsyncTask<Activity, Void, List<Item>> {
    private final String TAG=ItemsTask.class.getSimpleName();
    private Activity context;
    private List<Item> items;
    private RecyclerView.Adapter mAdapter;
    private int category;

    public ItemsTask(List<Item> items, RecyclerView.Adapter mAdapter,int category) {
        this.items = items;
        this.mAdapter = mAdapter;
        this.category=category;
    }

    @Override
    protected List<Item> doInBackground(Activity... contexts) {
        context = contexts[0];
        Log.e(TAG, "start asynctask to retrieve items");
        AppDatabase database=AppDatabase.getAppDatabase(context);
        List<Item> dbItems=database.itemDao().getByCategory(category);

        return dbItems;
    }
    @Override
    protected void onPostExecute(List<Item> dbItems) {
        super.onPostExecute(items);
        if(items!=null){
            for(Item i:dbItems){
                if(!items.contains(i)){
                    items.add(i);
                }
            }
            mAdapter.notifyDataSetChanged();
        }

    }

}
