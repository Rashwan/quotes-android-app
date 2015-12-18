package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.utils.CategoryClickListener;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;
import com.android.people.quotesandroidapp.utils.QuoteClickListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends Fragment {


    @Bind(R.id.first_quote_body)
    TextView firstQuoteContent;
    @Bind(R.id.second_quote_body)
    TextView secondQuoteContent;
    @Bind(R.id.favorite_quote_body)
    TextView favoriteQuoteContent;


    @Bind(R.id.first_quote_category)
    TextView firstQuoteCategory;
    @Bind(R.id.second_quote_category)
    TextView secondQuoteCategory;

    @Bind(R.id.first_fav)
    Button first_fav;


    @Bind(R.id.second_fav)
    Button second_fav;


    private QuoteClickListener mQuoteListener;
    private CategoryClickListener mCatListener;

    public Home() {
    }

    // quotes for the two categories cards
    private Quote mFirstQuote;
    private Quote mSecondQuote;

    // quote for favorite card
    private Quote mFavoriteQuote;


    // Fragment factory method
    public static Home newInstance() {
        return new Home();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // bind rootView
        ButterKnife.bind(this, rootView);


        mFirstQuote = DatabaseUtils.getRandomQuote(getActivity().getApplicationContext());
        mSecondQuote = DatabaseUtils.getRandomQuote(getActivity().getApplicationContext());
        mFavoriteQuote = DatabaseUtils.getRandomFavoriteQuote(getActivity().getApplicationContext());


        if (mFirstQuote != null) {
            firstQuoteContent.setText(mFirstQuote.getContent());
            firstQuoteCategory.setText(DatabaseUtils.getCategoryName(mFirstQuote.getCategoryID(), getActivity().getApplicationContext()));
            setInitialFavoriteState(mFirstQuote.getId(), first_fav);

        }
        if (mSecondQuote != null) {
            secondQuoteContent.setText(mSecondQuote.getContent());
            secondQuoteCategory.setText(DatabaseUtils.getCategoryName(mSecondQuote.getCategoryID(), getActivity().getApplicationContext()));
            setInitialFavoriteState(mSecondQuote.getId(), second_fav);


        }
        if (mFavoriteQuote != null) {
            favoriteQuoteContent.setText(mFavoriteQuote.getContent());
        } else {
            favoriteQuoteContent.setText("No favorites yet!, Save some.");
        }


        return rootView;
    }


    private void onCardPressed(long quoteID) {
        if (mQuoteListener != null) {
            mQuoteListener.onQuoteClicked(quoteID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QuoteClickListener) {
            mQuoteListener = (QuoteClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuoteClickListener");
        }

        if (context instanceof CategoryClickListener) {
            mCatListener = (CategoryClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCategoryClickListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mQuoteListener = null;
        mCatListener = null;
    }


    @OnClick(R.id.first_category_card)
    public void onFirstCardClicked() {
        onCardPressed(mFirstQuote.getId());
    }


    @OnClick(R.id.second_category_card)
    public void onSecondCardClicked() {
        onCardPressed(mSecondQuote.getId());
    }


    @OnClick(R.id.favorite_category_card)
    public void onFavCardClicked() {
        if (mFavoriteQuote != null) {
            onCardPressed(mFavoriteQuote.getId());
        }
    }


    @OnClick(R.id.first_fav)
    public void onFirstFavClicked() {
        changeFavoriteState(mFirstQuote.getId(), first_fav);
    }


    @OnClick(R.id.second_fav)
    public void onSecondFavClicked() {
        changeFavoriteState(mSecondQuote.getId(), second_fav);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void changeFavoriteState(Long quoteID, Button b) {

        //If the quote is already favorited remove it otherwise add it
        if (DatabaseUtils.isQuoteFavorite(getActivity().getApplicationContext(), quoteID)) {
            DatabaseUtils.removeFromFavorites(quoteID, getActivity().getApplicationContext());
            b.setText("Add to favorites");
        } else {
            DatabaseUtils.addToFavorites(quoteID, getActivity().getApplicationContext());
            b.setText("Favorited");
        }

    }

    private void setInitialFavoriteState(Long quoteID, Button b) {


        if (DatabaseUtils.isQuoteFavorite(getActivity().getApplicationContext(), quoteID)) {
            b.setText("Favorited");
        } else {
            b.setText("Add to favorite");
        }

    }

    @OnClick(R.id.first_quote_category)
    public void onFirstCatClicked() {
        openCategoryQuotes(mFirstQuote.getCategoryID());
    }

    @OnClick(R.id.second_quote_category)
    public void onSecondCatClicked() {
        openCategoryQuotes(mSecondQuote.getCategoryID());
    }

    private void openCategoryQuotes(long catID) {
        if (mCatListener != null) {
            mCatListener.onCategoryClicked((int) catID);
        }

    }
}

