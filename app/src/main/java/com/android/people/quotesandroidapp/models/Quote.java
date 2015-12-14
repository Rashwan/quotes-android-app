package com.android.people.quotesandroidapp.models;

/**
 * Created by amrelmasry on 12/14/15.
 */
public class Quote {
    private long id;
    private String content;
    private long categoryID;

    public Quote(long id, String content, long categoryID) {
        this.id = id;
        this.content = content;
        this.categoryID = categoryID;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}

