package etf.unibl.org.bltv;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import etf.unibl.org.bltv.activity.AboutActivity;
import etf.unibl.org.bltv.activity.CurrentWeatherActivity;
import etf.unibl.org.bltv.activity.MapsActivity;
import etf.unibl.org.bltv.activity.SettingsActivity;
import etf.unibl.org.bltv.adapter.ItemAdapter;
import etf.unibl.org.bltv.adapter.TabPager;
import etf.unibl.org.bltv.db.AppDatabase;
import etf.unibl.org.bltv.db.Item;
import etf.unibl.org.bltv.fragment.IFragment;
import etf.unibl.org.bltv.model.news.NewsModel;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private static final String SELECTED_LANGUAGE = "language";
    private ViewPager viewPager;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private TabLayout tabs;
    private TabPager tabPager;
    public static final List<Item> favItems=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ON CREATE MAIN ACTIVITY");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AppController.mainActivity=this;
        String lang = getPersistedData(getApplicationContext(), Resources.getSystem().getConfiguration().locale.getLanguage());
        if(lang.equals("sr"))
            setLocale(getApplicationContext(), "sr");
        else
            setLocale(getApplicationContext(), "en");
       tabs=findViewById(R.id.tabs);
        tabPager=new TabPager(getSupportFragmentManager());
        tabPager.setActivity(this);
        AppController.cacheContext=this.getApplicationContext();
        AppController.tabPager=tabPager;
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTitle(tabPager.getPageTitle(tab.getPosition()));
                searchMenuItem=toolbar.getMenu().findItem(R.id.action_search);
                if(searchMenuItem!=null) {
                    if(tab.getPosition()==0 || tab.getPosition()==5) {
                        searchMenuItem.setVisible(false);
                    }else{
                            searchMenuItem.setVisible(true);
                    }
                    searchMenuItem.collapseActionView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager=findViewById(R.id.viewpager);

        viewPager.setAdapter(tabPager);
        viewPager.setOffscreenPageLimit(6);
        tabs.setupWithViewPager(viewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppController.mainActivity=this;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String lang = getPersistedData(getApplicationContext(), Resources.getSystem().getConfiguration().locale.getLanguage());
        if(lang.equals("sr"))
            setLocale(getApplicationContext(), "sr");
        else
            setLocale(getApplicationContext(), "en");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView =
                (SearchView) searchMenuItem.getActionView();
        searchMenuItem.setVisible(false);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                int position=tabs.getSelectedTabPosition();
                System.out.println("submit:"+newText);
                if(newText!=null) {
                    if (position != 0 && position != 5) {
                        IFragment fragment = (IFragment) AppController.tabPager.getItem(position);
                        ((ItemAdapter)fragment.getAdapter()).getFilter().filter(newText);
                        return false;
                    }
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                int position=tabs.getSelectedTabPosition();
                System.out.println("query:"+newText);
                if(newText!=null) {
                        if (position != 0 && position != 5) {
                            IFragment fragment = (IFragment) AppController.tabPager.getItem(position);
                            ((ItemAdapter)fragment.getAdapter()).getFilter().filter(newText);
                            return false;
                        }
                    }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_weather) {
            Intent intent=new Intent(this, CurrentWeatherActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_about){
            Intent intent=new Intent(this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
          Intent intent=new Intent(this, SettingsActivity.class);
          startActivity(intent);
        } else if(id==R.id.nav_maps){
           Intent intent=new Intent(this, MapsActivity.class);
           startActivity(intent);
        }
        else if(id==R.id.nav_favourites){
            tabs.getTabAt(5).select();
        }else if(id==R.id.nav_news){
            tabs.getTabAt(0).select();
        }
        else if(id==R.id.nav_institutions){
            tabs.getTabAt(1).select();
        }
        else if(id==R.id.nav_monuments){
            tabs.getTabAt(2).select();
        }
        else if(id==R.id.nav_hotels){
            tabs.getTabAt(3).select();
        }
        else if(id==R.id.nav_events){
            tabs.getTabAt(4).select();
        }else if(id==R.id.nav_rate_app){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //localization

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        String language = getLanguage(context);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public Context setLocale(Context context, String language) {
        persist(context, language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(context, locale);
        }
        updateResourcesLocaleLegacy(context, locale);
        return context;
    }

    // helpers
    private void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    public String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    private String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("DESTROYED MAIN ACTIVITY");
    }

}
