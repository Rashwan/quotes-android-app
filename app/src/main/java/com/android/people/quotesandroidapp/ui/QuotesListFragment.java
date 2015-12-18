package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.os.Bundle;
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
public class QuotesListFragment extends Fragment {

    private static final String TAG = QuotesListFragment.class.getSimpleName();
    private QuotesCursor quotesCursor;
    private QuoteClickListener mListener;
    public static final int TYPE_QUOTES = 1;
    public static final int TYPE_CATEGORIES = 2;



    public QuotesListFragment(){}

    public static QuotesListFragment newInstance() {

        return new QuotesListFragment();
    }

    public static QuotesListFragment newInstance(int type,int categoryId) {

        Bundle args = new Bundle();
        QuotesListFragment fragment = new QuotesListFragment();
        args.putInt("Type",type);
        args.putInt("CatId",categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){

            if (getArguments().getInt("Type") == TYPE_CATEGORIES && getArguments().containsKey("CatId")){
                quotesCursor = DatabaseUtils.getQuotesForACategory(getActivity(),getArguments().getInt("CatId", 1));
            }
        }else {
            quotesCursor = DatabaseUtils.getAllQuotesWithCategories(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quotes_list,container,false);

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
