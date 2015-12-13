package com.android.people.quotesandroidapp.provider;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.people.quotesandroidapp.BuildConfig;
import com.android.people.quotesandroidapp.provider.categories.CategoriesColumns;
import com.android.people.quotesandroidapp.provider.categories.CategoriesContentValues;
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.quotes.QuotesContentValues;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;
import com.android.people.quotesandroidapp.provider.status.StatusContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Implement your custom database creation or upgrade code here.
 *
 * This file will not be overwritten if you re-run the content provider generator.
 */
public class QuotesSQLiteOpenHelperCallbacks {
    private static final String TAG = QuotesSQLiteOpenHelperCallbacks.class.getSimpleName();

    public void onOpen(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onOpen");
        // Insert your db open code here.
    }

    public void onPreCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPreCreate");
        // Insert your db creation code here. This is called before your tables are created.
    }

    public void onPostCreate(final Context context, final SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onPostCreate");
        // Insert your db creation code here. This is called after your tables are created.

        CategoriesContentValues [] categoriesValues  = new CategoriesContentValues[2];
        categoriesValues[0] = new CategoriesContentValues().putCategory("تربية");
        categoriesValues[1] = new CategoriesContentValues().putCategory("ثقافة");
        for (CategoriesContentValues categoriesValue : categoriesValues) {
            db.insert(CategoriesColumns.TABLE_NAME, null, categoriesValue.values());
        }

        readCSVIntoTable(context,db);

        StatusContentValues statusContentValues = new StatusContentValues();
        statusContentValues.putQuoteid(1);
        statusContentValues.putFavorite(true);
        db.insert(StatusColumns.TABLE_NAME,null,statusContentValues.values());

    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        // Insert your upgrading code here.
        db.execSQL("DROP TABLE IF EXISTS " + QuotesColumns.TABLE_NAME);
        db.execSQL(QuotesSQLiteOpenHelper.SQL_CREATE_TABLE_QUOTES);
        readCSVIntoTable(context,db);
    }

    private void readCSVIntoTable(Context context,SQLiteDatabase db){
        String mCSVfile = "quotes.csv";
        AssetManager manager = context.getAssets();
        InputStream inStream = null;
        try {
            inStream = manager.open(mCSVfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String reader = "";
        db.beginTransaction();

        try {
            while ((reader = buffer.readLine()) != null){
                String[] rowData = reader.split(",");

                QuotesContentValues values = new QuotesContentValues();
                values.putContent(rowData[0]);
                values.putCategoryid(Integer.valueOf(rowData[1]));
                db.insert(QuotesColumns.TABLE_NAME,null,values.values());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
