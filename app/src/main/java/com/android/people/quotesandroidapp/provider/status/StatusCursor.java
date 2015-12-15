package com.android.people.quotesandroidapp.provider.status;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.AbstractCursor;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;

/**
 * Cursor wrapper for the {@code status} table.
 */
public class StatusCursor extends AbstractCursor implements StatusModel {
    public StatusCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(StatusColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Id for the quote
     */
    public long getQuoteid() {
        Long res = getLongOrNull(StatusColumns.QUOTEID);
        if (res == null)
            throw new NullPointerException("The value of 'quoteid' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Content of the quote
     * Can be {@code null}.
     */
    @Nullable
    public String getQuotesContent() {
        String res = getStringOrNull(QuotesColumns.CONTENT);
        return res;
    }

    /**
     * Category ID
     */
    public int getQuotesCategoryid() {
        Integer res = getIntegerOrNull(QuotesColumns.CATEGORYID);
        if (res == null)
            throw new NullPointerException("The value of 'categoryid' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Category for the quote
     * Can be {@code null}.
     */
    @Nullable
    public String getQuotesCategoriesCategory() {
        String res = getStringOrNull(CategoriesColumns.CATEGORY);
        return res;
    }

    /**
     * Is the quote favorited?
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getFavorite() {
        Boolean res = getBooleanOrNull(StatusColumns.FAVORITE);
        return res;
    }
}
