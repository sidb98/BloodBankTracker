package com.example.android.bbtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FirebaseUser firebaseUser;
    private Button buttonLogin;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseAuth mAuth;


    ArrayList<LastUpdate> bbname=new ArrayList<>();
    ArrayList<LastUpdate> tempbbname=new ArrayList<>();
    ArrayList<String> zone=new ArrayList<>();
    ArrayList<String> tempzone=new ArrayList<>();
    ArrayList<String> uids=new ArrayList<>();
    ArrayList<String> phoneno=new ArrayList<>();
    public static  ArrayList<String> tempphoneno=new ArrayList<>();
    ArrayList<String> tempuids=new ArrayList<>();

    ArrayList<BloodGroups> whbl=new ArrayList<>();
    ArrayList<BloodGroups> prbcl=new ArrayList<>();
    ArrayList<BloodGroups> pcl=new ArrayList<>();
    ArrayList<BloodGroups> ahfl=new ArrayList<>();
    ArrayList<BloodGroups> ffpl=new ArrayList<>();

    ArrayList<Integer> whbitemcount=new ArrayList<>();
    ArrayList<Integer> prbcitemcount=new ArrayList<>();
    ArrayList<Integer> pcitemcount=new ArrayList<>();
    ArrayList<Integer> ahfitemcount=new ArrayList<>();
    ArrayList<Integer> ffpitemcount=new ArrayList<>();

    ListView bblistview;
    DatabaseReference reff= FirebaseDatabase.getInstance().getReference();
    DatabaseReference bbdreff= FirebaseDatabase.getInstance().getReference().child("BloodBankDetails");
    static String bbref,bbnameref;
    TextView whbcount,prbccount,pccount,ahfcount,ffpcount,tvbg;
    EditText search;
    BBListAdapter BBArrayAdapter;
    private int TotalWHB=0,TotalPRBC=0,TotalPC=0,TotalAHF=0,TotalFFP=0;
    ProgressBar progressBar;
    private Spinner categoryspinner,bgspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth.getInstance().signOut();

        bblistview=(ListView)findViewById(R.id.BBListView);
        BBArrayAdapter=new BBListAdapter(this,R.layout.adapter_view_bbdetails,tempbbname);

        progressBar=findViewById(R.id.progress_circular);


        categoryspinner=findViewById(R.id.categoryspinner);
        bgspinner=findViewById(R.id.bgspinner);
        tvbg=findViewById(R.id.displaytvbg);

        bblistview.setAdapter(BBArrayAdapter);

        whbcount=(TextView)findViewById(R.id.whbcount);
        prbccount=(TextView)findViewById(R.id.prbccount);
        pccount=(TextView)findViewById(R.id.pccount);
        ahfcount=(TextView)findViewById(R.id.ahfcount);
        ffpcount=(TextView)findViewById(R.id.ffpcount);

        progressBar.setVisibility(View.VISIBLE);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapshot=dataSnapshot.child("PacketTracker");
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    //Getting total Count
                    BloodGroups WHB=snapshot1.child("WholeHumanBlood").getValue(BloodGroups.class);
                    BloodGroups PRBC=snapshot1.child("PackedRBC").getValue(BloodGroups.class);
                    BloodGroups PC=snapshot1.child("PlateletConcentration").getValue(BloodGroups.class);
                    BloodGroups AHF=snapshot1.child("AntiHaemophilicFactor").getValue(BloodGroups.class);
                    BloodGroups FFP=snapshot1.child("FreshFrozenPlasma").getValue(BloodGroups.class);

                    whbl.add(WHB);
                    prbcl.add(PRBC);
                    pcl.add(PC);
                    ahfl.add(AHF);
                    ffpl.add(FFP);

                    whbitemcount.add(WHB.AP+WHB.AN+WHB.BP+WHB.BN+WHB.ABP+WHB.ABN+WHB.OP+WHB.ON);
                    prbcitemcount.add(PRBC.AP+PRBC.AN+PRBC.BP+PRBC.BN+PRBC.ABP+PRBC.ABN+PRBC.OP+PRBC.ON);
                    pcitemcount.add(PC.AP+PC.AN+PC.BP+PC.BN+PC.ABP+PC.ABN+PC.OP+PC.ON);
                    ahfitemcount.add(AHF.AP+AHF.AN+AHF.BP+AHF.BN+AHF.ABP+AHF.ABN+AHF.OP+AHF.ON);
                    ffpitemcount.add(FFP.AP+FFP.AN+FFP.BP+FFP.BN+FFP.ABP+FFP.ABN+FFP.OP+FFP.ON);

                    TotalWHB+=WHB.AP+WHB.AN+WHB.BP+WHB.BN+WHB.ABP+WHB.ABN+WHB.OP+WHB.ON;
                    TotalPRBC+=PRBC.AP+PRBC.AN+PRBC.BP+PRBC.BN+PRBC.ABP+PRBC.ABN+PRBC.OP+PRBC.ON;
                    TotalPC+=PC.AP+PC.AN+PC.BP+PC.BN+PC.ABP+PC.ABN+PC.OP+PC.ON;
                    TotalAHF+=AHF.AP+AHF.AN+AHF.BP+AHF.BN+AHF.ABP+AHF.ABN+AHF.OP+AHF.ON;
                    TotalFFP+=FFP.AP+FFP.AN+FFP.BP+FFP.BN+FFP.ABP+FFP.ABN+FFP.OP+FFP.ON;

                    //Getting Name,PhoneNumber and Last Updated
                    LastUpdate myChildValue = new LastUpdate((String) dataSnapshot.child("BloodBankDetails").child(snapshot1.getKey()).child("Name").getValue(), (String) snapshot1.child("LastUpdated").getValue());

                    bbname.add(myChildValue);
                    tempbbname.add(myChildValue);
                    uids.add(snapshot1.getKey());
                    tempuids.add(snapshot1.getKey());
                    zone.add((String) dataSnapshot.child("BloodBankDetails").child(snapshot1.getKey()).child("Zone").getValue());
                    phoneno.add((dataSnapshot.child("BloodBankDetails").child(snapshot1.getKey()).child("PhoneNumber").getValue().toString().trim()));
                    tempphoneno.add((dataSnapshot.child("BloodBankDetails").child(snapshot1.getKey()).child("PhoneNumber").getValue().toString().trim()));
                    Log.d("TAG", snapshot1.getKey());
                    BBArrayAdapter.notifyDataSetChanged();
                }
                whbcount.setText(Integer.toString(TotalWHB));
                prbccount.setText(Integer.toString(TotalPRBC));
                pccount.setText(Integer.toString(TotalPC));
                ahfcount.setText(Integer.toString(TotalAHF));
                ffpcount.setText(Integer.toString(TotalFFP));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        categoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(categoryspinner.getSelectedItem().toString().trim().compareTo("None")==0){
                    bgspinner.setVisibility(View.INVISIBLE);
                    tvbg.setVisibility(View.INVISIBLE);
                    updateList(search.getText().toString().trim().toLowerCase().replaceAll("\\s","")
                            ,categoryspinner.getSelectedItem().toString().trim()
                            ,bgspinner.getSelectedItem().toString().trim());
                    BBArrayAdapter.notifyDataSetChanged();
                }
                else{
                    bgspinner.setVisibility(View.VISIBLE);
                    tvbg.setVisibility(View.VISIBLE);
                    updateList(search.getText().toString().trim().toLowerCase().replaceAll("\\s","")
                            ,categoryspinner.getSelectedItem().toString().trim()
                            ,bgspinner.getSelectedItem().toString().trim());
                    BBArrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bgspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(tvbg.getVisibility()==View.VISIBLE){
                    updateList(search.getText().toString().trim().toLowerCase().replaceAll("\\s","")
                            ,categoryspinner.getSelectedItem().toString().trim()
                            ,bgspinner.getSelectedItem().toString().trim());
                    BBArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search=(EditText)findViewById(R.id.txtsearch);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()){
                    updateList(editable.toString().trim().toLowerCase().replaceAll("\\s","")
                            ,categoryspinner.getSelectedItem().toString().trim()
                            ,bgspinner.getSelectedItem().toString().trim());
                    BBArrayAdapter.notifyDataSetChanged();
                }
                else{
                    updateList(editable.toString().trim().toLowerCase().replaceAll("\\s","")
                            ,categoryspinner.getSelectedItem().toString().trim()
                            ,bgspinner.getSelectedItem().toString().trim());
                    BBArrayAdapter.notifyDataSetChanged();
                }
            }
        });


        bblistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bbref=tempuids.get(i);
                bbnameref=tempbbname.get(i).BBName;
                Intent myIntent=new Intent(MainActivity.this,ShowbbDB.class);
                startActivity(myIntent);
            }
        });


        buttonLogin = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();
