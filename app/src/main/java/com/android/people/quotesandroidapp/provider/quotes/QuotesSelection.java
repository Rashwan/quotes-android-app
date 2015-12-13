package com.android.people.quotesandroidapp.provider.quotes;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.people.quotesandroidapp.provider.base.AbstractSelection;
import com.android.people.quotesandroidapp.provider.categories.*;

/**
 * Selection for the {@code quotes} table.
 */
public class QuotesSelection extends AbstractSelection<QuotesSelection> {
    @Override
    protected Uri baseUri() {
        return QuotesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QuotesCursor} object, which is positioned before the first entry, or null.
     */
    public QuotesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QuotesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public QuotesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QuotesCursor} object, which is positioned before the first entry, or null.
     */
    public QuotesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QuotesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public QuotesCursor query(Context context) {
        return query(context, null);
    }


    public QuotesSelection id(long... value) {
        addEquals("quotes." + QuotesColumns._ID, toObjectArray(value));
        return this;
    }

    public QuotesSelection idNot(long... value) {
        addNotEquals("quotes." + QuotesColumns._ID, toObjectArray(value));
        return this;
    }

    public QuotesSelection orderById(boolean desc) {
        orderBy("quotes." + QuotesColumns._ID, desc);
        return this;
    }

    public QuotesSelection orderById() {
        return orderById(false);
    }

    public QuotesSelection content(String... value) {
        addEquals(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesSelection contentNot(String... value) {
        addNotEquals(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesSelection contentLike(String... value) {
        addLike(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesSelection contentContains(String... value) {
        addContains(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesSelection contentStartsWith(String... value) {
        addStartsWith(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesSelection contentEndsWith(String... value) {
        addEndsWith(QuotesColumns.CONTENT, value);
        return this;
    }

    public QuotesSelection orderByContent(boolean desc) {
        orderBy(QuotesColumns.CONTENT, desc);
        return this;
    }

    public QuotesSelection orderByContent() {
        orderBy(QuotesColumns.CONTENT, false);
        return this;
    }

    public QuotesSelection categoryid(int... value) {
        addEquals(QuotesColumns.CATEGORYID, toObjectArray(value));
        return this;
    }

    public QuotesSelection categoryidNot(int... value) {
        addNotEquals(QuotesColumns.CATEGORYID, toObjectArray(value));
        return this;
    }

    public QuotesSelection categoryidGt(int value) {
        addGreaterThan(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public QuotesSelection categoryidGtEq(int value) {
        addGreaterThanOrEquals(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public QuotesSelection categoryidLt(int value) {
        addLessThan(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public QuotesSelection categoryidLtEq(int value) {
        addLessThanOrEquals(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public QuotesSelection orderByCategoryid(boolean desc) {
        orderBy(QuotesColumns.CATEGORYID, desc);
        return this;
    }

    public QuotesSelection orderByCategoryid() {
        orderBy(QuotesColumns.CATEGORYID, false);
        return this;
    }

    public QuotesSelection categoriesCategory(String... value) {
        addEquals(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public QuotesSelection categoriesCategoryNot(String... value) {
        addNotEquals(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public QuotesSelection categoriesCategoryLike(String... value) {
        addLike(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public QuotesSelection categoriesCategoryContains(String... value) {
        addContains(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public QuotesSelection categoriesCategoryStartsWith(String... value) {
        addStartsWith(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public QuotesSelection categoriesCategoryEndsWith(String... value) {
        addEndsWith(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public QuotesSelection orderByCategoriesCategory(boolean desc) {
        orderBy(CategoriesColumns.CATEGORY, desc);
        return this;
    }

    public QuotesSelection orderByCategoriesCategory() {
        orderBy(CategoriesColumns.CATEGORY, false);
        return this;
    }
}
