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
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.adapter.ItemAdapter;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.task.ItemsTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstitutionsFragment extends Fragment implements IFragment {

    private List<Item> items=new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String getTitle() {
        return "Institutions Fragment";
    }
    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter = new ItemAdapter(items, AppController.mainActivity);
    private Boolean pulled=false;
    {
        //load();
        if(!pulled){
            pulled=true;
            new ItemsTask(items,mAdapter,Item.INSTITUTION).execute(AppController.mainActivity);
        }
    }
    private  RecyclerView.LayoutManager layoutManager;
    long currentVisiblePosition = 0;

    public InstitutionsFragment() {
        // Required empty public constructor
    }
    public Integer getCount(){
        return items.size();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_institutions, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
        //prikazivanje kartica

        /*recyclerView = getActivity().findViewById(R.id.rvInstitutions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
       // mAdapter = new ItemAdapter(items,getActivity());
        recyclerView.setAdapter(mAdapter);
        /*if(!pulled){
            pulled=true;
            new ItemsTask(items,mAdapter,Item.INSTITUTION).execute(getActivity());
        }*/

    }
    private void load(){
        recyclerView = AppController.mainActivity.findViewById(R.id.rvInstitutions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //mAdapter = new ItemAdapter(items,getActivity());
        recyclerView.setAdapter(mAdapter);
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

}
