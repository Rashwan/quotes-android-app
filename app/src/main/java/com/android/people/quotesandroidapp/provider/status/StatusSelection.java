package com.android.people.quotesandroidapp.provider.status;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.android.people.quotesandroidapp.provider.base.AbstractSelection;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;

/**
 * Selection for the {@code status} table.
 */
public class StatusSelection extends AbstractSelection<StatusSelection> {
    @Override
    protected Uri baseUri() {
        return StatusColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StatusCursor} object, which is positioned before the first entry, or null.
     */
    public StatusCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StatusCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public StatusCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code StatusCursor} object, which is positioned before the first entry, or null.
     */
    public StatusCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new StatusCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public StatusCursor query(Context context) {
        return query(context, null);
    }


    public StatusSelection id(long... value) {
        addEquals("status." + StatusColumns._ID, toObjectArray(value));
        return this;
    }

    public StatusSelection idNot(long... value) {
        addNotEquals("status." + StatusColumns._ID, toObjectArray(value));
        return this;
    }

    public StatusSelection orderById(boolean desc) {
        orderBy("status." + StatusColumns._ID, desc);
        return this;
    }

    public StatusSelection orderById() {
        return orderById(false);
    }

    public StatusSelection quoteid(long... value) {
        addEquals(StatusColumns.QUOTEID, toObjectArray(value));
        return this;
    }

    public StatusSelection quoteidNot(long... value) {
        addNotEquals(StatusColumns.QUOTEID, toObjectArray(value));
        return this;
    }

    public StatusSelection quoteidGt(long value) {
        addGreaterThan(StatusColumns.QUOTEID, value);
        return this;
    }

    public StatusSelection quoteidGtEq(long value) {
        addGreaterThanOrEquals(StatusColumns.QUOTEID, value);
        return this;
    }

    public StatusSelection quoteidLt(long value) {
        addLessThan(StatusColumns.QUOTEID, value);
        return this;
    }

    public StatusSelection quoteidLtEq(long value) {
        addLessThanOrEquals(StatusColumns.QUOTEID, value);
        return this;
    }

    public StatusSelection orderByQuoteid(boolean desc) {
        orderBy(StatusColumns.QUOTEID, desc);
        return this;
    }

    public StatusSelection orderByQuoteid() {
        orderBy(StatusColumns.QUOTEID, false);
        return this;
    }

    public StatusSelection quotesContent(String... value) {
        addEquals(QuotesColumns.CONTENT, value);
        return this;
    }

    public StatusSelection quotesContentNot(String... value) {
        addNotEquals(QuotesColumns.CONTENT, value);
        return this;
    }

    public StatusSelection quotesContentLike(String... value) {
        addLike(QuotesColumns.CONTENT, value);
        return this;
    }

    public StatusSelection quotesContentContains(String... value) {
        addContains(QuotesColumns.CONTENT, value);
        return this;
    }

    public StatusSelection quotesContentStartsWith(String... value) {
        addStartsWith(QuotesColumns.CONTENT, value);
        return this;
    }

    public StatusSelection quotesContentEndsWith(String... value) {
        addEndsWith(QuotesColumns.CONTENT, value);
        return this;
    }

    public StatusSelection orderByQuotesContent(boolean desc) {
        orderBy(QuotesColumns.CONTENT, desc);
        return this;
    }

    public StatusSelection orderByQuotesContent() {
        orderBy(QuotesColumns.CONTENT, false);
        return this;
    }

    public StatusSelection quotesCategoryid(int... value) {
        addEquals(QuotesColumns.CATEGORYID, toObjectArray(value));
        return this;
    }

    public StatusSelection quotesCategoryidNot(int... value) {
        addNotEquals(QuotesColumns.CATEGORYID, toObjectArray(value));
        return this;
    }

    public StatusSelection quotesCategoryidGt(int value) {
        addGreaterThan(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public StatusSelection quotesCategoryidGtEq(int value) {
        addGreaterThanOrEquals(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public StatusSelection quotesCategoryidLt(int value) {
        addLessThan(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public StatusSelection quotesCategoryidLtEq(int value) {
        addLessThanOrEquals(QuotesColumns.CATEGORYID, value);
        return this;
    }

    public StatusSelection orderByQuotesCategoryid(boolean desc) {
        orderBy(QuotesColumns.CATEGORYID, desc);
        return this;
    }

    public StatusSelection orderByQuotesCategoryid() {
        orderBy(QuotesColumns.CATEGORYID, false);
        return this;
    }

    public StatusSelection quotesCategoriesCategory(String... value) {
        addEquals(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public StatusSelection quotesCategoriesCategoryNot(String... value) {
        addNotEquals(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public StatusSelection quotesCategoriesCategoryLike(String... value) {
        addLike(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public StatusSelection quotesCategoriesCategoryContains(String... value) {
        addContains(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public StatusSelection quotesCategoriesCategoryStartsWith(String... value) {
        addStartsWith(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public StatusSelection quotesCategoriesCategoryEndsWith(String... value) {
        addEndsWith(CategoriesColumns.CATEGORY, value);
        return this;
    }

    public StatusSelection orderByQuotesCategoriesCategory(boolean desc) {
        orderBy(CategoriesColumns.CATEGORY, desc);
        return this;
    }

    public StatusSelection orderByQuotesCategoriesCategory() {
        orderBy(CategoriesColumns.CATEGORY, false);
        return this;
    }

    public StatusSelection favorite(Boolean value) {
        addEquals(StatusColumns.FAVORITE, toObjectArray(value));
        return this;
    }

    public StatusSelection orderByFavorite(boolean desc) {
        orderBy(StatusColumns.FAVORITE, desc);
        return this;
    }

    public StatusSelection orderByFavorite() {
        orderBy(StatusColumns.FAVORITE, false);
        return this;
    }
}
