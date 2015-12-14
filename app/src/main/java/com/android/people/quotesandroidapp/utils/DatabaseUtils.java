package com.android.people.quotesandroidapp.utils;

import android.content.Context;

import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.provider.quotes.QuotesSelection;

/**
 * Created by amrelmasry on 12/14/15.
 */
public class DatabaseUtils {
    public static Quote getRandomQuote(Context context) {

        // TODO generate random quote
        QuotesSelection where = new QuotesSelection();
        where.id(2);
        QuotesCursor quoteCursor = where.query(context);

        if (quoteCursor.getCount() > 0 && quoteCursor.moveToNext()) {

            return new Quote(quoteCursor.getId(), quoteCursor.getContent(), quoteCursor.getCategoryid());

        }
        return null;

    }
}