package com.android.people.quotesandroidapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.utils.CategoryClickListener;
import com.android.people.quotesandroidapp.utils.QuoteClickListener;

public class FavoritesActivity extends AppCompatActivity implements QuoteClickListener,CategoryClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        //Received an intent to open quotes of a specific category
        if (intent != null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            QuotesListFragment quotesListFragment = QuotesListFragment.newInstance(QuotesListFragment.TYPE_FAVORITES);
            fragmentManager.beginTransaction().add(R.id.favorites_content,quotesListFragment).commit();
        }
    }

    @Override
    public void onCategoryClicked(int catID) {
        Intent intent = new Intent(this,QuotesOfACategory.class);
        intent.putExtra("Type",QuotesListFragment.TYPE_CATEGORIES);
        intent.putExtra("Name",catID);
        startActivity(intent);
    }

    @Override
    public void onQuoteClicked(long quoteID) {
        Intent intent = new Intent(this, SingleQuoteActivity.class);
        intent.putExtra("QUOTE_ID", quoteID);
        startActivity(intent);
    }
}
