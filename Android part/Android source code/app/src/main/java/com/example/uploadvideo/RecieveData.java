package com.example.uploadvideo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RecieveData extends AppCompatActivity {

    private Button rbtn,gth,strBtn;
    private VideoView mvideoView;
    private TextView mtextView,minfo,ntextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar)));
        }

        Intent j=getIntent();

        strBtn=(Button)findViewById(R.id.Stress);
        rbtn=(Button)findViewById(R.id.ReadBtn);
        //mvideoView=(VideoView)findViewById(R.id.Video);
        mtextView=(TextView)findViewById(R.id.Rate);
        ntextView=(TextView)findViewById(R.id.heart);
        gth=(Button)findViewById(R.id.EndBtn);
        minfo=(TextView)findViewById(R.id.extraInfo);

        /*String uripath2="android.resource://"+getPackageName()+R.raw.gifhy;
        Uri uri2=Uri.parse(uripath2);
        mvideoView.setVideoURI(uri2);
        mvideoView.requestFocus();
        mvideoView.start();*/

        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                read_data();
               /* String uripath2="android.resource://com.example.appVideo/"+R.raw.gifhy;
                Uri uri2=Uri.parse(uripath2);
                mvideoView.setVideoURI(uri2);
                mvideoView.requestFocus();
                mvideoView.start();*/

            }
        });

        gth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoHomeActivity();
            }
        });

        strBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StressActivity();
            }
        });



    }

    private void GoHomeActivity() {
        Intent intent = new Intent(this,MainActivity.class);// New activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void StressActivity() {
        Intent intent = new Intent(this,StressData.class);// New activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void read_data(){

        BackgroundTask b=new BackgroundTask(this);
        b.execute();
    }


    public class BackgroundTask extends AsyncTask<Void,Void,Void> {

    Context c;
    String value;
    float rate;
    String a1="Normal Heart Rate!!";
    String b1="Heart rate too high! Consult a doctor";
    String c1="Heart rate too low! Consult a doctor";


    Handler h=new Handler();
    BackgroundTask(Context c){
        this.c=c;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String url = "http://192.168.43.6/AndroidFileUpload/uploads/test.txt";

        try {
            URL myurl = new URL(url);
            HttpURLConnection huc = (HttpURLConnection) myurl.openConnection();
            huc.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(huc.getInputStream()));

            value = reader.readLine();
            try{
                rate = Float.parseFloat(value);
            }catch(Exception e){
                e.printStackTrace();
            }


            h.post(new Runnable() {
                @Override
                public void run() {

                    mtextView.setText(value);
                    if (rate >= 60 && rate <= 100) {
                        minfo.setText(a1);
                    } else if (rate > 100 && rate <= 200) {
                        minfo.setText(b1);
                    } else if (rate < 60) {
                        minfo.setText(c1);
                    }

                    Toast.makeText(c, value, Toast.LENGTH_SHORT).show();
                }
            });


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
      }
    }
}
