package com.android.people.quotesandroidapp.provider.categories;

import android.net.Uri;
import android.provider.BaseColumns;

import com.android.people.quotesandroidapp.provider.QuotesProvider;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;

/**
 * A Category for every quote 
 */
public class CategoriesColumns implements BaseColumns {
    public static final String TABLE_NAME = "categories";
    public static final Uri CONTENT_URI = Uri.parse(QuotesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Category for the quote
     */
    public static final String CATEGORY = "category";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CATEGORY
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CATEGORY) || c.contains("." + CATEGORY)) return true;
        }
        return false;
    }

}
