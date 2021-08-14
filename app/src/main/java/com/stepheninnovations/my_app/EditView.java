package com.stepheninnovations.my_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditView extends AppCompatActivity {

    EditText username,email;
    ImageButton option;
    AppCompatButton update,delete;
    String pkey;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_view);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        option = findViewById(R.id.option);
        update = findViewById(R.id.btn_update);
        delete = findViewById(R.id.btn_delete);


        BindData();

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                username.setEnabled(true);
                email.setEnabled(true);

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(pkey);
                String unme = username.getText().toString();
                String Email = email.getText().toString();

                User user = new User(unme,Email);
                databaseReference.setValue(user);
                Toast.makeText(EditView.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(pkey);
                databaseReference.removeValue();
                Toast.makeText(EditView.this, "Data Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void BindData(){

        Intent intent = getIntent();
        String uname = intent.getStringExtra("username");
        String emal = intent.getStringExtra("email");
        String key = intent.getStringExtra("key");

        Log.e("IntentData",uname+"\n"+email+"\n"+key);

        username.setText(uname);
        email.setText(emal);
        pkey = key;

       // txt_heading.setText(topic);
       // article.setText(description);



    }
}