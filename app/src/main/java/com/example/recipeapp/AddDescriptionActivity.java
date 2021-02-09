package com.example.recipeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.google.gson.Gson;
import java.util.ArrayList;

public class AddDescriptionActivity extends ListActivity {
    private EditText descriptionText;


    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_description);

        descriptionText = (EditText) findViewById(R.id.descriptionText);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        listItems.add("" + descriptionText.getText());
        descriptionText.setText("");
        adapter.notifyDataSetChanged();
    }

    public void finishBtn(View v){
        Gson gson = new Gson();
        FoodData foodData= gson.fromJson(getIntent().getStringExtra("MyRecipe"), FoodData.class);
        foodData.setDescription(listItems);

        String myJson = gson.toJson(foodData);
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("MyRecipe", myJson);

        startActivity(new Intent(AddDescriptionActivity.this, MainActivity.class));
    }
}
