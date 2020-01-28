package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madproject.adapter.RecyclerViewMealByCategory;
import com.example.madproject.model.Meal;
import com.example.madproject.sql.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.OnClick;

import static com.example.madproject.HomeActivity.EXTRA_DETAIL;

public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imageCategory;
    ImageView imageCategoryBg;
    TextView textCategory;
    DatabaseHelper databaseHelper;

    AlertDialog.Builder descDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        imageCategory = view.findViewById(R.id.imageCategory);
        imageCategoryBg = view.findViewById(R.id.imageCategoryBg);
        textCategory = view.findViewById(R.id.textCategory);
        databaseHelper = new DatabaseHelper(getContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            textCategory.setText(getArguments().getString("EXTRA_DATA_DESC"));
            Picasso.get()
                    .load(getArguments().getString("EXTRA_DATA_IMAGE"))
                    .into(imageCategory);
            Picasso.get()
                    .load(getArguments().getString("EXTRA_DATA_IMAGE"))
                    .into(imageCategoryBg);
            descDialog = new AlertDialog.Builder(getActivity())
                    .setTitle(getArguments().getString("EXTRA_DATA_NAME"))
                    .setMessage(getArguments().getString("EXTRA_DATA_DESC"));


            List<Meal> meals = databaseHelper.getMealByCategoryName(getArguments().getString("EXTRA_DATA_NAME"));
            this.setMeals(meals);
        }
    }

    public void setMeals(List<Meal> meals) {
        RecyclerViewMealByCategory adapter =
                new RecyclerViewMealByCategory(getActivity(), meals);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);
        });
    }

    @OnClick(R.id.cardCategory)
    public void onClick() {
        descDialog.setPositiveButton("CLOSE", (dialog, which) -> dialog.dismiss());
        descDialog.show();
    }

}
