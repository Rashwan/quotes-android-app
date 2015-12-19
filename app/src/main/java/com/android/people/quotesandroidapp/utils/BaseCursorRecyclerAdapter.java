package com.android.people.quotesandroidapp.utils;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 ARNAUD FRUGIER
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;

import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;

/**
 * Created by rashwan on 12/14/15.
 */
public abstract class BaseCursorRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    protected boolean mDataValid;
    protected Cursor mCursor;
    protected int mRowIDColumn;
    protected int mRowCategoryIDColumn;

    public BaseCursorRecyclerAdapter(Cursor c) {
        init(c);
    }

    private void init(Cursor c) {
        boolean cursorPresent = c != null;
        mCursor = c;
        mDataValid = cursorPresent;
        mRowIDColumn = cursorPresent ? c.getColumnIndex("_id") : -1;
        mRowCategoryIDColumn = cursorPresent ? c.getColumnIndex(QuotesColumns.CATEGORYID) : -1;

        setHasStableIds(true);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }

        onBindViewHolder(holder, mCursor);
    }

    public abstract void onBindViewHolder(VH holder, Cursor cursor) ;

    public Cursor getCursor(){return mCursor;}

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        }else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        if (hasStableIds() && mDataValid && mCursor != null){
            if (mCursor.moveToPosition(position)){
                return mCursor.getLong(mRowIDColumn);
            }else {
                return RecyclerView.NO_ID;
            }
        }else {
            return RecyclerView.NO_ID;
        }
    }

    //get the correct quote Id depending on the type of the view (Quotes,Categories,Favorites)
    public int getSpecialId(int position,String column) {

        if (hasStableIds() && mDataValid && mCursor != null){
            if (mCursor.moveToPosition(position)){
                return mCursor.getInt(mCursor.getColumnIndex(column));
            }else {
                return (int)RecyclerView.NO_ID;
            }
        }else {
            return (int)RecyclerView.NO_ID;
        }
    }

    /**
     * Converts the cursor into a CharSequence. Subclasses should override this
     * method to convert their results. The default implementation returns an
     * empty String for null values or the default String representation of
     * the value.
     *
     * @param cursor the cursor to convert to a CharSequence
     * @return a CharSequence representing the value
     */
    public CharSequence convertToString(Cursor cursor) {
        return cursor == null ? "" : cursor.toString();
    }
}
