package com.android.people.quotesandroidapp.provider.quotes;

import android.net.Uri;
import android.provider.BaseColumns;

import com.android.people.quotesandroidapp.provider.QuotesProvider;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;

/**
 * A categorized quote 
 */
public class QuotesColumns implements BaseColumns {
    public static final String TABLE_NAME = "quotes";
    public static final Uri CONTENT_URI = Uri.parse(QuotesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Content of the quote
     */
    public static final String CONTENT = "content";

    /**
     * Category ID
     */
    public static final String CATEGORYID = "categoryId";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CONTENT,
            CATEGORYID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CONTENT) || c.contains("." + CONTENT)) return true;
            if (c.equals(CATEGORYID) || c.contains("." + CATEGORYID)) return true;
        }
        return false;
    }

    public static final String PREFIX_CATEGORIES = TABLE_NAME + "__" + CategoriesColumns.TABLE_NAME;
}
