package com.android.people.quotesandroidapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        TextView cardContent  =holder.cardContent;
        TextView cardCategory = holder.cardCategory;
        Button cardFavorite = holder.cardFavorite;

        //Check if the quote is favorite to set the Button state
        quoteFavorite = DatabaseUtils.isQuoteFavorite(mContext,quoteId);

        cardContent.setText(quoteContent);
        cardCategory.setText(quoteCategory);
        cardFavorite.setText(quoteFavorite + "");
    }



    @Override
    public AllQuotesRecyclerAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.home_first_category_card, parent, false);

        //Return a new ViewHolder With implementation for each item's in the card click listener
        SimpleViewHolder VH = new SimpleViewHolder(contactView, new SimpleViewHolder.VHClicks() {
            @Override
            public void onFavorite(Button fav,int position) {
                Long quoteId  =getItemId(position);

                //If the quote is already favorited remove it otherwise add it
                if (DatabaseUtils.isQuoteFavorite(context,quoteId)){
                    DatabaseUtils.removeFromFavorites(quoteId,context);
                }else {
                    DatabaseUtils.addToFavorites(quoteId, context);
                }
                notifyItemChanged(position);
            }
        });
        return VH;
    }


    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView cardCategory;
        public TextView cardContent;
        public Button cardFavorite;
        public VHClicks mListener;

        public SimpleViewHolder(View itemView,VHClicks listener) {
            super(itemView);
            mListener = listener;

            cardCategory = (TextView) itemView.findViewById(R.id.first_quote_title);
            cardContent = (TextView) itemView.findViewById(R.id.first_quote_body);
            cardFavorite = (Button) itemView.findViewById(R.id.first_fav);
            cardFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v instanceof Button){
                mListener.onFavorite((Button) v,getLayoutPosition());
            }else {
                Log.d("CLICK","ONTHECARD");
            }
        }
        public interface VHClicks{
             void onFavorite(Button fav,int position);
        }
    }
}


