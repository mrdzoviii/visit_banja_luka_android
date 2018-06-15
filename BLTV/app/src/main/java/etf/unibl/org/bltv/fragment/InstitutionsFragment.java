package etf.unibl.org.bltv.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private RecyclerView.Adapter mAdapter;
    private Boolean pulled=false;

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
       // load();
        System.out.println("INSTITUONS ON START");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("INSTITUTIONS ON CREATE");
        mAdapter = new ItemAdapter(items, AppController.mainActivity,this);
        if(!pulled){
            pulled=true;
            new ItemsTask(items,mAdapter,Item.INSTITUTION).execute(AppController.mainActivity);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        load();
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
