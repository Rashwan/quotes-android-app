package com.android.people.quotesandroidapp.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.android.people.quotesandroidapp.BuildConfig;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;

public class QuotesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = QuotesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "quotesandroid.db";
    private static final int DATABASE_VERSION = 1;
    private static QuotesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final QuotesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_CATEGORIES = "CREATE TABLE IF NOT EXISTS "
            + CategoriesColumns.TABLE_NAME + " ( "
            + CategoriesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CategoriesColumns.CATEGORY + " TEXT DEFAULT 'default Value for category' "
            + " );";

    public static final String SQL_CREATE_TABLE_QUOTES = "CREATE TABLE IF NOT EXISTS "
            + QuotesColumns.TABLE_NAME + " ( "
            + QuotesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QuotesColumns.CONTENT + " TEXT DEFAULT 'default Value for quote content', "
            + QuotesColumns.CATEGORYID + " INTEGER NOT NULL "
            + ", CONSTRAINT fk_categoryid FOREIGN KEY (" + QuotesColumns.CATEGORYID + ") REFERENCES categories (_id) ON DELETE CASCADE"
            + " );";

    public static final String SQL_CREATE_TABLE_STATUS = "CREATE TABLE IF NOT EXISTS "
            + StatusColumns.TABLE_NAME + " ( "
            + StatusColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StatusColumns.QUOTEID + " INTEGER NOT NULL, "
            + StatusColumns.FAVORITE + " INTEGER DEFAULT 1 "
            + ", CONSTRAINT fk_quoteid FOREIGN KEY (" + StatusColumns.QUOTEID + ") REFERENCES quotes (_id) ON DELETE CASCADE"
            + " );";

    // @formatter:on

    public static QuotesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static QuotesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static QuotesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new QuotesSQLiteOpenHelper(context);
    }

    private QuotesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new QuotesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static QuotesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new QuotesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private QuotesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new QuotesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIES);
        db.execSQL(SQL_CREATE_TABLE_QUOTES);
        db.execSQL(SQL_CREATE_TABLE_STATUS);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
