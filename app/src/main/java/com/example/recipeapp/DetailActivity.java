package com.example.recipeapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView RecipeName;
    private ImageView foodImage;
    private String key="";
    private String imageUrl="";
    private ListView ingredientList;
    private ListView descriptionList;
    private TextView tv;
    private int timer;
    private CountDownTimer countTimer;
    ArrayList<String> firstListItems =new ArrayList<String>();
    ArrayList<String> secondListItems =new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ingredientList = (ListView) findViewById(R.id.firstList);
        descriptionList = (ListView) findViewById(R.id.secondList);
        RecipeName = (TextView) findViewById(R.id.txtRecipeName);
        tv = (TextView) findViewById(R.id.txtTimer);
        //foodImage = (ImageView)findViewById(R.id.ivImage2);
        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){

            key = mBundle.getString("keyValue");
            imageUrl = mBundle.getString("Image");
            RecipeName.setText(mBundle.getString("RecipeName"));
            //foodImage.setImageResource(mBundle.getInt("Image"));
            firstListItems = mBundle.getStringArrayList("listIngredient");
            secondListItems = mBundle.getStringArrayList("listDescription");
            timer =  mBundle.getInt("timer");

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    firstListItems);

            ingredientList.setAdapter(arrayAdapter);


            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    secondListItems);

            descriptionList.setAdapter(arrayAdapter2);
            int millisUntilFinished = timer * 1000;
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            tv.setText("TIME : " + String.format("%02d", minutes)
                    + ":" + String.format("%02d", seconds));
        }

    }

    public void btnDeleteRecipe(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Recipe");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(DetailActivity.this, "Recipe Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });


    }

    public void startTimer(View view){
        Log.d("timer is :", ""+timer);
        reverseTimer(timer);
    }



    public void reverseTimer(int Seconds){

        new CountDownTimer(Seconds* 1000+1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                tv.setText("Completed");
            }
        }.start();
    }
}
