package etf.unibl.org.bltv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.MainActivity;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.adapter.ItemAdapter;
import etf.unibl.org.bltv.db.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment implements IFragment {
    private  RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager layoutManager;

    public List<Item> getItems() {
        return MainActivity.favItems;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    public FavouritesFragment() {
        // Required empty public constructor
    }
    @Override
    public String getTitle() {
        return "Fav Fragment";
    }


   // long currentVisiblePosition = 0;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }



    @Override
    public void onStart() {
        super.onStart();
        load();

    }
    private void load(){
        recyclerView =AppController.mainActivity.findViewById(R.id.rvFavorites);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter= new ItemAdapter(MainActivity.favItems,getActivity(),this);
        recyclerView.setAdapter(mAdapter);
        System.out.println("Fav load"+MainActivity.favItems.size());
    }

    @Override
    public void onResume() {
        super.onResume();
        /*((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition((int) currentVisiblePosition);
        currentVisiblePosition = 0;*/

    }

    @Override
    public void onPause() {
        super.onPause();
      //  currentVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

}
