package com.example.android.bbtracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView bbname, bbphoneno, bbaddress, bbemail;
    String userid;
    RelativeLayout relativeParentLayout;
    ProgressBar progressBar;
    ToggleButton emailToggleButton, phoneToggleButton;
    EditText editTextPhone, editTextEmail;
    View viewEmail;

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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);

//        relativeParentLayout = v.findViewById(R.id.tab1ParentLayout);
//        relativeParentLayout.setVisibility(View.GONE);
        progressBar = v.findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);

        bbname = (TextView) v.findViewById(R.id.textHospitalName);
        bbaddress = (TextView) v.findViewById(R.id.textAddress);
        bbphoneno = (TextView) v.findViewById(R.id.textMobileNo);
        bbemail = (TextView) v.findViewById(R.id.textEmailId);

        editTextPhone = v.findViewById(R.id.editTextPhone);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        emailToggleButton = v.findViewById(R.id.emailToggleButton);
        phoneToggleButton = v.findViewById(R.id.phoneToggleButton);

        viewEmail = v.findViewById(R.id.viewEmail);

        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("BloodBankDetails");
        final FirebaseAuth userreff=FirebaseAuth.getInstance();

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BBDetails bb = dataSnapshot.child(userid).getValue(BBDetails.class);
//                relativeParentLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                bbname.setText(bb.Name);
                bbaddress.setText(bb.Address);
                String tempNumber = bb.PhoneNumber;
                if (tempNumber.isEmpty())
                    tempNumber = "NA";
                bbphoneno.setText(tempNumber);
                bbemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        phoneToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    String phoneno=bbphoneno.getText().toString().trim();
                    editTextPhone.setText(phoneno);
                    bbphoneno.setVisibility(View.INVISIBLE);
                    editTextPhone.setVisibility(View.VISIBLE);
                    phoneToggleButton.setBackgroundResource(R.drawable.tick);

                } else {

                    final String phoneno=editTextPhone.getText().toString().trim();
                    String phonePattern="[0-9]{10}";
                    if(!phoneno.matches(phonePattern)){
                        Toast.makeText(getActivity(),"Invalid phone number!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        reff.child(userreff.getCurrentUser().getUid()).child("PhoneNumber").setValue(phoneno).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Phone number changed successfully!", Toast.LENGTH_LONG).show();
                                    bbphoneno.setText(phoneno);
                                    bbphoneno.setVisibility(View.VISIBLE);
                                    editTextPhone.setVisibility(View.GONE);
                                    phoneToggleButton.setBackgroundResource(R.drawable.edit_text_icon);

                                } else {
                                    Toast.makeText(getActivity(), "Failed to update phone number!", Toast.LENGTH_LONG).show();
                                    bbphoneno.setVisibility(View.VISIBLE);
                                    editTextPhone.setVisibility(View.GONE);
                                    phoneToggleButton.setBackgroundResource(R.drawable.edit_text_icon);

                                }
                            }
                        });
                    }
                }
            }
        });

        emailToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    String email=bbemail.getText().toString().trim();
                    editTextEmail.setText(email);
                    bbemail.setVisibility(View.INVISIBLE);
                    viewEmail.setVisibility(View.GONE);
                    editTextEmail.setVisibility(View.VISIBLE);
                    emailToggleButton.setBackgroundResource(R.drawable.tick);
                }else {
                    final String email=editTextEmail.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if(!email.matches(emailPattern)){
                        Toast.makeText(getActivity(),"Invalid EmailID!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        userreff.getCurrentUser().updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Email changed successfully!", Toast.LENGTH_LONG).show();
                                            bbemail.setText(email);
                                            bbemail.setVisibility(View.VISIBLE);
                                            viewEmail.setVisibility(View.VISIBLE);
                                            editTextEmail.setVisibility(View.GONE);
                                            emailToggleButton.setBackgroundResource(R.drawable.edit_text_icon);

                                        } else {
                                            Toast.makeText(getActivity(), "Failed to update email!", Toast.LENGTH_LONG).show();
                                            bbemail.setVisibility(View.VISIBLE);
                                            viewEmail.setVisibility(View.VISIBLE);
                                            editTextEmail.setVisibility(View.GONE);
                                            emailToggleButton.setBackgroundResource(R.drawable.edit_text_icon);

                                        }
                                    }
                                });
                    }
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
