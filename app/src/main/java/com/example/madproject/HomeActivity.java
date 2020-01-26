package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.madproject.adapter.RecyclerViewAdapter;
import com.example.madproject.adapter.ViewPagerHeaderAdapter;
import com.example.madproject.model.Category;
import com.example.madproject.model.Meal;
import com.example.madproject.sql.DatabaseHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIL = "detail";

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    ViewPager viewPagerMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize(){
        databaseHelper = new DatabaseHelper(this);
        viewPagerMeal = findViewById(R.id.viewPagerHeader);
        recyclerView = findViewById(R.id.recycler_home);

        List<Meal> meal =  databaseHelper.getAllMeal();
        setMeal(meal);

        ArrayList<Category> categories =  databaseHelper.getAllCategories();
        setCategory(categories);
    }


    public void setMeal(List<Meal> meal) {
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();

        headerAdapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
            intent.putExtra(EXTRA_DETAIL, mealName.getText().toString());
            startActivity(intent);
        });
    }


    public void setCategory(ArrayList<Category> category) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(category, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);
        });
    }
}
