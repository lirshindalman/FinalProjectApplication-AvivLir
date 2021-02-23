package com.example.recipeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Upload_Recipe extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText txt_name;
    String imageUrl="";
    private Button switch_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__recipe);

        recipeImage = (ImageView)findViewById(R.id.iv_foodImage);
        txt_name = (EditText)findViewById(R.id.txt_recipe_name);

    }

    public void btnSelectImage(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            recipeImage.setImageURI(uri);

        }
        else Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();
    }

    public void uploadImage(){
        try {
            imageUrl = "";
            continueToIngredient();


            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("RecipeImage").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Recipe Uplading....");
            //progressDialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    if (urlImage == null) {
                        Log.d("image url is ", "nulll");
                        imageUrl = "";
                    } else {
                        imageUrl = urlImage.toString();

                    }

                    Log.d("image url is not null ", imageUrl);
                    continueToIngredient();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
        }catch (Exception e){
            imageUrl = "";
            continueToIngredient();
        }
    }


    public void nextBtn(View view) {
        uploadImage();
    }

    public void continueToIngredient(){
        Intent addIngredient = new Intent(this, AddIngredientActivity.class);
        addIngredient.putExtra(Constants.FOOD_DATA_NAME, txt_name.getText().toString());
        Log.d("name is: ", txt_name.getText().toString());
        addIngredient.putExtra(Constants.FOOD_DATA_IMAGE_URL, imageUrl);
        Log.d("image url is: ", imageUrl);
        startActivity(addIngredient);
    }



}
