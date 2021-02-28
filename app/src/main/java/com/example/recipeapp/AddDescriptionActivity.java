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
    private EditText add_TXT_description, add_TXT_timer;
    private FoodData foodData;
    private int timer = 0 ;
    ArrayList<String> descriptionList=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_add_description);
        findViews();

        //list view
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                descriptionList);
        setListAdapter(adapter);
    }
    public void findViews(){
        add_TXT_description = (EditText) findViewById(R.id.add_TXT_description);
        add_TXT_timer = (EditText) findViewById(R.id.add_TXT_timer);
    }


        //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(View v) {
        descriptionList.add("" + add_TXT_description.getText());
        add_TXT_description.setText("");
        adapter.notifyDataSetChanged();
    }

    public void addTimer(View v) {
        Log.d("timer is: ", String.valueOf(add_TXT_timer.getText()));
        timer = Integer.parseInt(""+ add_TXT_timer.getText()) * 60;
        add_TXT_timer.setText("Timer set to "+ add_TXT_timer.getText()+" minutes");
        adapter.notifyDataSetChanged();
    }

    public void finishBtn(View v){
        if(descriptionList.isEmpty()){
            descriptionList.add("");
        }

        //Getting food data info
        Log.d("descriptionList: ", descriptionList.toString());
        ArrayList<String> ingredients;
        ingredients = getIntent().getStringArrayListExtra(Constants.FOOD_DATA_INGRIDIENTS);
        Log.d("ingredients: ", ingredients.toString());
        String itemName = getIntent().getStringExtra(Constants.FOOD_DATA_NAME);
        Log.d("itemName: ", itemName);
        String itemImageUrl = getIntent().getStringExtra(Constants.FOOD_DATA_IMAGE_URL);
        Log.d("itemImage: ", itemImageUrl);

        //create food data object and transfer it to Detail Activity
        foodData= new FoodData(itemName ,itemImageUrl,ingredients, descriptionList, timer);
        Gson gson = new Gson();
        String myJson = gson.toJson(foodData);
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra(Constants.FOOD_DATA, myJson);
        uploadRecipe();
        startActivity(new Intent(AddDescriptionActivity.this, MainActivity.class));
    }

    //Upload recipe to firebase
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
