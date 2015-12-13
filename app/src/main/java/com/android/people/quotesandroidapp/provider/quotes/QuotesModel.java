package com.android.people.quotesandroidapp.provider.quotes;

import com.android.people.quotesandroidapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A categorized quote 
 */
public interface QuotesModel extends BaseModel {

    /**
     * Content of the quote
     * Can be {@code null}.
     */
    @Nullable
    String getContent();

    /**
     * Category ID
     */
    int getCategoryid();
}
