package com.android.people.quotesandroidapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.provider.categories.CategoriesSelection;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
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

        QuotesSelection where = new QuotesSelection();
        String[] projection = QuotesColumns.ALL_COLUMNS;

        int max = where.count(context.getContentResolver());

        Random rand = new Random();
        int id = rand.nextInt(max) + 1;

        where.id((long) id);
        QuotesCursor quoteCursor = where.query(context, projection);


        if (quoteCursor != null && quoteCursor.moveToNext()) {
            return new Quote(quoteCursor.getId(), quoteCursor.getContent(), quoteCursor.getCategoryid());
        }
        return null;

    }

    public static Quote getSpecificQuote(long quoteID, Context context) {
        QuotesSelection where = new QuotesSelection();
        String[] projection = QuotesColumns.ALL_COLUMNS;

        where.id(quoteID);

        QuotesCursor quoteCursor = where.query(context, projection);
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

    public static QuotesCursor getAllQuotesWithCategories(Context context) {
        QuotesSelection where;
        QuotesCursor quotesCursor;

        where = new QuotesSelection();
        String[] projection = {QuotesColumns._ID, QuotesColumns.CONTENT,QuotesColumns.CATEGORYID,CategoriesColumns.CATEGORY};
        quotesCursor = where.query(context.getContentResolver(), projection);

        return quotesCursor;

    }

    public static Boolean isQuoteFavorite(Context context, Long quoteId) {

        Boolean isFavorite = false;
        String[] projection = {StatusColumns.FAVORITE};
        StatusSelection where = new StatusSelection();

        StatusCursor statusCursor = where.quoteid(quoteId).query(context.getContentResolver(), projection);

        if (statusCursor != null && statusCursor.moveToNext()) {
            isFavorite = statusCursor.getFavorite();
        }
        return isFavorite;
    }

    public static Uri addToFavorites(long quoteID, Context context) {

        StatusContentValues contentValues = new StatusContentValues();
        contentValues.putQuoteid(quoteID)
                .putFavorite(true);

        Uri result = context.getContentResolver().insert(StatusColumns.CONTENT_URI, contentValues.values());
        Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();

        return result;


    }

    public static int removeFromFavorites(long quoteID, Context context) {

        StatusSelection where = new StatusSelection();

        int rowsDeleted = where.quoteid(quoteID).delete(context);

        Toast.makeText(context, "removed from Favorites", Toast.LENGTH_SHORT).show();

        return rowsDeleted;
    }


    public static Quote getRandomFavoriteQuote(Context context) {

        int favoritesCount = getFavoritesCount(context);
        Toast.makeText(context, "favorites quotes count is " + favoritesCount, Toast.LENGTH_SHORT).show();
        if (favoritesCount > 0) {

            Cursor c = context.getContentResolver().query(StatusColumns.CONTENT_URI,
                    null, null, null, "RANDOM() LIMIT 1");

            if (c != null && c.moveToNext()) {
                Long quoteID = Long.valueOf(c.getString(1));
                c.close();
                return getSpecificQuote(quoteID, context);
            }
        }
        return null;
    }

    public static int getFavoritesCount(Context context) {
        StatusSelection where = new StatusSelection();
        return where.count(context.getContentResolver());
    }

    public static CategoriesCursor getAllCategories(Context context){
        CategoriesSelection where;
        CategoriesCursor categoriesCursor;

        where = new CategoriesSelection();
        String[] projection = CategoriesColumns.ALL_COLUMNS;
        categoriesCursor = where.query(context.getContentResolver(), projection);

        return categoriesCursor;
    }

    public static QuotesCursor getQuotesForACategory(Context context,int categoryId){
        QuotesCursor quotesCursor ;
        QuotesSelection where = new QuotesSelection();

        where.categoryid(categoryId);
        String[] projection = {QuotesColumns._ID, QuotesColumns.CONTENT, QuotesColumns.CATEGORYID,CategoriesColumns.CATEGORY};
        quotesCursor = where.query(context,projection);

        return quotesCursor;

    }

    public static StatusCursor getFavoriteQuotes(Context context){
        StatusCursor statusCursor;
        StatusSelection where = new StatusSelection();

        where.favorite(true);
        String[] projection = {StatusColumns._ID,StatusColumns.QUOTEID,QuotesColumns.CONTENT,QuotesColumns.CATEGORYID,CategoriesColumns.CATEGORY};
        statusCursor = where.query(context,projection);
        return statusCursor;
    }

}














