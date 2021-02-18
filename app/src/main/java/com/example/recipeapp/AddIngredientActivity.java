
 package com.example.recipeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class AddIngredientActivity extends ListActivity {
    private EditText ingredientText;


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> ingredientList=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_ingredient);

        ingredientText = (EditText) findViewById(R.id.ingredientText);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                ingredientList);
        setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        ingredientList.add("" + ingredientText.getText());
        ingredientText.setText("");
        adapter.notifyDataSetChanged();
    }

    public void nextBtn(View v){
        //Gson gson = new Gson();
//        FoodData foodData= gson.fromJson(getIntent().getStringExtra("MyRecipe"), FoodData.class);
//        foodData.setIngredient(listItems);
//        Log.d("namne :",foodData.getItemName());
//        Log.d("listItems :",listItems.toString());
//        String myJson = gson.toJson(foodData);
        String itemName = getIntent().getStringExtra(Constants.FOOD_DATA_NAME);
        String itemImageUrl = getIntent().getStringExtra(Constants.FOOD_DATA_IMAGE_URL);
        Intent addDescriptionActivity = new Intent(this, AddDescriptionActivity.class);
        Log.d("ingredientList" ,ingredientList.toString());
        addDescriptionActivity.putExtra(Constants.FOOD_DATA_INGRIDIENTS, ingredientList);
        addDescriptionActivity.putExtra(Constants.FOOD_DATA_NAME, itemName);
        addDescriptionActivity.putExtra(Constants.FOOD_DATA_IMAGE_URL, itemImageUrl);
        startActivity(addDescriptionActivity);
    }

}


