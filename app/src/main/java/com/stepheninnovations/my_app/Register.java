package com.stepheninnovations.my_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText fname,lname,email,pass,mobile;
    AppCompatButton register;
    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        User user;
        SetupUI();
        Register();
    }


    private void SetupUI(){

        fname = findViewById(R.id.f_name);
        lname = findViewById(R.id.l_name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        mobile = findViewById(R.id.mobile);

        register = findViewById(R.id.btn_register);

    }

    private void Register(){

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = email.getText().toString();
                String Firstname = fname.getText().toString();
                String Lastname = lname.getText().toString();
                String Password = pass.getText().toString();
                String Mobile = mobile.getText().toString();

                if(Email.isEmpty() || Firstname.isEmpty() || Lastname.isEmpty() || Password.isEmpty() || Mobile.isEmpty()){
                    Toast.makeText(Register.this, "Please Fill the all Fileds", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser fuser = mAuth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(Register.this, "Email Verification Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(Register.this, "Email Verification error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Toast.makeText(Register.this, "Register Successful.", Toast.LENGTH_SHORT).show();
                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = firestore.collection("users").document(userID);

                                Map<String,Object> user = new HashMap<>();
                                user.put("FirstName",Firstname);
                                user.put("LastName",Lastname);
                                user.put("Email",Email);
                                user.put("Mobile",Mobile);

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("Register_Check", "onSuccess: user Profile is created for "+ userID);
                                        Intent intent = new Intent(Register.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Register_Check", "onFailure: " + e.toString());
                                    }
                                });
                            }else {
                                Toast.makeText(Register.this, "Something Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });




    }
}