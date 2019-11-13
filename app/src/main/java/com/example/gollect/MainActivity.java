package com.example.gollect;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.gollect.adapter.TabPagerAdapter;
import com.example.gollect.adapter.TcListviewAdapter;
import com.example.gollect.fragment.TextFragment;
import com.example.gollect.utility.BackPressCloseHandler;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BackPressCloseHandler backPressCloseHandler;
    private Toast toast;
    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_gollect_light_24dp);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("텍스트"));
        tabLayout.addTab(tabLayout.newTab().setText("동영상"));
        tabLayout.addTab(tabLayout.newTab().setText("즐겨찾기"));
        tabLayout.addTab(tabLayout.newTab().setText("알림"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager)findViewById(R.id.pager);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
/*
        TextFragment textFragment = (TextFragment) getSupportFragmentManager().findFragmentById(R.id.textfragment);
        textFragment.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_foreground), "NEW","Contents");
 */
    }
    @Override
    public void onBackPressed(){
        backPressCloseHandler.onBackPressed();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.refresh_menu:
                break;
            case R.id.search_menu:
                Log.d("jaejin","search");
                break;
            case R.id.more_menu:
                Log.d("jaejin","more");
                break;
            case R.id.modification:
                Log.d("jaejin","first");
                break;
            case R.id.logout:
                Log.d("jaejin","second");
                break;
        }
        return true;
    }
}
