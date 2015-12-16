package com.android.people.quotesandroidapp.ui;


import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuoteDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuoteDetails extends Fragment {

    private static final String QUOTE_ID_KEY = "quoteID";

    private Long mQuoteID;


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

    @Bind(R.id.single_quote)
    TextView singleQuote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quote_details, container, false);

        ButterKnife.bind(this, rootView);

        Quote quote = DatabaseUtils.getSpecificQuote(mQuoteID, getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        singleQuote.setText(quote.getContent());


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
