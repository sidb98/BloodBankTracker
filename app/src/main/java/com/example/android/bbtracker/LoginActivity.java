package com.example.android.bbtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editEmailId;
    private TextInputEditText editPassword;
    private FirebaseAuth mAuth;


    public static boolean flagWarning;
    static String password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmailId = findViewById(R.id.editEmailId);
        editPassword = findViewById(R.id.editPassword);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        final String initialPassword = "QAZWSX";
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmailId.getText().toString().trim();
                password = editPassword.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(email.isEmpty()){
                    editEmailId.setError("Enter Email Id");
                    editEmailId.requestFocus();
                    return;
                }
                if(!email.matches(emailPattern)){
                    editEmailId.setError("Invalid Email Id");
                    editEmailId.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    editPassword.setError("Enter Password");
                    editPassword.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (task.isSuccessful() && password.equals(initialPassword)) {
                                    flagWarning = true;
                                    Log.d("FlagHide", "onComplete: Opening ResetActivity " +flagWarning);
                                    Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    //Intent intent=new Intent(LoginActivity.this,UpdateDB.class);
                                    finish();
                                    startActivity(intent);

                                }
                                else if (task.isSuccessful() && !password.equals(initialPassword)) {
                                    flagWarning = false;
                                    Log.d("FlagHide", "onComplete: Opening ProfileActivity " +flagWarning);
                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    finish();
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Invalid Email Id or Password ",
                                            Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });

            }
        });

        findViewById(R.id.buttonForgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}