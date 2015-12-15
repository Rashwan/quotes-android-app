package com.android.people.quotesandroidapp.utils;

import android.content.Context;

import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.provider.categories.CategoriesSelection;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.provider.quotes.QuotesSelection;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;
import com.android.people.quotesandroidapp.provider.status.StatusCursor;
import com.android.people.quotesandroidapp.provider.status.StatusSelection;

/**
 * Created by amrelmasry on 12/14/15.
 */
public class DatabaseUtils {
    public static Quote getRandomQuote(Context context) {

        // TODO generate random quote
        QuotesSelection where = new QuotesSelection();
        where.id(2);
        QuotesCursor quoteCursor = where.query(context);

        if (quoteCursor.moveToNext()) {

            return new Quote(quoteCursor.getId(), quoteCursor.getContent(), quoteCursor.getCategoryid());

        }
        return null;

    }

    public static String getCategoryName(long categoryID, Context context) {

        CategoriesSelection where = new CategoriesSelection();
        where.id(categoryID);
        CategoriesCursor categoryCursor = where.query(context);

        if (categoryCursor.moveToNext()) {

            return categoryCursor.getCategory();

        }
        return null;
    }

    public static QuotesCursor getAllQuotesWithCategories (Context context){
        QuotesSelection where;
        QuotesCursor quotesCursor;

        where = new QuotesSelection();
        String[] projection = {QuotesColumns._ID,QuotesColumns.CONTENT, CategoriesColumns.CATEGORY};
        quotesCursor = where.query(context.getContentResolver(),projection);
        return quotesCursor;

    }

    public static Boolean isQuoteFavorite(Context context,Long quoteId){
        Boolean isFavorite = false;
        String[] projection = {StatusColumns.FAVORITE};
        StatusSelection where = new StatusSelection();

        StatusCursor statusCursor = where.quoteid(quoteId).query(context.getContentResolver(), projection);

        if (statusCursor != null && statusCursor.moveToNext()){
            isFavorite = statusCursor.getFavorite();
        }
        return isFavorite;
    }
}