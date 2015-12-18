package com.android.people.quotesandroidapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.people.quotesandroidapp.R;
import com.android.people.quotesandroidapp.provider.categories.CategoriesCursor;
import com.android.people.quotesandroidapp.utils.BaseCursorRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoriesRecyclerViewAdapter extends BaseCursorRecyclerAdapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    private Context mContext;

    //An Array of image resource ids one for each category
    private static final int[] CATEGORY_IMAGES = {R.drawable.book,R.drawable.sky,R.drawable.education,
            R.drawable.love,R.drawable.dream,R.drawable.image_new6,R.drawable.question,R.drawable.sad};

    private static OnCategoryClickListener mListener;

    public interface OnCategoryClickListener{
        void onCategoryClicked(int position);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener){
        mListener = listener;
    }

    public CategoriesRecyclerViewAdapter(Context context, CategoriesCursor c) {
        super(c);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.fragment_categories_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, Cursor c) {
        CategoriesCursor categoriesCursor = (CategoriesCursor) c;
        int categoryId = (int) categoriesCursor.getId();
        String categoryName = categoriesCursor.getCategory();

        holder.categoryImage.setImageResource(CATEGORY_IMAGES[categoryId-1]);
        holder.categoryName.setText(categoryName);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.category_image) ImageView categoryImage;
        @Bind(R.id.category_name) TextView categoryName;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null){

                //Delegate the click to the fragment
                mListener.onCategoryClicked(getLayoutPosition());
            }
        }
    }
}
