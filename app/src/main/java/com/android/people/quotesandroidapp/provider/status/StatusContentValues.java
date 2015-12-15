package com.android.people.quotesandroidapp.provider.status;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code status} table.
 */
public class StatusContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return StatusColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable StatusSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable StatusSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Id for the quote
     */
    public StatusContentValues putQuoteid(long value) {
        mContentValues.put(StatusColumns.QUOTEID, value);
        return this;
    }


    /**
     * Is the quote favorited?
     */
    public StatusContentValues putFavorite(@Nullable Boolean value) {
        mContentValues.put(StatusColumns.FAVORITE, value);
        return this;
    }

    public StatusContentValues putFavoriteNull() {
        mContentValues.putNull(StatusColumns.FAVORITE);
        return this;
    }
}
