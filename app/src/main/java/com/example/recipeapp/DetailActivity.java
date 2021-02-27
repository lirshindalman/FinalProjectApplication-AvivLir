package com.example.recipeapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView detail_TXT_name, detail_TXT_timer;
    private String key="";
    private String imageUrl="";
    private ListView detail_LST_ingredient, detail_LST_description;
    private int timer;
    MediaPlayer mp;
    ArrayList<String> firstListItems =new ArrayList<String>();
    ArrayList<String> secondListItems =new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();
        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){

            key = mBundle.getString("keyValue");
            imageUrl = mBundle.getString("Image");
            detail_TXT_name.setText(mBundle.getString("RecipeName"));
            firstListItems = mBundle.getStringArrayList("listIngredient");
            secondListItems = mBundle.getStringArrayList("listDescription");
            timer =  mBundle.getInt("timer");

            //Ingredient list view
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    firstListItems);

            detail_LST_ingredient.setAdapter(arrayAdapter);

            //Description list view
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    secondListItems);

            detail_LST_description.setAdapter(arrayAdapter2);

            //Show timer before start it
            int millisUntilFinished = timer * 1000;
            int seconds = (int) (millisUntilFinished / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            detail_TXT_timer.setText("TIME : " + String.format("%02d", minutes)
                    + ":" + String.format("%02d", seconds));
        }

    }

    public void findViews(){
        detail_LST_ingredient = (ListView) findViewById(R.id.detail_LST_ingredient);
        detail_LST_description = (ListView) findViewById(R.id.detail_LST_description);
        detail_TXT_name = (TextView) findViewById(R.id.detail_TXT_name);
        detail_TXT_timer = (TextView) findViewById(R.id.detail_TXT_timer);
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
                detail_TXT_timer.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                detail_TXT_timer.setText("Completed");
                playSound(R.raw.timer_snd);
            }
        }.start();
    }

    //Play sound when timer is over
    private void playSound(int rawId) {
        mp = MediaPlayer.create(this, rawId);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
        mp.start();
    }

    //Delete recipe from database
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
}
