package com.example.madproject;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.madproject.model.Meal;
import com.example.madproject.sql.DatabaseHelper;
import com.squareup.picasso.Picasso;

import static com.example.madproject.HomeActivity.EXTRA_DETAIL;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView mealThumb;
    TextView category;
    TextView country;
    TextView instructions;
    TextView ingredients;
    TextView measures;
    TextView youtube;
    TextView source;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.appbar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mealThumb = findViewById(R.id.mealThumb);
        category = findViewById(R.id.category);
        country = findViewById(R.id.country);
        instructions = findViewById(R.id.instructions);
        ingredients = findViewById(R.id.ingredient);
        measures = findViewById(R.id.measure);
        youtube = findViewById(R.id.youtube);
        source = findViewById(R.id.source);
        databaseHelper = new DatabaseHelper(this);

        setupActionBar();

        Intent intent = getIntent();
        String mealName = intent.getStringExtra(EXTRA_DETAIL);

        Meal meal = databaseHelper.getMealByName(mealName);
        setMeal(meal);
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorWhite));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    void setupColorActionBarIcon(Drawable favoriteItemColor) {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if ((collapsingToolbarLayout.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout))) {
                if (toolbar.getNavigationIcon() != null)
                    toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                favoriteItemColor.mutate().setColorFilter(getResources().getColor(R.color.colorPrimary),
                        PorterDuff.Mode.SRC_ATOP);

            } else {
                if (toolbar.getNavigationIcon() != null)
                    toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
                favoriteItemColor.mutate().setColorFilter(getResources().getColor(R.color.colorWhite),
                        PorterDuff.Mode.SRC_ATOP);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem favoriteItem = menu.findItem(R.id.favorite);
        Drawable favoriteItemColor = favoriteItem.getIcon();
        setupColorActionBarIcon(favoriteItemColor);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setMeal(Meal meal) {
        Picasso.get().load(meal.getStrMealThumb()).into(mealThumb);
        collapsingToolbarLayout.setTitle(meal.getStrMeal());
        category.setText(meal.getStrCategory());
        country.setText(meal.getStrArea());
        instructions.setText(meal.getStrInstructions());
        setupActionBar();

        if (meal.getStrIngredient1() != null && !meal.getStrIngredient1().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient1());
        }
        if (meal.getStrIngredient2() != null && !meal.getStrIngredient2().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient2());
        }
        if (meal.getStrIngredient3() != null && !meal.getStrIngredient3().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient3());
        }
        if (meal.getStrIngredient4() != null && !meal.getStrIngredient4().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient4());
        }
        if (meal.getStrIngredient5() != null && !meal.getStrIngredient5().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient5());
        }
        if (meal.getStrIngredient6() != null && !meal.getStrIngredient6().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient6());
        }
        if (meal.getStrIngredient7() != null && !meal.getStrIngredient7().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient7());
        }
        if (meal.getStrIngredient8() != null && !meal.getStrIngredient8().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient8());
        }
        if (meal.getStrIngredient9() != null && !meal.getStrIngredient9().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient9());
        }
        if (meal.getStrIngredient10() != null && !meal.getStrIngredient10().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient10());
        }
        if (meal.getStrIngredient11() != null && !meal.getStrIngredient11().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient11());
        }
        if (meal.getStrIngredient12() != null && !meal.getStrIngredient12().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient12());
        }
        if (meal.getStrIngredient13() != null && !meal.getStrIngredient13().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient13());
        }
        if (meal.getStrIngredient14() != null && !meal.getStrIngredient14().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient14());
        }
        if (meal.getStrIngredient15() != null && !meal.getStrIngredient15().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient15());
        }
        if (meal.getStrIngredient16() != null && !meal.getStrIngredient16().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient16());
        }
        if (meal.getStrIngredient17() != null && !meal.getStrIngredient17().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient17());
        }
        if (meal.getStrIngredient18() != null && !meal.getStrIngredient18().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient18());
        }
        if (meal.getStrIngredient19() != null && !meal.getStrIngredient19().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient19());
        }
        if (meal.getStrIngredient20() != null && !meal.getStrIngredient20().isEmpty()) {
            ingredients.append("\n \u2022 " + meal.getStrIngredient20());
        }

        if (meal.getStrMeasure1() != null && !meal.getStrMeasure1().isEmpty() && !Character.isWhitespace(meal.getStrMeasure1().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure1());
        }
        if (meal.getStrMeasure2() != null && !meal.getStrMeasure2().isEmpty() && !Character.isWhitespace(meal.getStrMeasure2().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure2());
        }
        if (meal.getStrMeasure3() != null && !meal.getStrMeasure3().isEmpty() && !Character.isWhitespace(meal.getStrMeasure3().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure3());
        }
        if (meal.getStrMeasure4() != null && !meal.getStrMeasure4().isEmpty() && !Character.isWhitespace(meal.getStrMeasure4().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure4());
        }
        if (meal.getStrMeasure5() != null && !meal.getStrMeasure5().isEmpty() && !Character.isWhitespace(meal.getStrMeasure5().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure5());
        }
        if (meal.getStrMeasure6() != null && !meal.getStrMeasure6().isEmpty() && !Character.isWhitespace(meal.getStrMeasure6().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure6());
        }
        if (meal.getStrMeasure7() != null && !meal.getStrMeasure7().isEmpty() && !Character.isWhitespace(meal.getStrMeasure7().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure7());
        }
        if (meal.getStrMeasure8() != null && !meal.getStrMeasure8().isEmpty() && !Character.isWhitespace(meal.getStrMeasure8().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure8());
        }
        if (meal.getStrMeasure9() != null && !meal.getStrMeasure9().isEmpty() && !Character.isWhitespace(meal.getStrMeasure9().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure9());
        }
        if (meal.getStrMeasure10() != null && !meal.getStrMeasure10().isEmpty() && !Character.isWhitespace(meal.getStrMeasure10().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure10());
        }
        if (meal.getStrMeasure11() != null && !meal.getStrMeasure11().isEmpty() && !Character.isWhitespace(meal.getStrMeasure11().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure11());
        }
        if (meal.getStrMeasure12() != null && !meal.getStrMeasure12().isEmpty() && !Character.isWhitespace(meal.getStrMeasure12().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure12());
        }
        if (meal.getStrMeasure13() != null && !meal.getStrMeasure13().isEmpty() && !Character.isWhitespace(meal.getStrMeasure13().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure13());
        }
        if (meal.getStrMeasure14() != null && !meal.getStrMeasure14().isEmpty() && !Character.isWhitespace(meal.getStrMeasure14().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure14());
        }
        if (meal.getStrMeasure15() != null && !meal.getStrMeasure15().isEmpty() && !Character.isWhitespace(meal.getStrMeasure15().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure15());
        }
        if (meal.getStrMeasure16() != null && !meal.getStrMeasure16().isEmpty() && !Character.isWhitespace(meal.getStrMeasure16().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure16());
        }
        if (meal.getStrMeasure17() != null && !meal.getStrMeasure17().isEmpty() && !Character.isWhitespace(meal.getStrMeasure17().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure17());
        }
        if (meal.getStrMeasure18() != null && !meal.getStrMeasure18().isEmpty() && !Character.isWhitespace(meal.getStrMeasure18().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure18());
        }
        if (meal.getStrMeasure19() != null && !meal.getStrMeasure19().isEmpty() && !Character.isWhitespace(meal.getStrMeasure19().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure19());
        }
        if (meal.getStrMeasure20() != null && !meal.getStrMeasure20().isEmpty() && !Character.isWhitespace(meal.getStrMeasure20().charAt(0))) {
            measures.append("\n : " + meal.getStrMeasure20());
        }


        youtube.setOnClickListener(v -> {
            Intent intentYoutube = new Intent(Intent.ACTION_VIEW);
            intentYoutube.setData(Uri.parse(meal.getStrYoutube()));
            startActivity(intentYoutube);
        });

        source.setOnClickListener(v -> {
            Intent intentSource = new Intent(Intent.ACTION_VIEW);
            intentSource.setData(Uri.parse(meal.getStrSource()));
            startActivity(intentSource);
        });
    }

}
