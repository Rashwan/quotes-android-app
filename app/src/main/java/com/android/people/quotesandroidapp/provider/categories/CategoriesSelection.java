package com.android.people.quotesandroidapp.provider.categories;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.people.quotesandroidapp.provider.base.AbstractSelection;

/**
 * Selection for the {@code categories} table.
 */
public class CategoriesSelection extends AbstractSelection<CategoriesSelection> {
    @Override
    protected Uri baseUri() {
        return CategoriesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CategoriesCursor} object, which is positioned before the first entry, or null.
     */
    public CategoriesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CategoriesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public CategoriesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code CategoriesCursor} object, which is positioned before the first entry, or null.
     */
    public CategoriesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new CategoriesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public CategoriesCursor query(Context context) {
        return query(context, null);
    }


    public CategoriesSelection id(long... value) {
        addEquals("categories." + CategoriesColumns._ID, toObjectArray(value));
        return this;
    }

    public CategoriesSelection idNot(long... value) {
        addNotEquals("categories." + CategoriesColumns._ID, toObjectArray(value));
        return this;
    }

    public CategoriesSelection orderById(boolean desc) {
        orderBy("categories." + CategoriesColumns._ID, desc);
        return this;
    }

    public CategoriesSelection orderById() {
        return orderById(false);
    }

    public CategoriesSelection category(String... value) {
        addEquals(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesSelection categoryNot(String... value) {
        addNotEquals(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesSelection categoryLike(String... value) {
        addLike(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesSelection categoryContains(String... value) {
        addContains(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesSelection categoryStartsWith(String... value) {
        addStartsWith(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesSelection categoryEndsWith(String... value) {
        addEndsWith(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public CategoriesSelection orderByCategory(boolean desc) {
        orderBy(CategoriesColumns.CATEGORY, desc);
        return this;
    }

    public CategoriesSelection orderByCategory() {
        orderBy(CategoriesColumns.CATEGORY, false);
        return this;
    }
}
