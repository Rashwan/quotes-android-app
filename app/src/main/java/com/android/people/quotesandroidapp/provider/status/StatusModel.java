package com.android.people.quotesandroidapp.provider.status;

import android.support.annotation.Nullable;

import com.android.people.quotesandroidapp.provider.base.BaseModel;

/**
 * Status for every quote 
 */
public interface StatusModel extends BaseModel {

    /**
     * Id for the quote
     */
    long getQuoteid();

    /**
     * Is the quote favorited?
     * Can be {@code null}.
     */
    @Nullable
    Boolean getFavorite();
}
