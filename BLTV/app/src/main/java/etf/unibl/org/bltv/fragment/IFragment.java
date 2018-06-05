package etf.unibl.org.bltv.fragment;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.adapter.ItemAdapter;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.task.ItemsTask;

/**
 * A simple {@link Fragment} subclass.
 */
public interface IFragment {
    List<Item> getItems();
    RecyclerView getRecyclerView();
    RecyclerView.Adapter getAdapter();
    Activity getActivity();
    String getTitle();
}
