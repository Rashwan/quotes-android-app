package com.android.people.quotesandroidapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.provider.categories.CategoriesSelection;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.provider.quotes.QuotesSelection;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;
import com.android.people.quotesandroidapp.provider.status.StatusContentValues;
import com.android.people.quotesandroidapp.provider.status.StatusCursor;
import com.android.people.quotesandroidapp.provider.status.StatusSelection;

import java.util.Random;

/**
 * Created by amrelmasry on 12/14/15.
 */
public class DatabaseUtils {
    public static Quote getRandomQuote(Context context) {

        // TODO generate random quote in a more efficient way


        QuotesSelection where = new QuotesSelection();
        int max = where.count(context.getContentResolver());

        Random rand = new Random();
        int id = rand.nextInt(max) + 1;

        where.id(id);
        QuotesCursor quoteCursor = where.query(context);

        if (quoteCursor.moveToNext()) {

            return new Quote(quoteCursor.getId(), quoteCursor.getContent(), quoteCursor.getCategoryid());

        }
        return null;

    }

    public static Quote getSpecificQuote(long quoteID, Context context) {

        QuotesSelection where = new QuotesSelection();
        where.id(quoteID);
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

    public static void addToFavorites(long quoteID, Context context) {

        StatusContentValues contentValues = new StatusContentValues();
        contentValues.putQuoteid((int) quoteID)
                .putFavorite(true);

        context.getContentResolver().insert(StatusColumns.CONTENT_URI, contentValues.values());
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();

        Log.i("Fav_Q", "saved id is = " + quoteID);


    }

    public static Quote getRandomFavoriteQuote(Context context) {

        // TODO implement this in a more efficient way

        StatusSelection where = new StatusSelection();
        int max = where.count(context.getContentResolver());


//        String[] projection = {StatusColumns.QUOTEID,QuotesColumns.CONTENT,QuotesColumns.CATEGORYID};
//        StatusSelection where = new StatusSelection();
//        where.query(context, projection);
//
//        int max = where.count(context.getContentResolver());

        Toast.makeText(context, "favorites quotes count is " + max, Toast.LENGTH_SHORT).show();

        if (max > 0) {
            Random rand = new Random();
            int id = rand.nextInt(max) + 1;
            Log.i("Fav_Q", "random id to get is = " + id);

            where.id(id);
            StatusCursor statusCursor = where.query(context);


            if (statusCursor.moveToNext()) {
                Log.i("Fav_Q", "chosen quoteID to get is = " + statusCursor.getQuoteid());

                QuotesSelection where2 = new QuotesSelection();
                where2.id(statusCursor.getQuoteid());
                QuotesCursor quoteCursor = where2.query(context);


                if (quoteCursor.moveToNext()) {
                    Log.i("Fav_Q", "id is = " + quoteCursor.getId());
                    Log.i("Fav_Q", "quote is = " + quoteCursor.getContent());
                    Log.i("Fav_Q", "category id is = " + quoteCursor.getCategoryid());

                    return new Quote(quoteCursor.getId(), quoteCursor.getContent(), quoteCursor.getCategoryid());
                }
            }

        }


        return null;

    }
}