//        Log.d("Tag1",FirebaseAuth.getInstance().getCurrentUser().getUid());

        if(FirebaseAuth.getInstance().getCurrentUser() != null ) {
            buttonLogin.setText("Profile");
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });

        }
        else {
            buttonLogin.setText("Login");
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

//        findViewById(R.id.buttonAddUser).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent=new Intent(MainActivity.this,UpdateDB.class);
//                startActivity(myIntent);
//            }
//        });
//
//        findViewById(R.id.buttonAddPT).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent=new Intent(MainActivity.this,AddPacketTracker.class);
//                startActivity(myIntent);
//            }
//        });

//        findViewById(R.id.buttonDisplayDB).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent myIntent=new Intent(MainActivity.this,DisplayDB.class);
//                startActivity(myIntent);
//            }
//        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        buttonLogin = findViewById(R.id.buttonLogin);
        if(FirebaseAuth.getInstance().getCurrentUser() != null ) {
            buttonLogin.setText("Profile");
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });

        }
        else {
            buttonLogin.setText("Login");
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
    private void updateList(String searchstring,String category,String bg){
        tempuids.clear();
        tempbbname.clear();
        tempzone.clear();
        tempphoneno.clear();
        for(int i=0;i<bbname.size();i++){

            if (bbname.get(i).BBName.toLowerCase().replaceAll("\\s","").contains(searchstring) ||
                    zone.get(i).toLowerCase().replaceAll("\\s", "").contains(searchstring)){
                switch(category){
                    case "Whole Human Blood":
                        switch (bg){
                            case "A+ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).AP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "A-ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).AN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B+ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).BP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B-ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).BN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB+ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).ABP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB-ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).ABN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O+ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).OP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O-ve":
                                if(whbitemcount.get(i)>0 && whbl.get(i).ON>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "None":
                                if(whbitemcount.get(i)>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            default:
                                Toast.makeText(this,"Error Occurred",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Packed RBC":
                        switch (bg){
                            case "A+ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).AP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "A-ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).AN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B+ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).BP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B-ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).BN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB+ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).ABP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB-ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).ABN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O+ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).OP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O-ve":
                                if(prbcitemcount.get(i)>0 && prbcl.get(i).ON>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "None":
                                if(prbcitemcount.get(i)>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            default:
                                Toast.makeText(this,"Error Occurred",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Platelet Concentration":
                        switch (bg){
                            case "A+ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).AP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "A-ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).AN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B+ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).BP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B-ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).BN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB+ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).ABP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB-ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).ABN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O+ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).OP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O-ve":
                                if(pcitemcount.get(i)>0 && pcl.get(i).ON>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "None":
                                if(pcitemcount.get(i)>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            default:
                                Toast.makeText(this,"Error Occurred",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Anti Hemophilic Factor":
                        switch (bg){
                            case "A+ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).AP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "A-ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).AN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B+ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).BP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B-ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).BN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB+ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).ABP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB-ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).ABN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O+ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).OP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O-ve":
                                if(ahfitemcount.get(i)>0 && ahfl.get(i).ON>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "None":
                                if(ahfitemcount.get(i)>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            default:
                                Toast.makeText(this,"Error Occurred",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "Fresh Frozen Plasma":
                        switch (bg){
                            case "A+ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).AP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "A-ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).AN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B+ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).BP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "B-ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).BN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB+ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).ABP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "AB-ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).ABN>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O+ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).OP>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "O-ve":
                                if(ffpitemcount.get(i)>0 && ffpl.get(i).ON>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            case "None":
                                if(ffpitemcount.get(i)>0){
                                    tempbbname.add(bbname.get(i));
                                    tempuids.add(uids.get(i));
                                    tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                                }
                                break;
                            default:
                                Toast.makeText(this,"Error Occurred",Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        tempbbname.add(bbname.get(i));
                        tempzone.add(zone.get(i));tempphoneno.add(phoneno.get(i));
                        tempuids.add(uids.get(i));
                        //Toast.makeText(this,"Error Occured",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }
}