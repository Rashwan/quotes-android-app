package com.android.people.quotesandroidapp.provider.categories;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code categories} table.
 */
public class CategoriesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return CategoriesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable CategoriesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable CategoriesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Category for the quote
     */
    public CategoriesContentValues putCategory(@Nullable String value) {
        mContentValues.put(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesContentValues putCategoryNull() {
        mContentValues.putNull(CategoriesColumns.CATEGORY);
        return this;
    }
}
