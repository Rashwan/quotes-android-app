package com.android.people.quotesandroidapp.provider.quotes;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code quotes} table.
 */
public class QuotesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return QuotesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable QuotesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable QuotesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Content of the quote
     */
    public QuotesContentValues putContent(@Nullable String value) {
        mContentValues.put(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesContentValues putContentNull() {
        mContentValues.putNull(QuotesColumns.CONTENT);
        return this;
    }

    /**
     * Category ID
     */
    public QuotesContentValues putCategoryid(int value) {
        mContentValues.put(QuotesColumns.CATEGORYID, value);
        return this;
    }

}
