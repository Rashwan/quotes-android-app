package com.android.people.quotesandroidapp.provider.categories;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code categories} table.
 */
public class CategoriesCursor extends AbstractCursor implements CategoriesModel {
    public CategoriesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(CategoriesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Category for the quote
     * Can be {@code null}.
     */
    @Nullable
    public String getCategory() {
        String res = getStringOrNull(CategoriesColumns.CATEGORY);
        return res;
    }
}
