package com.android.people.quotesandroidapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.adapters.AllQuotesRecyclerAdapter;
import com.android.people.quotesandroidapp.provider.quotes.QuotesCursor;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

/**
 * Created by rashwan on 12/14/15.
 */
public class AllQuotes extends Fragment {

    QuotesCursor quotesCursor;

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

        quotesCursor = DatabaseUtils.getAllQuotesWithCategories(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_quotes,container,false);

        RecyclerView rvAllQuotes = (RecyclerView) rootView.findViewById(R.id.rv_all_quotes);
        AllQuotesRecyclerAdapter rvAdapter = new AllQuotesRecyclerAdapter(getActivity(),quotesCursor);

        rvAllQuotes.setAdapter(rvAdapter);
        rvAllQuotes.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}
