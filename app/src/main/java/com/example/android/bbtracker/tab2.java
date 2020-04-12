package com.example.android.bbtracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText whbap,whban,whbbp,whbbn,whbabp,whbabn,whbop,whbon,
            prbcap,prbcan,prbcbp,prbcbn,prbcabp,prbcabn,prbcop,prbcon,
            pcap,pcan,pcbp,pcbn,pcabp,pcabn,pcop,pcon,
            ahfap,ahfan,ahfbp,ahfbn,ahfabp,ahfabn,ahfop,ahfon,
            ffpap,ffpan,ffpbp,ffpbn,ffpabp,ffpabn,ffpop,ffpon;
    Button updatedata;
    public int whbapn,whbann,whbbpn,whbbnn,whbabpn,whbabnn,whbopn,whbonn,
            prbcapn,prbcann,prbcbpn,prbcbnn,prbcabpn,prbcabnn,prbcopn,prbconn,
            pcapn,pcann,pcbpn,pcbnn,pcabpn,pcabnn,pcopn,pconn,
            ahfapn,ahfann,ahfbpn,ahfbnn,ahfabpn,ahfabnn,ahfopn,ahfonn,
            ffpapn,ffpann,ffpbpn,ffpbnn,ffpabpn,ffpabnn,ffpopn,ffponn;
    Tracker tracker1;
    String userid;

    int flag=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_tab2, container, false);

        final ExpandableCardView whbcard = v.findViewById(R.id.cardViewWHB);
        final ExpandableCardView prbccard = v.findViewById(R.id.cardViewPRBC);
        final ExpandableCardView pccard = v.findViewById(R.id.cardViewPC);
        final ExpandableCardView ahfcard = v.findViewById(R.id.cardViewAHF);
        final ExpandableCardView ffpcard = v.findViewById(R.id.cardViewFFP);

        whbap=(EditText)v.findViewById(R.id.txtwhbAP);
        whban=(EditText)v.findViewById(R.id.txtwhbAN);
        whbbp=(EditText)v.findViewById(R.id.txtwhbBP);
        whbbn=(EditText)v.findViewById(R.id.txtwhbBN);
        whbabp=(EditText)v.findViewById(R.id.txtwhbABP);
        whbabn=(EditText)v.findViewById(R.id.txtwhbABN);
        whbop=(EditText)v.findViewById(R.id.txtwhbOP);
        whbon=(EditText)v.findViewById(R.id.txtwhbON);

        prbcap=(EditText)v.findViewById(R.id.txtprbcAP);
        prbcan=(EditText)v.findViewById(R.id.txtprbcAN);
        prbcbp=(EditText)v.findViewById(R.id.txtprbcBP);
        prbcbn=(EditText)v.findViewById(R.id.txtprbcBN);
        prbcabp=(EditText)v.findViewById(R.id.txtprbcABP);
        prbcabn=(EditText)v.findViewById(R.id.txtprbcABN);
        prbcop=(EditText)v.findViewById(R.id.txtprbcOP);
        prbcon=(EditText)v.findViewById(R.id.txtprbcON);

        pcap=(EditText)v.findViewById(R.id.txtpcAP);
        pcan=(EditText)v.findViewById(R.id.txtpcAN);
        pcbp=(EditText)v.findViewById(R.id.txtpcBP);
        pcbn=(EditText)v.findViewById(R.id.txtpcBN);
        pcabp=(EditText)v.findViewById(R.id.txtpcABP);
        pcabn=(EditText)v.findViewById(R.id.txtpcABN);
        pcop=(EditText)v.findViewById(R.id.txtpcOP);
        pcon=(EditText)v.findViewById(R.id.txtpcON);

        ahfap=(EditText)v.findViewById(R.id.txtahfAP);
        ahfan=(EditText)v.findViewById(R.id.txtahfAN);
        ahfbp=(EditText)v.findViewById(R.id.txtahfBP);
        ahfbn=(EditText)v.findViewById(R.id.txtahfBN);
        ahfabp=(EditText)v.findViewById(R.id.txtahfABP);
        ahfabn=(EditText)v.findViewById(R.id.txtahfABN);
        ahfop=(EditText)v.findViewById(R.id.txtahfOP);
        ahfon=(EditText)v.findViewById(R.id.txtahfON);

        ffpap=(EditText)v.findViewById(R.id.txtffpAP);
        ffpan=(EditText)v.findViewById(R.id.txtffpAN);
        ffpbp=(EditText)v.findViewById(R.id.txtffpBP);
        ffpbn=(EditText)v.findViewById(R.id.txtffpBN);
        ffpabp=(EditText)v.findViewById(R.id.txtffpABP);
        ffpabn=(EditText)v.findViewById(R.id.txtffpABN);
        ffpop=(EditText)v.findViewById(R.id.txtffpOP);
        ffpon=(EditText)v.findViewById(R.id.txtffpON);

        final DatabaseReference reff= FirebaseDatabase.getInstance().getReference().child("PacketTracker");

        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userid)){
                    BloodGroups WHB=dataSnapshot.child(userid).child("WholeHumanBlood").getValue(BloodGroups.class);
                    BloodGroups PRBC=dataSnapshot.child(userid).child("PackedRBC").getValue(BloodGroups.class);
                    BloodGroups PC=dataSnapshot.child(userid).child("PlateletConcentration").getValue(BloodGroups.class);
                    BloodGroups AHF=dataSnapshot.child(userid).child("AntiHaemophilicFactor").getValue(BloodGroups.class);
                    BloodGroups FFP=dataSnapshot.child(userid).child("FreshFrozenPlasma").getValue(BloodGroups.class);
                   
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updatedata= (Button) v.findViewById(R.id.update);
        
        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                whbapn=Integer.parseInt(whbap.getText().toString().trim());
                whbann=Integer.parseInt(whban.getText().toString().trim());
                whbbpn=Integer.parseInt(whbbp.getText().toString().trim());
                whbbnn=Integer.parseInt(whbbn.getText().toString().trim());
                whbabpn=Integer.parseInt(whbabp.getText().toString().trim());
                whbabnn=Integer.parseInt(whbabn.getText().toString().trim());
                whbopn=Integer.parseInt(whbop.getText().toString().trim());
                whbonn=Integer.parseInt(whbon.getText().toString().trim());

                prbcapn=Integer.parseInt(prbcap.getText().toString().trim());
                prbcann=Integer.parseInt(prbcan.getText().toString().trim());
                prbcbpn=Integer.parseInt(prbcbp.getText().toString().trim());
                prbcbnn=Integer.parseInt(prbcbn.getText().toString().trim());
                prbcabpn=Integer.parseInt(prbcabp.getText().toString().trim());
                prbcabnn=Integer.parseInt(prbcabn.getText().toString().trim());
                prbcopn=Integer.parseInt(prbcop.getText().toString().trim());
                prbconn=Integer.parseInt(prbcon.getText().toString().trim());

                pcapn=Integer.parseInt(pcap.getText().toString().trim());
                pcann=Integer.parseInt(pcan.getText().toString().trim());
                pcbpn=Integer.parseInt(pcbp.getText().toString().trim());
                pcbnn=Integer.parseInt(pcbn.getText().toString().trim());
                pcabpn=Integer.parseInt(pcabp.getText().toString().trim());
                pcabnn=Integer.parseInt(pcabn.getText().toString().trim());
                pcopn=Integer.parseInt(pcop.getText().toString().trim());
                pconn=Integer.parseInt(pcon.getText().toString().trim());

                ahfapn=Integer.parseInt(ahfap.getText().toString().trim());
                ahfann=Integer.parseInt(ahfan.getText().toString().trim());
                ahfbpn=Integer.parseInt(ahfbp.getText().toString().trim());
                ahfbnn=Integer.parseInt(ahfbn.getText().toString().trim());
                ahfabpn=Integer.parseInt(ahfabp.getText().toString().trim());
                ahfabnn=Integer.parseInt(ahfabn.getText().toString().trim());
                ahfopn=Integer.parseInt(ahfop.getText().toString().trim());
                ahfonn=Integer.parseInt(ahfon.getText().toString().trim());

                ffpapn=Integer.parseInt(ffpap.getText().toString().trim());
                ffpann=Integer.parseInt(ffpan.getText().toString().trim());
                ffpbpn=Integer.parseInt(ffpbp.getText().toString().trim());
                ffpbnn=Integer.parseInt(ffpbn.getText().toString().trim());
                ffpabpn=Integer.parseInt(ffpabp.getText().toString().trim());
                ffpabnn=Integer.parseInt(ffpabn.getText().toString().trim());
                ffpopn=Integer.parseInt(ffpop.getText().toString().trim());
                ffponn=Integer.parseInt(ffpon.getText().toString().trim());
                
                BloodGroups WHB=new BloodGroups(whbapn,whbann,whbbpn,whbbnn,whbabpn,whbabnn,whbopn,whbonn);
                BloodGroups PRBC=new BloodGroups(prbcapn,prbcann,prbcbpn,prbcbnn,prbcabpn,prbcabnn,prbcopn,prbconn);
                BloodGroups PC=new BloodGroups(pcapn,pcann,pcbpn,pcbnn,pcabpn,pcabnn,pcopn,pconn);
                BloodGroups AHF=new BloodGroups(ahfapn,ahfann,ahfbpn,ahfbnn,ahfabpn,ahfabnn,ahfopn,ahfonn);
                BloodGroups FFP=new BloodGroups(ffpapn,ffpann,ffpbpn,ffpbnn,ffpabpn,ffpabnn,ffpopn,ffponn);


                reff.child(userid).child("WholeHumanBlood").setValue(WHB)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flag=1;
                            }
                        });
                reff.child(userid).child("PackedRBC").setValue(PRBC)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flag=1;
                            }
                        });
                reff.child(userid).child("PlateletConcentration").setValue(PC)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flag=1;
                            }
                        });
                reff.child(userid).child("AntiHaemophilicFactor").setValue(AHF)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flag=1;
                            }
                        });
                reff.child(userid).child("FreshFrozenPlasma").setValue(FFP)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flag=1;
                            }
                        });
                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                reff.child(userid).child("LastUpdated").setValue(date)
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                flag=1;
                            }
                        });
                if(flag==1){
                    Toast.makeText(getActivity(),"Data Entry Failed!",Toast.LENGTH_LONG).show();
                }
                else{
                    if(whbcard.isExpanded())
                        whbcard.collapse();
                    if(prbccard.isExpanded())
                        prbccard.collapse();
                    if(pccard.isExpanded())
                        pccard.collapse();
                    if(ahfcard.isExpanded())
                        ahfcard.collapse();
                    if(ffpcard.isExpanded())
                        ffpcard.collapse();
                    Toast.makeText(getActivity(),"Data Updated Successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
