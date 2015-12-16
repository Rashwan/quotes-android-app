package com.android.people.quotesandroidapp.utils;

/**
 * Created by rashwan on 12/16/15.
 * Each activity that will open SingleQuoteActivity should implement this
 */

public interface QuoteClickListener {
    void onQuoteClicked(long quoteID);
}
