package com.example.android.bbtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText editForgotEmailId;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editForgotEmailId = findViewById(R.id.editForgotEmailId);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonUpdatePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editForgotEmailId.getText().toString().trim();
                String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
                if(email.isEmpty() || !email.matches(emailPattern)){
                    editForgotEmailId.setError("Enter EmailID");
                    editForgotEmailId.requestFocus();
                    return;
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Reset link sent!",
                                        Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(ForgotPasswordActivity.this, "Email Id does not exist",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
