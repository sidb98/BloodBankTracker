package com.example.android.bbtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.bbtracker.BBDetails;
import com.example.android.bbtracker.R;
import com.example.android.bbtracker.Tracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateDB extends AppCompatActivity {

    EditText name,phonenumber,address,emailid,zone;
    Button updatedata;
    Tracker tracker1;
    FirebaseAuth mAuth;

    HashMap<String, String> result = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_db);

        name=(EditText)findViewById(R.id.txtName);
        phonenumber=(EditText)findViewById(R.id.txtPhoneNumber);
        address=(EditText)findViewById(R.id.txtAddress);
        zone=(EditText)findViewById(R.id.txtZone) ;
        emailid = findViewById(R.id.txtEmail);
        mAuth = FirebaseAuth.getInstance();

        final String oldpassword = LoginActivity.initialPassword;


        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String email = emailid.getText().toString().trim();
                if (email.isEmpty() || !email.matches(emailPattern)) {
                    emailid.setError("Enter EmailID");
                    emailid.requestFocus();
                    return;
                }
                else if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Toast.makeText(UpdateDB.this,"Already signed in",Toast.LENGTH_LONG).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email, oldpassword)
                            .addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                try {
                                                    throw task.getException();
                                                }
                                                // if user enters wrong email.
                                                catch (FirebaseAuthWeakPasswordException weakPassword) {
                                                    Log.d("TAG", "onComplete: weak_password");

                                                    // TODO: take your actions!
                                                }
                                                // if user enters wrong password.
                                                catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                                    Log.d("TAG", "onComplete: malformed_email");

                                                    // TODO: Take your action
                                                } catch (FirebaseAuthUserCollisionException existEmail) {
                                                    Log.d("TAG", "onComplete: exist_email");
                                                    Toast.makeText(UpdateDB.this,"User already Exist",Toast.LENGTH_LONG).show();

                                                    // TODO: Take your action
                                                } catch (Exception e) {
                                                    Log.d("TAG", "onComplete: " + e.getMessage());
                                                }
                                            } else {
                                                Toast.makeText(UpdateDB.this, "Sign Up Done", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                            );
                }
            }
        });

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namev = name.getText().toString().trim();
                String phoneno =(phonenumber.getText().toString().trim());
                String addressv = address.getText().toString().trim();
                String zonev=zone.getText().toString().trim();
                Pattern p = Pattern.compile("[7-9][0-9]{9}");
                Matcher m = p.matcher((phoneno));
                if (namev.isEmpty() ||zonev.isEmpty() || addressv.isEmpty()) {
                    Toast.makeText(UpdateDB.this, "Enter correct Details", Toast.LENGTH_LONG).show();
                }
                else {
                    BBDetails details = new BBDetails(namev, phoneno, addressv, zonev);
                    BloodGroups bg=new BloodGroups(0,0,0,0,0,0,0,0);
                    String date0="00-00-0000";
                    if (mAuth.getCurrentUser() != null) {
                        FirebaseDatabase.getInstance().getReference().child("BloodBankDetails")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(details);
                        FirebaseDatabase.getInstance().getReference().child("PacketTracker")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(bg);
                        FirebaseDatabase.getInstance().getReference().child("PacketTracker")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LastUpdated").setValue(date0)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                          @Override
                                                          public void onSuccess(Void aVoid) {
                                                              Toast.makeText(UpdateDB.this,"Update Successful", Toast.LENGTH_LONG).show();
                                                          }
                                                      }
                                ).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateDB.this,"Update Unsuccessful", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else
                        Toast.makeText(UpdateDB.this, "Sign in first", Toast.LENGTH_LONG).show();


                }
            }
        });

        findViewById(R.id.signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                emailid.setText("");
                name.setText("");
                phonenumber.setText("");
                address.setText("");
                zone.setText("");
                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    Toast.makeText(UpdateDB.this,"Sign out Failed", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(UpdateDB.this,"Sign out Successful", Toast.LENGTH_LONG).show();

            }
        });

    };


}