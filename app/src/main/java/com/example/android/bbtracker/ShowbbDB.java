package com.example.android.bbtracker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowbbDB extends AppCompatActivity {

    TextView whbap,whban,whbbp,whbbn,whbabp,whbabn,whbop,whbon,
            prbcap,prbcan,prbcbp,prbcbn,prbcabp,prbcabn,prbcop,prbcon,
            pcap,pcan,pcbp,pcbn,pcabp,pcabn,pcop,pcon,
            ahfap,ahfan,ahfbp,ahfbn,ahfabp,ahfabn,ahfop,ahfon,
            ffpap,ffpan,ffpbp,ffpbn,ffpabp,ffpabn,ffpop,ffpon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showbb_d_b);

        final ExpandableCardView whbcard = findViewById(R.id.cardViewWHB);
        final ExpandableCardView prbccard = findViewById(R.id.cardViewPRBC);
        final ExpandableCardView pccard = findViewById(R.id.cardViewPC);
        final ExpandableCardView ahfcard = findViewById(R.id.cardViewAHF);
        final ExpandableCardView ffpcard = findViewById(R.id.cardViewFFP);

        whbap=(TextView)findViewById(R.id.tvwhbAP);
        whban=(TextView)findViewById(R.id.tvwhbAN);
        whbbp=(TextView)findViewById(R.id.tvwhbBP);
        whbbn=(TextView)findViewById(R.id.tvwhbBN);
        whbabp=(TextView)findViewById(R.id.tvwhbABP);
        whbabn=(TextView)findViewById(R.id.tvwhbABN);
        whbop=(TextView)findViewById(R.id.tvwhbOP);
        whbon=(TextView)findViewById(R.id.tvwhbON);

        prbcap=(TextView)findViewById(R.id.tvprbcAP);
        prbcan=(TextView)findViewById(R.id.tvprbcAN);
        prbcbp=(TextView)findViewById(R.id.tvprbcBP);
        prbcbn=(TextView)findViewById(R.id.tvprbcBN);
        prbcabp=(TextView)findViewById(R.id.tvprbcABP);
        prbcabn=(TextView)findViewById(R.id.tvprbcABN);
        prbcop=(TextView)findViewById(R.id.tvprbcOP);
        prbcon=(TextView)findViewById(R.id.tvprbcON);

        pcap=(TextView)findViewById(R.id.tvpcAP);
        pcan=(TextView)findViewById(R.id.tvpcAN);
        pcbp=(TextView)findViewById(R.id.tvpcBP);
        pcbn=(TextView)findViewById(R.id.tvpcBN);
        pcabp=(TextView)findViewById(R.id.tvpcABP);
        pcabn=(TextView)findViewById(R.id.tvpcABN);
        pcop=(TextView)findViewById(R.id.tvpcOP);
        pcon=(TextView)findViewById(R.id.tvpcON);

        ahfap=(TextView)findViewById(R.id.tvahfAP);
        ahfan=(TextView)findViewById(R.id.tvahfAN);
        ahfbp=(TextView)findViewById(R.id.tvahfBP);
        ahfbn=(TextView)findViewById(R.id.tvahfBN);
        ahfabp=(TextView)findViewById(R.id.tvahfABP);
        ahfabn=(TextView)findViewById(R.id.tvahfABN);
        ahfop=(TextView)findViewById(R.id.tvahfOP);
        ahfon=(TextView)findViewById(R.id.tvahfON);

        ffpap=(TextView)findViewById(R.id.tvffpAP);
        ffpan=(TextView)findViewById(R.id.tvffpAN);
        ffpbp=(TextView)findViewById(R.id.tvffpBP);
        ffpbn=(TextView)findViewById(R.id.tvffpBN);
        ffpabp=(TextView)findViewById(R.id.tvffpABP);
        ffpabn=(TextView)findViewById(R.id.tvffpABN);
        ffpop=(TextView)findViewById(R.id.tvffpOP);
        ffpon=(TextView)findViewById(R.id.tvffpON);


        DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("PacketTracker");
        //Log.d("TAG1",MainActivity.bbref);
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                BloodGroups WHB=dataSnapshot.child(MainActivity.bbref).child("WholeHumanBlood").getValue(BloodGroups.class);
                BloodGroups PRBC=dataSnapshot.child(MainActivity.bbref).child("PackedRBC").getValue(BloodGroups.class);
                BloodGroups PC=dataSnapshot.child(MainActivity.bbref).child("PlateletConcentration").getValue(BloodGroups.class);
                BloodGroups AHF=dataSnapshot.child(MainActivity.bbref).child("AntiHemophilicFactor").getValue(BloodGroups.class);
                BloodGroups FFP=dataSnapshot.child(MainActivity.bbref).child("FreshFrozenPlasma").getValue(BloodGroups.class);

                int whbsum=WHB.AP+WHB.AN+WHB.BP+WHB.BN+WHB.ABP+WHB.ABN+WHB.OP+WHB.ON;
                int prbcsum=PRBC.AP+PRBC.AN+PRBC.BP+PRBC.BN+PRBC.ABP+PRBC.ABN+PRBC.OP+PRBC.ON;
                int pcsum=PC.AP+PC.AN+PC.BP+PC.BN+PC.ABP+PC.ABN+PC.OP+PC.ON;
                int ahfsum=AHF.AP+AHF.AN+AHF.BP+AHF.BN+AHF.ABP+AHF.ABN+AHF.OP+AHF.ON;
                int ffpsum=FFP.AP+FFP.AN+FFP.BP+FFP.BN+FFP.ABP+FFP.ABN+FFP.OP+FFP.ON;

                whbcard.setTitle(-1,getString(R.string.WHB)+"\nTotal Count: "+whbsum);
                prbccard.setTitle(-1,getString(R.string.PRBC)+"\nTotal Count: "+prbcsum);
                pccard.setTitle(-1,getString(R.string.PC)+"\nTotal Count: "+pcsum);
                ahfcard.setTitle(-1,getString(R.string.AHF)+"\nTotal Count: "+ahfsum);
                ffpcard.setTitle(-1,getString(R.string.FFP)+"\nTotal Count: "+ffpsum);
                
                whbap.setText(Integer.toString(WHB.AP));
                whban.setText(Integer.toString(WHB.AN));
                whbbp.setText(Integer.toString(WHB.BP));
                whbbn.setText(Integer.toString(WHB.BN));
                whbabp.setText(Integer.toString(WHB.ABP));
                whbabn.setText(Integer.toString(WHB.ABN));
                whbop.setText(Integer.toString(WHB.OP));
                whbon.setText(Integer.toString(WHB.ON));

                prbcap.setText(Integer.toString(PRBC.AP));
                prbcan.setText(Integer.toString(PRBC.AN));
                prbcbp.setText(Integer.toString(PRBC.BP));
                prbcbn.setText(Integer.toString(PRBC.BN));
                prbcabp.setText(Integer.toString(PRBC.ABP));
                prbcabn.setText(Integer.toString(PRBC.ABN));
                prbcop.setText(Integer.toString(PRBC.OP));
                prbcon.setText(Integer.toString(PRBC.ON));

                pcap.setText(Integer.toString(PC.AP));
                pcan.setText(Integer.toString(PC.AN));
                pcbp.setText(Integer.toString(PC.BP));
                pcbn.setText(Integer.toString(PC.BN));
                pcabp.setText(Integer.toString(PC.ABP));
                pcabn.setText(Integer.toString(PC.ABN));
                pcop.setText(Integer.toString(PC.OP));
                pcon.setText(Integer.toString(PC.ON));

                ahfap.setText(Integer.toString(AHF.AP));
                ahfan.setText(Integer.toString(AHF.AN));
                ahfbp.setText(Integer.toString(AHF.BP));
                ahfbn.setText(Integer.toString(AHF.BN));
                ahfabp.setText(Integer.toString(AHF.ABP));
                ahfabn.setText(Integer.toString(AHF.ABN));
                ahfop.setText(Integer.toString(AHF.OP));
                ahfon.setText(Integer.toString(AHF.ON));

                ffpap.setText(Integer.toString(FFP.AP));
                ffpan.setText(Integer.toString(FFP.AN));
                ffpbp.setText(Integer.toString(FFP.BP));
                ffpbn.setText(Integer.toString(FFP.BN));
                ffpabp.setText(Integer.toString(FFP.ABP));
                ffpabn.setText(Integer.toString(FFP.ABN));
                ffpop.setText(Integer.toString(FFP.OP));
                ffpon.setText(Integer.toString(FFP.ON));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
