package etf.unibl.org.bltv.fragment;


import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.MainActivity;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.adapter.NewsAdapter;
import etf.unibl.org.bltv.model.news.NewsModel;
import etf.unibl.org.bltv.task.NewsTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private List<NewsModel> news=new ArrayList<>();
    public List<NewsModel> getNews() {
        return news;
        //return MainActivity.newsItems;
    }
    private ProgressDialog nDialog;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private boolean caching;
    long currentVisiblePosition = 0;
    private Boolean pulled=false;
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        caching=sharedPref.getBoolean("caching",false);
        //prikazivanje kartica

        recyclerView = getActivity().findViewById(R.id.recyclerView);
        progressBar=getActivity().findViewById(R.id.progressBar);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(news,getActivity());
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout=getActivity().findViewById(R.id.swipeRefreshLayout);
        pullNews();
        swipeRefreshLayout.setOnRefreshListener(() -> {
                new NewsTask(news, mAdapter, swipeRefreshLayout,progressBar).execute(getActivity());
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.indigo,R.color.red,R.color.lime);
       recyclerView.addOnScrollListener(new OnScrollListener() {
           private boolean scrollEnabled;
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
           }

           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               int topRowVerticalPosition =
                       (recyclerView == null || recyclerView.getChildCount() == 0) ?
                               0 :recyclerView.getChildAt(0).getTop();
               LinearLayoutManager layoutManager = ((LinearLayoutManager)recyclerView.getLayoutManager());
               int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
               boolean newScrollEnabled =
                       (firstVisiblePosition == 0 && topRowVerticalPosition >= 0) ?
                               true : false;
               if (null != swipeRefreshLayout && scrollEnabled != newScrollEnabled) {
                   // Start refreshing....
                   swipeRefreshLayout.setEnabled(newScrollEnabled);
                   scrollEnabled = newScrollEnabled;
               }
           }
       });


    }
    private void pullNews(){
            if (!pulled) {
                pulled = true;
                new NewsTask(news, mAdapter, swipeRefreshLayout,progressBar).execute(getActivity());
            }
    }
    public int getCount(){
        return news.size();

    }
    @Override
    public void onResume() {
        super.onResume();
        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition((int) currentVisiblePosition);
        currentVisiblePosition = 0;

    }

    @Override
    public void onPause() {
        super.onPause();
        currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
