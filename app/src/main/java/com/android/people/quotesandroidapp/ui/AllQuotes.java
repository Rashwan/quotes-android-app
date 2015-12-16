package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.adapters.AllQuotesRecyclerAdapter;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;
import com.android.people.quotesandroidapp.utils.QuoteClickListener;

/**
 * Created by rashwan on 12/14/15.
 */
public class AllQuotes extends Fragment {

    QuotesCursor quotesCursor;
    QuoteClickListener mListener;


    public AllQuotes(){}

    public static AllQuotes newInstance() {

        Bundle args = new Bundle();

        AllQuotes fragment = new AllQuotes();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Query DB for all available quotes
        quotesCursor = DatabaseUtils.getAllQuotesWithCategories(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_quotes,container,false);

        RecyclerView rvAllQuotes = (RecyclerView) rootView.findViewById(R.id.rv_all_quotes);
        final AllQuotesRecyclerAdapter rvAdapter = new AllQuotesRecyclerAdapter(getActivity(),quotesCursor);

        rvAllQuotes.setAdapter(rvAdapter);
        rvAllQuotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        //To Improve Performance
        rvAllQuotes.setHasFixedSize(true);
        //To Disable blink animation in notifyItemChanged
        rvAllQuotes.getItemAnimator().setChangeDuration(0);

        //Implement behavior of each clicked Item
        rvAdapter.setOnCardClickListener(new AllQuotesRecyclerAdapter.OnCardClickListener() {
            //Favorite button behavior
            @Override
            public void onFavoriteClicked(Button fav, int position) {
                Long quoteId = rvAdapter.getItemId(position);

                //If the quote is already favorited remove it otherwise add it
                if (DatabaseUtils.isQuoteFavorite(getActivity(), quoteId)) {
                    DatabaseUtils.removeFromFavorites(quoteId, getActivity());
                } else {
                    DatabaseUtils.addToFavorites(quoteId, getActivity());
                }
                rvAdapter.notifyItemChanged(position);
            }

            //Card content behavior
            @Override
            public void onContentClicked(int position) {
                if (mListener != null){
                    Long quoteId = rvAdapter.getItemId(position);
                    mListener.onQuoteClicked(quoteId);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Attach listener from the activity
        if (context instanceof QuoteClickListener) {
            mListener = (QuoteClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCardContentClickedListener");
        }
    }

}
