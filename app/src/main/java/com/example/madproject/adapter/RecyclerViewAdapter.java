package com.example.madproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madproject.R;
import com.example.madproject.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeView> {

    ArrayList<Category> categories = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;

    public RecyclerViewAdapter(ArrayList<Category> categoryArrayList, Context context)
    {
        this.categories = categoryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_grid, viewGroup, false);
        return new HomeView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeView homeView, int i) {
        String strCategoryThumb = categories.get(i).getStrCategoryThumb();
        Picasso.get().load(strCategoryThumb).into(homeView.categoryThumb);
        //homeView.categoryThumb.setImageURI(strCategoryThumb);
        String strCategoryName = categories.get(i).getStrCategory();
        homeView.categoryName.setText(strCategoryName);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class HomeView extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView categoryName;
       ImageView categoryThumb;
        HomeView(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            categoryThumb = itemView.findViewById(R.id.category_thumb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerViewAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
