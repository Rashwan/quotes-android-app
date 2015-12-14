package com.android.people.quotesandroidapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.models.Quote;
import com.android.people.quotesandroidapp.utils.DatabaseUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.android.people.quotesandroidapp.ui.Home.OnQuoteClickedListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnQuoteClickedListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // only for testing
    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "gfh");
        args.putString(ARG_PARAM2, "fdf");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        Quote firstQuote = DatabaseUtils.getRandomQuote(getActivity().getApplicationContext());
        Quote secondQuote = DatabaseUtils.getRandomQuote(getActivity().getApplicationContext());


        if (firstQuote != null) {
            firstQuoteContent.setText(firstQuote.getContent());
            firstQuoteCategory.setText(DatabaseUtils.getCategoryName(firstQuote.getCategoryID(), getActivity().getApplicationContext()));
        }
        if (secondQuote != null) {
            secondQuoteContent.setText(secondQuote.getContent());
            secondQuoteCategory.setText(DatabaseUtils.getCategoryName(secondQuote.getCategoryID(), getActivity().getApplicationContext()));
        }


        firstQuoteCard.setOnClickListener(this);
        secondQuoteCard.setOnClickListener(this);
        favoriteQuoteCard.setOnClickListener(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    private void onCardPressed(int quoteID) {
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
                Toast.makeText(getContext(), "Hi 1", Toast.LENGTH_LONG).show();
                break;
            case R.id.second_category_card:
                Toast.makeText(getContext(), "Hi 2", Toast.LENGTH_LONG).show();
                break;
            case R.id.favorite_category_card:
                Toast.makeText(getContext(), "Hi 3", Toast.LENGTH_LONG).show();
                break;

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnQuoteClickedListener {
        // TODO: Update argument type and name
        void onQuoteClicked(int quoteID);
    }

}
