package com.example.android.bbtracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class BBListAdapter extends ArrayAdapter<LastUpdate> {

    private Context mContext;
    int mResource;
    private int PHONE_PERMISSION_CODE =1;

    public BBListAdapter(Context context, int resource, List<LastUpdate> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    //    public BBListAdapter(@NonNull Context context, int resource, @NonNull List<LastUpdate> objects) {
//        super(context, resource, objects);
//        this.mContext = mContext;
//        this.mResource=resource;
//
//    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String bbname = getItem(position).getBBName();
        String date = getItem(position).getDate();

        String currDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        LastUpdate lastUpdate = new LastUpdate(bbname, date);

        Log.d("Date", "getView:Last Date " + date + " Curren Date" + currDate);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvbbname = (TextView) convertView.findViewById(R.id.tvbbname);
        TextView tvdate = (TextView) convertView.findViewById(R.id.tvlastupdated);

        String[] prevDateArr = date.split("-");
        String[] currDateArr = currDate.split("-");
        Log.d("Separate Value", "compareDate: " + prevDateArr[0] + "--" + prevDateArr[1] + "--" + prevDateArr[2]);
        int Hr24 = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int yearDifference = Math.abs(Integer.parseInt(prevDateArr[2]) - Integer.parseInt(currDateArr[2]));
        int monthDifference = Math.abs(Integer.parseInt(prevDateArr[1]) - Integer.parseInt(currDateArr[1]));
        int dayDifference = Math.abs(Integer.parseInt(prevDateArr[0]) - Integer.parseInt(currDateArr[0]));
        if (yearDifference > 0) {
            tvdate.setTextColor(Color.parseColor("#ff3341"));
            tvdate.setTypeface(null, Typeface.BOLD);
        } else if (monthDifference > 0) {
            tvdate.setTextColor(Color.parseColor("#ff3341"));
            tvdate.setTypeface(null, Typeface.BOLD);
        } else if (dayDifference > 1) {
            tvdate.setTextColor(Color.parseColor("#ff3341"));
            tvdate.setTypeface(null, Typeface.BOLD);
        } else if ((dayDifference == 1 && Hr24 >= 11)) {
            tvdate.setTextColor(Color.parseColor("#ff3341"));
            tvdate.setTypeface(null, Typeface.BOLD);
        } else {
            tvdate.setTextColor(Color.parseColor("#A9A9A9"));
            tvdate.setTypeface(null, Typeface.NORMAL);
        }

        //Log.d("Flag", "getView: "+flag +" HR:"+(Hr24>11)+ "24Hr"+Hr24);

        Button call = (Button) convertView.findViewById(R.id.imagecall);
        final int tempPos = position;
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), Integer.toString(DisplayDB.tempphoneno.get(tempPos)), Toast.LENGTH_LONG).show();
                String number = Integer.toString(MainActivity.tempphoneno.get(tempPos));
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel: 9892836216" ));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //Request for permission
                    ActivityCompat.requestPermissions((Activity) getContext(), new String[] {Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_CODE);

                    return;
                }
                getContext().startActivity(callIntent);
            }
        });

        tvbbname.setText(bbname);
        tvdate.setText(date);
        return convertView;
    }


}
