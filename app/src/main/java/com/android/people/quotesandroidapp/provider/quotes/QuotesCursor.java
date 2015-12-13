package com.android.people.quotesandroidapp.provider.quotes;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.AbstractCursor;
import com.android.people.quotesandroidapp.provider.categories.*;

/**
 * Cursor wrapper for the {@code quotes} table.
 */
public class QuotesCursor extends AbstractCursor implements QuotesModel {
    public QuotesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(QuotesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Content of the quote
     * Can be {@code null}.
     */
    @Nullable
    public String getContent() {
        String res = getStringOrNull(QuotesColumns.CONTENT);
        return res;
    }

    /**
     * Category ID
     */
    public int getCategoryid() {
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
    public String getCategoriesCategory() {
        String res = getStringOrNull(CategoriesColumns.CATEGORY);
        return res;
    }
}
