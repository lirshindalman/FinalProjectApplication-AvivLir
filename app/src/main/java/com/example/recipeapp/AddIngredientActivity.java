
 package com.example.recipeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class AddIngredientActivity extends ListActivity {
    private EditText add_TXT_ingredient;
    ArrayList<String> ingredientList=new ArrayList<String>();
    ArrayAdapter<String> adapter;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_ingredient);

        //list view
        add_TXT_ingredient = (EditText) findViewById(R.id.add_TXT_ingredient);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                ingredientList);
        setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        ingredientList.add("" + add_TXT_ingredient.getText());
        add_TXT_ingredient.setText("");
        adapter.notifyDataSetChanged();
    }

    //transfer info to the next activity
    public void nextBtn(View v){
        if(ingredientList.isEmpty()){
            ingredientList.add("");
        }
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


