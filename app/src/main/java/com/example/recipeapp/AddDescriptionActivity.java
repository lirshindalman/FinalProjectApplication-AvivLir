package com.example.recipeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.google.gson.Gson;
import java.util.ArrayList;

public class AddDescriptionActivity extends ListActivity {
    private EditText descriptionText;


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> descriptionList=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_description);

        descriptionText = (EditText) findViewById(R.id.descriptionText);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                descriptionList);
        setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        descriptionList.add("" + descriptionText.getText());
        descriptionText.setText("");
        adapter.notifyDataSetChanged();
    }

    public void finishBtn(View v){
        Log.d("descriptionList: ", descriptionList.toString());
        ArrayList<String> ingredients;
        ingredients = getIntent().getStringArrayListExtra(Constants.FOOD_DATA_INGRIDIENTS);
        Log.d("ingredients: ", ingredients.toString());

        String itemName = getIntent().getStringExtra(Constants.FOOD_DATA_NAME);
        Log.d("itemName: ", itemName);
        String itemImageUrl = getIntent().getStringExtra(Constants.FOOD_DATA_IMAGE_URL);
        Log.d("itemImage: ", itemImageUrl);

        FoodData foodData= new FoodData(itemName ,itemImageUrl,ingredients, descriptionList);
        Gson gson = new Gson();
        String myJson = gson.toJson(foodData);
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra(Constants.FOOD_DATA, myJson);

        startActivity(new Intent(AddDescriptionActivity.this, MainActivity.class));
    }
}
