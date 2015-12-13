package com.android.people.quotesandroidapp.provider.status;

import com.android.people.quotesandroidapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Status for every quote 
 */
public interface StatusModel extends BaseModel {

    /**
     * Id for the quote
     */
    int getQuoteid();

    /**
     * Is the quote favorited?
     * Can be {@code null}.
     */
    @Nullable
    Boolean getFavorite();
}
