package com.android.people.quotesandroidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.utils.CategoryClickListener;
import com.android.people.quotesandroidapp.utils.QuoteClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, QuoteClickListener, CategoryClickListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Open Home fragment on launching app
        Fragment fragment = Home.newInstance();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_activity_fragment_container, fragment).commit();


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                fragment = Home.newInstance();
                break;
            case R.id.nav_all_quotes:
                fragment = QuotesListFragment.newInstance(QuotesListFragment.TYPE_QUOTES);
                break;
            case R.id.nav_favorites:
//                Intent intent = new Intent(this,FavoritesActivity.class);
//                startActivity(intent);
                fragment = QuotesListFragment.newInstance(QuotesListFragment.TYPE_FAVORITES);
                break;
            case R.id.nav_categories:
                fragment = CategoriesFragment.newInstance();
                break;
            case R.id.nav_settings:

                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;

            case R.id.nav_about:
                //TODO

                break;
            default:
                fragment = Home.newInstance();
                break;

        }

        // Insert the chosen fragment by replacing any existing fragment
        fragmentManager = getSupportFragmentManager();
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.main_activity_fragment_container, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onQuoteClicked(long quoteID) {
        Intent intent = new Intent(this, SingleQuoteActivity.class);
        intent.putExtra("QUOTE_ID", quoteID);
        startActivity(intent);
    }

    @Override
    public void onCategoryClicked(int catID) {
        Intent intent = new Intent(this,QuotesOfACategory.class);
        intent.putExtra("Name",catID);
        startActivity(intent);

    }
}
