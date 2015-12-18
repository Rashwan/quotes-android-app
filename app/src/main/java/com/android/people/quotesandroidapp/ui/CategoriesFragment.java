package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.adapters.CategoriesRecyclerViewAdapter;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CategoriesFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = CategoriesFragment.class.getSimpleName();
    private int mColumnCount = 2;
    private CategoriesCursor categoriesCursor;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
        Log.d(TAG, "newInstance");
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        categoriesCursor = DatabaseUtils.getAllCategories(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        RecyclerView rvAllCategories = (RecyclerView) view.findViewById(R.id.rv_all_categories);
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
                QuotesListFragment fragment = QuotesListFragment.newInstance(QuotesListFragment.TYPE_CATEGORIES, position + 1);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.main_activity_fragment_container, fragment).commit();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);

    }

}
