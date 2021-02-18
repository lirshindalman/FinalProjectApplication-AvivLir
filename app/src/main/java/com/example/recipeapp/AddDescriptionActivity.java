package com.example.recipeapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.support.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddDescriptionActivity extends ListActivity {
    private EditText descriptionText;
    private EditText timerText;
    private FoodData foodData;
    private int timer = 0 ;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> descriptionList=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_description);

        descriptionText = (EditText) findViewById(R.id.descriptionText);
        timerText = (EditText) findViewById(R.id.timerText);
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

    public void addTimer(View v) {
        Log.d("timer is: ", String.valueOf(timerText.getText()));
        timer = Integer.parseInt(""+timerText.getText()) * 60;
        timerText.setText("Timer set to "+timerText.getText()+" minuts");
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

        foodData= new FoodData(itemName ,itemImageUrl,ingredients, descriptionList, timer);
        Gson gson = new Gson();
        String myJson = gson.toJson(foodData);
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra(Constants.FOOD_DATA, myJson);
        uploadRecipe();
        startActivity(new Intent(AddDescriptionActivity.this, MainActivity.class));
    }

    public void uploadRecipe(){

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Recipe")
                .child(myCurrentDateTime).setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(AddDescriptionActivity.this, "Recipe Uploaded", Toast.LENGTH_SHORT).show();

                    finish();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddDescriptionActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
