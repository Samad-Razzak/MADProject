package com.example.madproject.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.madproject.model.Category;
import com.example.madproject.model.Meal;
import com.example.madproject.model.User;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FoodApp.db";
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_AGREED = "user_agreed";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_AGREED + " INTEGER DEFAULT 0" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private static final String TABLE_CATEGORY = "category";

    private static final String COLUMN_CATEGORY_ID = "idCategory";
    private static final String COLUMN_CATEGORY_NAME = "strCategory";
    private static final String COLUMN_CATEGORY_THUMB = "strCategoryThumb";
    private static final String COLUMN_CATEGORY_DESCRIPTION = "strCategoryDescription";

    private static String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORY_NAME + " TEXT,"
            + COLUMN_CATEGORY_THUMB + " TEXT,"
            + COLUMN_CATEGORY_DESCRIPTION + " TEXT"
            + ")";

    public static String DROP_CATEGORY_TABLE = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            db.execSQL(CREATE_CATEGORY_TABLE);
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(Meal.CREATE_MEAL_TABLE);
            Log.i("Database:", "Created Successfully");
        }catch (Exception ex){
            Log.e("Database:", ex.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CATEGORY_TABLE);
        db.execSQL(Meal.DROP_MEAL_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_AGREED, user.getAgree());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email){
        String[] columns = {
          COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }

    public boolean checkUser(String username, String password){
        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }


    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> categories = new ArrayList<Category>();

        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

       while (cursor.moveToNext()){
           Category category = new Category();
           category.setIdCategory(cursor.getInt(
                   cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
           category.setStrCategory(cursor.getString(
                   cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
           category.setStrCategoryThumb(cursor.getString(
                   cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_THUMB)));
           category.setStrCategoryDescription(cursor.getString(
                   cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_DESCRIPTION)));

           categories.add(category);
       }
       return categories;
    }

    public Category getCategoryById(String id){
        Category category = new Category();
        String[] columns = {
                COLUMN_CATEGORY_ID,
                COLUMN_CATEGORY_NAME,
                COLUMN_CATEGORY_THUMB,
                COLUMN_CATEGORY_DESCRIPTION
        };

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_CATEGORY_ID + " = ?";
        String[] selectionArgs = { id };

        Cursor cursor = db.query(TABLE_CATEGORY,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            category.setIdCategory(cursor.getInt(
                    cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
            category.setStrCategory(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)));
            category.setStrCategoryThumb(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_THUMB)));
            category.setStrCategoryDescription(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_DESCRIPTION)));

        }
        return category;
    }

    public ArrayList<Meal> getAllMeal(){
        ArrayList<Meal> meals = new ArrayList<Meal>();

        String selectQuery = "SELECT  * FROM " + Meal.TABLE_MEAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()){
            Meal meal = new Meal();
            meal.setIdMeal(cursor.getInt(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_ID)));
            meal.setStrMeal(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_NAME)));
            meal.setStrDrinkAlternate(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_DRINK_ALTERNATE)));
            meal.setStrCategory(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_CATEGORY)));
            meal.setStrArea(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_AREA)));
            meal.setStrInstructions(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INSTRUCTIONS)));
            meal.setStrMealThumb(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_THUMB)));
            meal.setStrTags(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_TAGS)));
            meal.setStrYoutube(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_YOUTUBE)));
            meal.setStrIngredient1(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT1)));
            meal.setStrIngredient2(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT2)));
            meal.setStrIngredient3(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT3)));
            meal.setStrIngredient4(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT4)));
            meal.setStrIngredient5(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT5)));
            meal.setStrIngredient6(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT6)));
            meal.setStrIngredient7(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT7)));
            meal.setStrIngredient8(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT8)));
            meal.setStrIngredient9(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT9)));
            meal.setStrIngredient10(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT10)));
            meal.setStrIngredient11(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT11)));
            meal.setStrIngredient12(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT12)));
            meal.setStrIngredient13(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT13)));
            meal.setStrIngredient14(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT14)));
            meal.setStrIngredient15(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT15)));
            meal.setStrIngredient16(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT16)));
            meal.setStrIngredient17(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT17)));
            meal.setStrIngredient18(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT18)));
            meal.setStrIngredient19(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT19)));
            meal.setStrIngredient20(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT20)));
            meal.setStrMeasure1(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE1)));
            meal.setStrMeasure2(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE2)));
            meal.setStrMeasure3(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE3)));
            meal.setStrMeasure4(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE4)));
            meal.setStrMeasure5(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE5)));
            meal.setStrMeasure6(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE6)));
            meal.setStrMeasure7(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE7)));
            meal.setStrMeasure8(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE8)));
            meal.setStrMeasure9(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE9)));
            meal.setStrMeasure10(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE10)));
            meal.setStrMeasure11(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE11)));
            meal.setStrMeasure12(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE12)));
            meal.setStrMeasure13(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE13)));
            meal.setStrMeasure14(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE14)));
            meal.setStrMeasure15(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE15)));
            meal.setStrMeasure16(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE16)));
            meal.setStrMeasure17(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE17)));
            meal.setStrMeasure18(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE18)));
            meal.setStrMeasure19(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE19)));
            meal.setStrMeasure20(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE20)));
            meal.setStrSource(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_SOURCE)));
            meal.setDateModified(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_DATEMODIFIED)));

            meals.add(meal);
        }
        return meals;
    }

    public Meal getMealById(String id){
        Meal meal = new Meal();

        String[] columns = {
                Meal.COLUMN_MEAL_ID,
                Meal.COLUMN_MEAL_NAME,
                Meal.COLUMN_MEAL_DRINK_ALTERNATE,
                Meal.COLUMN_MEAL_CATEGORY,
                Meal.COLUMN_MEAL_AREA,
                Meal.COLUMN_MEAL_INSTRUCTIONS,
                Meal.COLUMN_MEAL_THUMB,
                Meal.COLUMN_MEAL_TAGS,
                Meal.COLUMN_MEAL_INGREDIENT1,
                Meal.COLUMN_MEAL_INGREDIENT2,
                Meal.COLUMN_MEAL_INGREDIENT3,
                Meal.COLUMN_MEAL_INGREDIENT4,
                Meal.COLUMN_MEAL_INGREDIENT5,
                Meal.COLUMN_MEAL_INGREDIENT6,
                Meal.COLUMN_MEAL_INGREDIENT7,
                Meal.COLUMN_MEAL_INGREDIENT8,
                Meal.COLUMN_MEAL_INGREDIENT9,
                Meal.COLUMN_MEAL_INGREDIENT10,
                Meal.COLUMN_MEAL_INGREDIENT11,
                Meal.COLUMN_MEAL_INGREDIENT12,
                Meal.COLUMN_MEAL_INGREDIENT13,
                Meal.COLUMN_MEAL_INGREDIENT14,
                Meal.COLUMN_MEAL_INGREDIENT15,
                Meal.COLUMN_MEAL_INGREDIENT16,
                Meal.COLUMN_MEAL_INGREDIENT17,
                Meal.COLUMN_MEAL_INGREDIENT18,
                Meal.COLUMN_MEAL_INGREDIENT19,
                Meal.COLUMN_MEAL_INGREDIENT20,
                Meal.COLUMN_MEAL_MEASURE1,
                Meal.COLUMN_MEAL_MEASURE2,
                Meal.COLUMN_MEAL_MEASURE3,
                Meal.COLUMN_MEAL_MEASURE4,
                Meal.COLUMN_MEAL_MEASURE5,
                Meal.COLUMN_MEAL_MEASURE6,
                Meal.COLUMN_MEAL_MEASURE7,
                Meal.COLUMN_MEAL_MEASURE8,
                Meal.COLUMN_MEAL_MEASURE9,
                Meal.COLUMN_MEAL_MEASURE10,
                Meal.COLUMN_MEAL_MEASURE11,
                Meal.COLUMN_MEAL_MEASURE12,
                Meal.COLUMN_MEAL_MEASURE13,
                Meal.COLUMN_MEAL_MEASURE14,
                Meal.COLUMN_MEAL_MEASURE15,
                Meal.COLUMN_MEAL_MEASURE16,
                Meal.COLUMN_MEAL_MEASURE17,
                Meal.COLUMN_MEAL_MEASURE18,
                Meal.COLUMN_MEAL_MEASURE19,
                Meal.COLUMN_MEAL_MEASURE20,
                Meal.COLUMN_MEAL_SOURCE,
                Meal.COLUMN_MEAL_DATEMODIFIED
        };

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = Meal.COLUMN_MEAL_ID + " = ?";
        String[] selectionArgs = { id };

        Cursor cursor = db.query(Meal.TABLE_MEAL,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            meal.setIdMeal(cursor.getInt(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_ID)));
            meal.setStrMeal(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_NAME)));
            meal.setStrDrinkAlternate(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_DRINK_ALTERNATE)));
            meal.setStrCategory(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_CATEGORY)));
            meal.setStrArea(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_AREA)));
            meal.setStrInstructions(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INSTRUCTIONS)));
            meal.setStrMealThumb(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_THUMB)));
            meal.setStrTags(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_TAGS)));
            meal.setStrYoutube(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_YOUTUBE)));
            meal.setStrIngredient1(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT1)));
            meal.setStrIngredient2(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT2)));
            meal.setStrIngredient3(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT3)));
            meal.setStrIngredient4(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT4)));
            meal.setStrIngredient5(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT5)));
            meal.setStrIngredient6(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT6)));
            meal.setStrIngredient7(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT7)));
            meal.setStrIngredient8(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT8)));
            meal.setStrIngredient9(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT9)));
            meal.setStrIngredient10(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT10)));
            meal.setStrIngredient11(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT11)));
            meal.setStrIngredient12(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT12)));
            meal.setStrIngredient13(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT13)));
            meal.setStrIngredient14(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT14)));
            meal.setStrIngredient15(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT15)));
            meal.setStrIngredient16(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT16)));
            meal.setStrIngredient17(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT17)));
            meal.setStrIngredient18(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT18)));
            meal.setStrIngredient19(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT19)));
            meal.setStrIngredient20(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_INGREDIENT20)));
            meal.setStrMeasure1(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE1)));
            meal.setStrMeasure2(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE2)));
            meal.setStrMeasure3(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE3)));
            meal.setStrMeasure4(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE4)));
            meal.setStrMeasure5(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE5)));
            meal.setStrMeasure6(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE6)));
            meal.setStrMeasure7(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE7)));
            meal.setStrMeasure8(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE8)));
            meal.setStrMeasure9(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE9)));
            meal.setStrMeasure10(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE10)));
            meal.setStrMeasure11(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE11)));
            meal.setStrMeasure12(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE12)));
            meal.setStrMeasure13(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE13)));
            meal.setStrMeasure14(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE14)));
            meal.setStrMeasure15(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE15)));
            meal.setStrMeasure16(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE16)));
            meal.setStrMeasure17(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE17)));
            meal.setStrMeasure18(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE18)));
            meal.setStrMeasure19(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE19)));
            meal.setStrMeasure20(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_MEASURE20)));
            meal.setStrSource(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_SOURCE)));
            meal.setDateModified(cursor.getString(
                    cursor.getColumnIndexOrThrow(Meal.COLUMN_MEAL_DATEMODIFIED)));

        }
        return meal;
    }

}
