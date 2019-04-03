package com.vrjulianti.dictionary.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;
import com.vrjulianti.dictionary.Adapter.RvAdapter;
import com.vrjulianti.dictionary.Class.Dictionary;
import com.vrjulianti.dictionary.Database.DictBuilder;
import com.vrjulianti.dictionary.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements MaterialSearchBar.OnSearchActionListener, NavigationView.OnNavigationItemSelectedListener {

    public static String kata;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_dictionary)
    RecyclerView rvDictionary;

    @BindView(R.id.searchBar)
    MaterialSearchBar searchBar;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle toggle;

    private DictBuilder dictBuilder;
    private RvAdapter adapter;

    private ArrayList<Dictionary> kamus_list = new ArrayList<>();
    private boolean isIndo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setNavButtonEnabled(true);

        /**ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
         this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawerLayout.addDrawerListener(toggle);**/
        //toggle.syncState();

        searchBar.setOnSearchActionListener(this);
        dictBuilder = new DictBuilder(this);

        create_list();
        load_data();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void create_list() {
        adapter = new RvAdapter();
        rvDictionary.setLayoutManager(new LinearLayoutManager(this));
        rvDictionary.setAdapter(adapter);
    }

    private void load_data(String search) {
        try
        {
            dictBuilder.open();
            if(search.isEmpty()) kamus_list = dictBuilder.getAllData(isIndo);
            else kamus_list = dictBuilder.getDataByText(search, isIndo);

            if(isIndo)
                getSupportActionBar().setSubtitle(getResources().getString(R.string.indonesia_english));
            else getSupportActionBar().setSubtitle(getResources().getString(R.string.english_indonesia));

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            dictBuilder.close();
        }

        adapter.replaceAll(kamus_list);
    }

    private void load_data()
    {
        load_data("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onButtonClicked(int buttonCode) {

        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawerLayout.addDrawerListener(toggle);
                Log.d("checkNavBar", "onResume: NavBar bisa");
                toggle.syncState();
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text)
    {
        load_data(String.valueOf(text));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_e_i) {
            isIndo = false;
            load_data();
        } else if (id == R.id.nav_i_e) {
            isIndo = true;
            load_data();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
