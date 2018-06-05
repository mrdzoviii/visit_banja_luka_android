package etf.unibl.org.bltv.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import etf.unibl.org.bltv.AppController;
import etf.unibl.org.bltv.R;
import etf.unibl.org.bltv.fragment.EventsFragment;
import etf.unibl.org.bltv.fragment.FavouritesFragment;
import etf.unibl.org.bltv.fragment.HotelsFragment;
import etf.unibl.org.bltv.fragment.InstitutionsFragment;
import etf.unibl.org.bltv.fragment.MonumentsFragment;
import etf.unibl.org.bltv.fragment.NewsFragment;

public class TabPager extends FragmentStatePagerAdapter {
    private String[] titles={"News","Institutions","Monuments","Hotels","Events","Favourites"};
    private final NewsFragment newsFragment=new NewsFragment();
    private final MonumentsFragment monumentsFragment=new MonumentsFragment();
    private final InstitutionsFragment institutionsFragment=new InstitutionsFragment();
    private final HotelsFragment hotelsFragment=new HotelsFragment();
    private final EventsFragment eventsFragment=new EventsFragment();
    private final FavouritesFragment favouritesFragment=new FavouritesFragment();
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    /*
    public FavouritesFragment getFavouritesFragment() {
        if(favouritesFragment==null){
            favouritesFragment=new FavouritesFragment();
        }
        return favouritesFragment;
    }

    public NewsFragment getNewsFragment() {
        if(newsFragment==null){
            newsFragment=new NewsFragment();
        }
        return newsFragment;
    }

    public InstitutionsFragment getInstitutionsFragment() {
        if(institutionsFragment==null){
            institutionsFragment=new InstitutionsFragment();
        }
        return institutionsFragment;
    }

    public MonumentsFragment getMonumentsFragment() {
        if(monumentsFragment==null){
            monumentsFragment=new MonumentsFragment();
        }
        return monumentsFragment;
    }

    public HotelsFragment getHotelsFragment() {
        if(hotelsFragment==null){
            hotelsFragment=new HotelsFragment();
        }
        return hotelsFragment;
    }

    public EventsFragment getEventsFragment() {
        if(eventsFragment==null){
            eventsFragment=new EventsFragment();
        }
        return eventsFragment;
    }
*/

    public NewsFragment getNewsFragment() {
        return newsFragment;
    }

    public MonumentsFragment getMonumentsFragment() {
        return monumentsFragment;
    }

    public InstitutionsFragment getInstitutionsFragment() {
        return institutionsFragment;
    }

    public HotelsFragment getHotelsFragment() {
        return hotelsFragment;
    }

    public EventsFragment getEventsFragment() {
        return eventsFragment;
    }

    public FavouritesFragment getFavouritesFragment() {
        return favouritesFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
               // titles[position];
        switch (position){
            case 0: return activity.getString(R.string.tab_news);
            case 1:return activity.getString(R.string.tab_institutions);
            case 2:return  activity.getString(R.string.tab_monuments);
            case 3:return  activity.getString(R.string.tab_hotels);
            case 4:return activity.getString(R.string.tab_events);
            case 5:return activity.getString(R.string.tab_favourites);
            default: return null;
        }
    }

    public TabPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return getNewsFragment();
            case 1:return getInstitutionsFragment();
            case 2:return  getMonumentsFragment();
            case 3:return  getHotelsFragment();
            case 4:return getEventsFragment();
            case 5:return getFavouritesFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
