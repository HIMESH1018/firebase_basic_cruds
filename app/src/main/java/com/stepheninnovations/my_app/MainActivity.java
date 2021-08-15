package com.stepheninnovations.my_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stepheninnovations.my_app.Firebase.DB_User;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText username,email;
    AppCompatButton btn_add,btn_view;
    ImageView imageView;
    public Uri imageurl;
    public String ImgUrl;
    private DB_User dbUser;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        imageView = findViewById(R.id.imageView);
        dbUser = new DB_User();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadpicture();

            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,DataView.class);
                startActivity(i);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectpicture();//only gallery
            }
        });


    }

    private void selectpicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imageurl = data.getData();
            imageView.setImageURI(imageurl);

        }
    }

    private void uploadpicture() {
        if(imageView.getDrawable() == null){
            Toast.makeText(this, "Please Select image", Toast.LENGTH_SHORT).show();
        }
        else {
            // Create a reference to "mountains.jpg"
            final String randomkey = UUID.randomUUID().toString();
            StorageReference mountainsRef = storageReference.child("Test/" + randomkey);
            mountainsRef.putFile(imageurl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            mountainsRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Uri> task) {
//                                    ImgUrl = task.getResult().toString();
//                                    Log.i("URL", "" + ImgUrl);
//                                    imageView.setImageURI(null);
//                                    Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                            });
                            mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    ImgUrl = uri.toString();
                                    Log.e("checkURI",""+ImgUrl);
                                    imageView.setImageURI(null);
                                    Toast.makeText(MainActivity.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                                    dataadd();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void dataadd(){

        if(username.getText().equals("") || email.getText().equals(""))
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
        else{
            User user = new User(username.getText().toString(),email.getText().toString(),ImgUrl);

            dbUser.add(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    uploadpicture();
                    Toast.makeText(MainActivity.this, "Data Added Successfully", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    email.setText("");
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}