package com.android.people.quotesandroidapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;
import com.android.people.quotesandroidapp.utils.QuotesCursorRecyclerAdapter;

/**
 * Created by rashwan on 12/14/15.
 */
public class AllQuotesRecyclerAdapter extends QuotesCursorRecyclerAdapter<AllQuotesRecyclerAdapter.SimpleViewHolder>{

    Context mContext;
    public AllQuotesRecyclerAdapter(Context context,QuotesCursor c){
        super(c);
        mContext = context;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, QuotesCursor cursor) {

        String quoteContent = cursor.getContent();
        String quoteCategory = cursor.getCategoriesCategory();
        Boolean quoteFavorite;
        Long quoteId = cursor.getId();

        quoteFavorite = DatabaseUtils.isQuoteFavorite(mContext,quoteId);

        TextView cardContent  =holder.cardContent;
        TextView cardCategory = holder.cardCategory;
        Button cardFavorite = holder.cardFavorite;

        cardContent.setText(quoteContent);
        cardCategory.setText(quoteCategory);
        cardFavorite.setPressed(quoteFavorite);
    }



    @Override
    public AllQuotesRecyclerAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.home_first_category_card, parent, false);

        // Return a new holder instance
        return new SimpleViewHolder(contactView);
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView cardCategory;
        public TextView cardContent;
        public Button cardFavorite;

        public SimpleViewHolder(View itemView) {
            super(itemView);

            cardCategory = (TextView) itemView.findViewById(R.id.first_quote_title);
            cardContent = (TextView) itemView.findViewById(R.id.first_quote_body);
            cardFavorite = (Button) itemView.findViewById(R.id.first_fav);
        }
    }
}


