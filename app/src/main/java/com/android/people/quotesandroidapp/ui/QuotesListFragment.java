package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.database.Cursor;
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
import com.android.people.quotesandroidapp.provider.quotes.QuotesColumns;
import com.android.people.quotesandroidapp.provider.status.StatusColumns;
import com.android.people.quotesandroidapp.utils.CategoryClickListener;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;
import com.android.people.quotesandroidapp.utils.QuoteClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rashwan on 12/14/15.
 */
public class QuotesListFragment extends Fragment {

    private static final String TAG = QuotesListFragment.class.getSimpleName();
    public static final int TYPE_QUOTES = 1;
    public static final int TYPE_CATEGORIES = 2;
    public static final int TYPE_FAVORITES = 3;
    private Cursor quotesCursor;
    private QuoteClickListener mQuoteListener;
    private CategoryClickListener mCategoryListener;
    @Bind(R.id.rv_all_quotes) RecyclerView rvAllQuotes;
    AllQuotesRecyclerAdapter rvAdapter = null;


    public QuotesListFragment(){}

    public static QuotesListFragment newInstance(int type) {
        Bundle args = new Bundle();
        QuotesListFragment fragment = new QuotesListFragment();
        args.putInt("Type", type);
        fragment.setArguments(args);
        return fragment;
    }

    public static QuotesListFragment newInstance(int type,int categoryId) {

        Bundle args = new Bundle();
        QuotesListFragment fragment = new QuotesListFragment();
        args.putInt("Type", type);
        args.putInt("CatId", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rvAdapter = new AllQuotesRecyclerAdapter(getActivity(),initCursors());
    }

    @Override
    public void onResume() {
        super.onResume();
        implementListeners();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quotes_list, container,false);

        ButterKnife.bind(this, rootView);
        rvAllQuotes.setAdapter(rvAdapter);

        rvAllQuotes.setLayoutManager(new LinearLayoutManager(getActivity()));
        //To Improve Performance
        rvAllQuotes.setHasFixedSize(true);
        //To Disable blink animation in notifyItemChanged
        rvAllQuotes.getItemAnimator().setChangeDuration(0);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Attach listener from the activity
        if (context instanceof QuoteClickListener) {
            mQuoteListener = (QuoteClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement QuoteClickListener");
        }
        if (context instanceof CategoryClickListener){
            mCategoryListener = (CategoryClickListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement CategoryClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCategoryListener = null;
        mQuoteListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private Cursor initCursors(){
        if (getArguments() != null) {

            //If the request is from CategoriesFragment to show all quotes of a specific category
            if (getArguments().getInt("Type") == TYPE_CATEGORIES && getArguments().containsKey("CatId")) {
                quotesCursor = DatabaseUtils.getQuotesForACategory(getActivity(), getArguments().getInt("CatId", 1));

            //If the request is to show all quotes from all categories
            } else if(getArguments().getInt("Type") == TYPE_FAVORITES){
                quotesCursor = DatabaseUtils.getFavoriteQuotes(getActivity());
            }else {
                quotesCursor = DatabaseUtils.getAllQuotesWithCategories(getActivity());

            }
        }
        return quotesCursor;
    }

    private void implementListeners(){
        if (rvAdapter != null) {

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
                    Long quoteId;
                    if (mQuoteListener != null) {

                        //if We are in Favorites get the quoteID from StatusColumns.QUOTEID not _id
                        if (getArguments().getInt("Type") == TYPE_FAVORITES) {
                            quoteId = (long) rvAdapter.getSpecialId(position, StatusColumns.QUOTEID);

                        //if we are anywhere else get the quoteID as usual
                        } else {
                            quoteId = rvAdapter.getItemId(position);
                        }
                        mQuoteListener.onQuoteClicked(quoteId);
                    }
                }

                //Card category behavior if it's not already showing all quotes of a specific category
                @Override
                public void onCardCategoryClicked(int position) {
                    if (mCategoryListener != null && getArguments().getInt("Type") != TYPE_CATEGORIES) {
                        int categoryId = rvAdapter.getSpecialId(position, QuotesColumns.CATEGORYID);
                        mCategoryListener.onCategoryClicked(categoryId);
                    }
                }
            });
        }
    }
}
