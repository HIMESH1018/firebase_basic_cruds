package com.stepheninnovations.my_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    TextView forgetpass,register;
    private FirebaseAuth mAuth;
    AppCompatButton login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        SetUpUI();
        NotRegister();
        LoginUser();
    }


    private void SetUpUI(){

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        forgetpass = findViewById(R.id.forget_pass);
        register = findViewById(R.id.textview_register);
        login = findViewById(R.id.btn_login);

    }

    private void LoginUser(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email = email.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(Email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Fill the all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                mAuth.getUid();
                                Log.e("CheckUID",""+mAuth.getUid());
                                Log.e("CheckName",""+ Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }

    private void ForgetPass(){

    }

    private void NotRegister(){

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this,Register.class);
                startActivity(i);
            }
        });


    }
}