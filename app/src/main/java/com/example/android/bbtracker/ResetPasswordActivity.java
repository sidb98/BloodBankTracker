package com.example.android.bbtracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editCurrentPassword, editNewPassword, editConfirmPassword;
    private FirebaseUser currentUser;

    private TextView warningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        editCurrentPassword = findViewById(R.id.editCurrentPassword);
        warningText = findViewById(R.id.warningText);
        if(LoginActivity.flagWarning) {
            Log.d("FlagHide", "onCreate: Reset Flag warning Visible(1)"+ LoginActivity.flagWarning);
            warningText.setVisibility(View.VISIBLE);
        } else {
            Log.d("FlagHide", "onCreate: Reset Flag warning Visible(2)"+ LoginActivity.flagWarning);
            warningText.setVisibility(View.GONE);
        }


//        if (LoginActivity.password.equals("QAZWSX")) {
//            Toast.makeText(ResetPasswordActivity.this, "Change password after first login",
//                    Toast.LENGTH_LONG).show();
//        }

        findViewById(R.id.buttonUpdatePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = editCurrentPassword.getText().toString();
                final String newPassword = editNewPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();

                if (currentPassword.isEmpty()) {
                    editCurrentPassword.setError("Current Password Required");
                    editCurrentPassword.requestFocus();
                    return;
                }

                if (newPassword.isEmpty()) {
                    editNewPassword.setError("Enter a new password");
                    editNewPassword.requestFocus();
                    return;
                }
                if (newPassword.length() < 6) {
                    editNewPassword.setError("Password must contain at least 6 characters");
                    editNewPassword.requestFocus();
                    return;
                }
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Please make sure your passwords match",
                            Toast.LENGTH_LONG).show();
                    editNewPassword.requestFocus();
                    editConfirmPassword.requestFocus();
                }
                if (newPassword.equals(currentPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "New password cannot be same as old password",
                            Toast.LENGTH_LONG).show();
                    editNewPassword.requestFocus();
                    editCurrentPassword.requestFocus();
                } else {
                    currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), currentPassword);
                    currentUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ResetPasswordActivity.this, "Password changed successfully",
                                                    Toast.LENGTH_SHORT).show();
                                            //FirebaseDatabase.getInstance().getReference().child("BBDetails").child(firmNumber)
                                            Log.d("ResetPassword", "Password Changed");
                                            warningText.setVisibility(View.GONE);
                                            LoginActivity.flagWarning = false;
                                            Intent intent = new Intent(ResetPasswordActivity.this, ProfileActivity.class);
                                            finish();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(ResetPasswordActivity.this, "Old password is incorrect ",
                                                    Toast.LENGTH_SHORT).show();
                                            Log.d("Password Error", "Error auth failed");
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Error occurred while changing password ",
                                        Toast.LENGTH_SHORT).show();
                                Log.d("Password Error", "Error auth failed");
                            }
                        }
                    });

                }
            }
        });

    }
}
