package com.android.people.quotesandroidapp.provider.categories;

import com.android.people.quotesandroidapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A Category for every quote 
 */
public interface CategoriesModel extends BaseModel {

    /**
     * Category for the quote
     * Can be {@code null}.
     */
    @Nullable
    String getCategory();
}
