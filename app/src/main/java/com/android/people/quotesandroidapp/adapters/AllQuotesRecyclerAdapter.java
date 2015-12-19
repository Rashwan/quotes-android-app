package com.android.people.quotesandroidapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.utils.BaseCursorRecyclerAdapter;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 12/14/15.
 */
public class AllQuotesRecyclerAdapter extends BaseCursorRecyclerAdapter<AllQuotesRecyclerAdapter.SimpleViewHolder> {

    public Context mContext;

    private static OnCardClickListener mListener;


    public interface OnCardClickListener{
        void onFavoriteClicked(Button fav,int position);
        void onContentClicked(int position);
        void onCardCategoryClicked(int position);
    }


    public void setOnCardClickListener(OnCardClickListener listener){
        mListener = listener;
    }

    public AllQuotesRecyclerAdapter(Context context,QuotesCursor c){
        super(c);
        mContext = context;
    }

    @Override
    public AllQuotesRecyclerAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contactView = inflater.inflate(R.layout.home_first_category_card, parent, false);

        return new SimpleViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, Cursor cursor) {
        QuotesCursor quotesCursor = (QuotesCursor) cursor;
        String quoteContent = quotesCursor.getContent();
        String quoteCategory = quotesCursor.getCategoriesCategory();
        Boolean quoteFavorite;
        Long quoteId = quotesCursor.getId();

        //Check if the quote is favorite to set the Button state
        quoteFavorite = DatabaseUtils.isQuoteFavorite(mContext,quoteId);

        holder.cardContent.setText(quoteContent);
        holder.cardCategory.setText(quoteCategory);
        holder.cardFavorite.setText(quoteFavorite.toString());
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.first_quote_category) TextView cardCategory;
        @Bind(R.id.first_quote_body) TextView cardContent;
        @Bind(R.id.first_fav) Button cardFavorite;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //Set OnClickListeners for each item in the card
            cardFavorite.setOnClickListener(this);
            itemView.setOnClickListener(this);
            cardCategory.setOnClickListener(this);
        }

        //call the listener method based on the type of the clicked item
        @Override
        public void onClick(View v) {
            if (mListener != null) {

                if (v instanceof Button) {
                    mListener.onFavoriteClicked((Button) v, getLayoutPosition());
                } else if (v instanceof TextView) {
                    mListener.onCardCategoryClicked(getLayoutPosition());
                } else if (v instanceof CardView) {
                    mListener.onContentClicked(getLayoutPosition());
                }
            }
        }
    }
}


