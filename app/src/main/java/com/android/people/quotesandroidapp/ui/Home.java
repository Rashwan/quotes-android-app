package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

public class Home extends Fragment implements View.OnClickListener {

    private OnQuoteClickedListener mListener;

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

        CardView firstQuoteCard = (CardView) rootView.findViewById(R.id.first_category_card);
        CardView secondQuoteCard = (CardView) rootView.findViewById(R.id.second_category_card);
        CardView favoriteQuoteCard = (CardView) rootView.findViewById(R.id.favorite_category_card);

        TextView firstQuoteContent = (TextView) firstQuoteCard.findViewById(R.id.first_quote_body);
        TextView secondQuoteContent = (TextView) secondQuoteCard.findViewById(R.id.second_quote_body);
        TextView favoriteQuoteContent = (TextView) favoriteQuoteCard.findViewById(R.id.favorite_quote_body);

        TextView firstQuoteCategory = (TextView) firstQuoteCard.findViewById(R.id.first_quote_title);
        TextView secondQuoteCategory = (TextView) secondQuoteCard.findViewById(R.id.second_quote_title);

        mFirstQuote = DatabaseUtils.getRandomQuote(getActivity().getApplicationContext());
        mSecondQuote = DatabaseUtils.getRandomQuote(getActivity().getApplicationContext());
        mFavoriteQuote = DatabaseUtils.getRandomFavoriteQuote(getActivity().getApplicationContext());

        Button first_fav = (Button) firstQuoteCard.findViewById(R.id.first_fav);
        Button second_fav = (Button) secondQuoteCard.findViewById(R.id.second_fav);


        if (mFirstQuote != null) {
            firstQuoteContent.setText(mFirstQuote.getContent());
            firstQuoteCategory.setText(DatabaseUtils.getCategoryName(mFirstQuote.getCategoryID(), getActivity().getApplicationContext()));
        }
        if (mSecondQuote != null) {
            secondQuoteContent.setText(mSecondQuote.getContent());
            secondQuoteCategory.setText(DatabaseUtils.getCategoryName(mSecondQuote.getCategoryID(), getActivity().getApplicationContext()));
        }
        if (mFavoriteQuote != null) {
            favoriteQuoteContent.setText(mFavoriteQuote.getContent());
        } else {
            favoriteQuoteContent.setText("No favorites yet!, Save some.");
        }


        firstQuoteCard.setOnClickListener(this);
        secondQuoteCard.setOnClickListener(this);
        favoriteQuoteCard.setOnClickListener(this);
        first_fav.setOnClickListener(this);
        second_fav.setOnClickListener(this);

        return rootView;
    }


    private void onCardPressed(long quoteID) {
        if (mListener != null) {
            mListener.onQuoteClicked(quoteID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuoteClickedListener) {
            mListener = (OnQuoteClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuoteClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_category_card:
                onCardPressed(mFirstQuote.getId());
                break;
            case R.id.second_category_card:
                onCardPressed(mSecondQuote.getId());
                break;
            case R.id.favorite_category_card:
                if (mFavoriteQuote != null) {
                    onCardPressed(mFavoriteQuote.getId());
                }
                break;
            case R.id.first_fav:
                DatabaseUtils.addToFavorites(mFirstQuote.getId(), getActivity().getApplicationContext());
                break;
            case R.id.second_fav:
                DatabaseUtils.addToFavorites(mSecondQuote.getId(), getActivity().getApplicationContext());
                break;

        }
    }

    //      This interface must be implemented by activities that contain this fragment
    public interface OnQuoteClickedListener {
        // TODO: Update argument type and name
        void onQuoteClicked(long quoteID);
    }

}
