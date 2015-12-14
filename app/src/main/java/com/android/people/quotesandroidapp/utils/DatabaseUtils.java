package com.android.people.quotesandroidapp.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.provider.categories.CategoriesSelection;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.provider.quotes.QuotesSelection;

import java.util.Random;

/**
 * Created by amrelmasry on 12/14/15.
 */
public class DatabaseUtils {
    public static Quote getRandomQuote(Context context) {

        // TODO generate random quote in a more efficient way


        QuotesSelection where = new QuotesSelection();
        int max = where.count(context.getContentResolver());
        Toast.makeText(context, "" + max, Toast.LENGTH_SHORT).show();

        Random rand = new Random();
        int id = rand.nextInt(max);

        where.id(id);
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
}