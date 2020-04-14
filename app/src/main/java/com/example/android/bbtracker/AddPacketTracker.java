package com.example.android.bbtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPacketTracker extends AppCompatActivity {


    Button signin,updateandsignout;
    EditText emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_packet_tracker);

        emailid=(EditText)findViewById(R.id.textptemail);
        signin=(Button)findViewById(R.id.ptsignin);
        updateandsignout=(Button)findViewById(R.id.ptupdate);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailid.getText().toString().trim();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,LoginActivity.initialPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AddPacketTracker.this,"Sign In Successful!",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(AddPacketTracker.this,"Sign In Failed! or User dont exist",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        updateandsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BloodGroups bg=new BloodGroups(0,0,0,0,0,0,0,0);
                String date0="00-00-0000";
                String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference reff=FirebaseDatabase.getInstance().getReference().child("PacketTracker");
                reff.child(userid).child("WholeHumanBlood").setValue(bg);
                reff.child(userid).child("PackedRBC").setValue(bg);
                reff.child(userid).child("PlateletConcentration").setValue(bg);
                reff.child(userid).child("AntiHaemophilicFactor").setValue(bg);
                reff.child(userid).child("FreshFrozenPlasma").setValue(bg);
                reff.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LastUpdated").setValue(date0)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                  @Override
                                                  public void onSuccess(Void aVoid) {
                                                      Toast.makeText(AddPacketTracker.this,"Update Successful", Toast.LENGTH_LONG).show();
                                                  }
                                              }
                        ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddPacketTracker.this,"Update Unsuccessful", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        findViewById(R.id.ptsignout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                    Toast.makeText(AddPacketTracker.this,"Sign out Failed", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AddPacketTracker.this,"Sign out Successful", Toast.LENGTH_LONG).show();;
            }
        });
    }
}
