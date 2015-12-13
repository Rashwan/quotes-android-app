package com.android.people.quotesandroidapp.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.people.quotesandroidapp.BuildConfig;
import com.android.people.quotesandroidapp.provider.base.BaseContentProvider;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;

public class QuotesProvider extends BaseContentProvider {
    private static final String TAG = QuotesProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.android.people.quotesandroidapp.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_CATEGORIES = 0;
    private static final int URI_TYPE_CATEGORIES_ID = 1;

    private static final int URI_TYPE_QUOTES = 2;
    private static final int URI_TYPE_QUOTES_ID = 3;

    private static final int URI_TYPE_STATUS = 4;
    private static final int URI_TYPE_STATUS_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, CategoriesColumns.TABLE_NAME, URI_TYPE_CATEGORIES);
        URI_MATCHER.addURI(AUTHORITY, CategoriesColumns.TABLE_NAME + "/#", URI_TYPE_CATEGORIES_ID);
        URI_MATCHER.addURI(AUTHORITY, QuotesColumns.TABLE_NAME, URI_TYPE_QUOTES);
        URI_MATCHER.addURI(AUTHORITY, QuotesColumns.TABLE_NAME + "/#", URI_TYPE_QUOTES_ID);
        URI_MATCHER.addURI(AUTHORITY, StatusColumns.TABLE_NAME, URI_TYPE_STATUS);
        URI_MATCHER.addURI(AUTHORITY, StatusColumns.TABLE_NAME + "/#", URI_TYPE_STATUS_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return QuotesSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_CATEGORIES:
                return TYPE_CURSOR_DIR + CategoriesColumns.TABLE_NAME;
            case URI_TYPE_CATEGORIES_ID:
                return TYPE_CURSOR_ITEM + CategoriesColumns.TABLE_NAME;

            case URI_TYPE_QUOTES:
                return TYPE_CURSOR_DIR + QuotesColumns.TABLE_NAME;
            case URI_TYPE_QUOTES_ID:
                return TYPE_CURSOR_ITEM + QuotesColumns.TABLE_NAME;

            case URI_TYPE_STATUS:
                return TYPE_CURSOR_DIR + StatusColumns.TABLE_NAME;
            case URI_TYPE_STATUS_ID:
                return TYPE_CURSOR_ITEM + StatusColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_CATEGORIES:
            case URI_TYPE_CATEGORIES_ID:
                res.table = CategoriesColumns.TABLE_NAME;
                res.idColumn = CategoriesColumns._ID;
                res.tablesWithJoins = CategoriesColumns.TABLE_NAME;
                res.orderBy = CategoriesColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_QUOTES:
            case URI_TYPE_QUOTES_ID:
                res.table = QuotesColumns.TABLE_NAME;
                res.idColumn = QuotesColumns._ID;
                res.tablesWithJoins = QuotesColumns.TABLE_NAME;
                if (CategoriesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CategoriesColumns.TABLE_NAME + " AS " + QuotesColumns.PREFIX_CATEGORIES + " ON " + QuotesColumns.TABLE_NAME + "." + QuotesColumns.CATEGORYID + "=" + QuotesColumns.PREFIX_CATEGORIES + "." + CategoriesColumns._ID;
                }
                res.orderBy = QuotesColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_STATUS:
            case URI_TYPE_STATUS_ID:
                res.table = StatusColumns.TABLE_NAME;
                res.idColumn = StatusColumns._ID;
                res.tablesWithJoins = StatusColumns.TABLE_NAME;
                if (QuotesColumns.hasColumns(projection) || CategoriesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + QuotesColumns.TABLE_NAME + " AS " + StatusColumns.PREFIX_QUOTES + " ON " + StatusColumns.TABLE_NAME + "." + StatusColumns.QUOTEID + "=" + StatusColumns.PREFIX_QUOTES + "." + QuotesColumns._ID;
                }
                if (CategoriesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + CategoriesColumns.TABLE_NAME + " AS " + QuotesColumns.PREFIX_CATEGORIES + " ON " + StatusColumns.PREFIX_QUOTES + "." + QuotesColumns.CATEGORYID + "=" + QuotesColumns.PREFIX_CATEGORIES + "." + CategoriesColumns._ID;
                }
                res.orderBy = StatusColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_CATEGORIES_ID:
            case URI_TYPE_QUOTES_ID:
            case URI_TYPE_STATUS_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
