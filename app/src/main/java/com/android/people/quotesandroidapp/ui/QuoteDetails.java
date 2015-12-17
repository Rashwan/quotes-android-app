package com.android.people.quotesandroidapp.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuoteDetails extends Fragment {

    private static final String QUOTE_ID_KEY = "quoteID";

    private Long mQuoteID;
    private Quote mSingleQuote;

    @Bind(R.id.single_quote_content)
    TextView singleQuote;

    @Bind(R.id.single_quote_category)
    TextView singleQuoteCategory;

    @Bind(R.id.fav_single_quote)
    FloatingActionButton fab;

    public QuoteDetails() {
    }

    public static QuoteDetails newInstance(Long quoteID) {
        QuoteDetails fragment = new QuoteDetails();
        Bundle args = new Bundle();
        args.putLong(QUOTE_ID_KEY, quoteID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuoteID = getArguments().getLong(QUOTE_ID_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quote_details, container, false);

        final Context context = getActivity().getApplicationContext();

        ButterKnife.bind(this, rootView);

        mSingleQuote = DatabaseUtils.getSpecificQuote(mQuoteID, context);

        if (mSingleQuote != null) {
            singleQuote.setText(mSingleQuote.getContent());
            singleQuoteCategory.setText(DatabaseUtils.getCategoryName(mSingleQuote.getCategoryID(), context));

            if (DatabaseUtils.isQuoteFavorite(context, mSingleQuote.getId())) {
                fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_heart_filled));
            }
        }


        return rootView;
    }

    @OnClick(R.id.fav_single_quote)
    public void onFabClicked() {
        if (mSingleQuote != null) {
            changeFabState();
        }
    }

    private void changeFabState() {

        //If the quote is already favorited remove it otherwise add it
        if (DatabaseUtils.isQuoteFavorite(getActivity().getApplicationContext(), mSingleQuote.getId())) {
            DatabaseUtils.removeFromFavorites(mSingleQuote.getId(), getActivity().getApplicationContext());
            fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_heart_outline));
        } else {
            DatabaseUtils.addToFavorites(mSingleQuote.getId(), getActivity().getApplicationContext());
            fab.setImageDrawable(getActivity().getDrawable(R.drawable.ic_heart_filled));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
