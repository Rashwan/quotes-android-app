package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.adapters.CategoriesRecyclerViewAdapter;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CategoriesFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = CategoriesFragment.class.getSimpleName();
    private int mColumnCount = 2;
    private CategoriesCursor categoriesCursor;
    @Bind(R.id.rv_all_categories )RecyclerView rvAllCategories;

    public CategoriesFragment() {
    }

    @SuppressWarnings("unused")

    public static CategoriesFragment newInstance(int columnCount) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        categoriesCursor = DatabaseUtils.getAllCategories(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        ButterKnife.bind(this,view);
        CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(getActivity(),categoriesCursor);
        if (mColumnCount <= 1) {
            rvAllCategories.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            rvAllCategories.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
        }
        rvAllCategories.setAdapter(adapter);
        rvAllCategories.setHasFixedSize(true);
        adapter.setOnCategoryClickListener(new CategoriesRecyclerViewAdapter.OnCategoryClickListener() {

            //Handle on Item Click by opening the QuoteList fragment & sending the type as Category
            // and the category name
            @Override
            public void onCategoryClicked(int position) {
                Intent intent = new Intent(getActivity(),QuotesOfACategory.class);
                intent.putExtra("Name",position+1);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}