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
    private enum Type {
        QUOTES,
        CATEGORIES
    }
    private static final String QUOTESFILENAME = "quotes.csv";
    private static final String CATEGORIESFILENAME = "categories.csv";

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

        // Add CSV filed to the DB
        readCSVIntoTable(context,db,QUOTESFILENAME,Type.QUOTES);
        readCSVIntoTable(context,db,CATEGORIESFILENAME,Type.CATEGORIES);

    }

    public void onUpgrade(final Context context, final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);

        // Drop quotes table then recreate it and populate it from csv
        db.execSQL("DROP TABLE IF EXISTS " + QuotesColumns.TABLE_NAME);
        db.execSQL(QuotesSQLiteOpenHelper.SQL_CREATE_TABLE_QUOTES);
        readCSVIntoTable(context,db,QUOTESFILENAME,Type.QUOTES);
    }

    /**
     *
     * @param context applicatopn context
     * @param db  Database to operate on
     * @param fileName  The name of the CSV file in the assets folder
     * @param type  The table type either quotes or categories
     */
    private void readCSVIntoTable(Context context,SQLiteDatabase db,String fileName,Type type){
        AssetManager manager = context.getAssets();
        InputStream inStream;
        try {
            inStream = manager.open(fileName);

            BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
            String reader;
            db.beginTransaction();

                while ((reader = buffer.readLine()) != null){
                    String[] rowData = reader.split(",");
                    switch (type) {
                        case QUOTES:
                            QuotesContentValues quoteValues = new QuotesContentValues();
                            quoteValues.putContent(rowData[0]);
                            quoteValues.putCategoryid(Integer.valueOf(rowData[1]));
                            db.insert(QuotesColumns.TABLE_NAME, null, quoteValues.values());
                            break;
                        case CATEGORIES:
                            CategoriesContentValues categoryValues = new CategoriesContentValues();
                            categoryValues.putCategory(rowData[0]);
                            db.insert(CategoriesColumns.TABLE_NAME, null, categoryValues.values());
                            break;
                    }
                }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
