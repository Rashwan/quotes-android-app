package com.android.people.quotesandroidapp.provider.status;

import android.net.Uri;
import android.provider.BaseColumns;

import com.android.people.quotesandroidapp.provider.QuotesProvider;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;

/**
 * Status for every quote 
 */
public class StatusColumns implements BaseColumns {
    public static final String TABLE_NAME = "status";
    public static final Uri CONTENT_URI = Uri.parse(QuotesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Id for the quote
     */
    public static final String QUOTEID = "quoteId";

    /**
     * Is the quote favorited?
     */
    public static final String FAVORITE = "favorite";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            QUOTEID,
            FAVORITE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(QUOTEID) || c.contains("." + QUOTEID)) return true;
            if (c.equals(FAVORITE) || c.contains("." + FAVORITE)) return true;
        }
        return false;
    }

    public static final String PREFIX_QUOTES = TABLE_NAME + "__" + QuotesColumns.TABLE_NAME;
}
