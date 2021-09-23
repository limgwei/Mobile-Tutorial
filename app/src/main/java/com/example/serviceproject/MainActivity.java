package com.example.serviceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    Button start,stop,sendBroadcast,saveButton,loadButton;
    String filename = "example.txt";
    EditText textToUse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.buttonStart);
        stop = findViewById(R.id.buttonStop);
        sendBroadcast = findViewById(R.id.sendBroadcast);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        textToUse = findViewById(R.id.textToUse);

        saveButton.setOnClickListener(v->{
            String text = textToUse.getText().toString();
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(filename,MODE_PRIVATE);
                fos.write(text.getBytes());

                textToUse.getText().clear();
                Toast.makeText(this,"Saved to "+getFilesDir()+"/"+filename,Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        loadButton.setOnClickListener(v->{
            FileInputStream fis = null;
            try {
                fis = openFileInput(filename);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text=br.readLine())!=null){
                    sb.append(text).append("\n");
                }

                textToUse.setText(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fis!=null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter("com.example.myapplication");
        MyReceiver r = new MyReceiver();
        registerReceiver(r,intentFilter);

        start.setOnClickListener(v->{
            Intent i = new Intent(this,MyAndroidService.class);
            startService(i);
        });
        stop.setOnClickListener(v->{
            Intent i = new Intent(this,MyAndroidService.class);
            stopService(i);
        });
        sendBroadcast.setOnClickListener(v->{

            Intent i = new Intent();
//        i.putExtra("gg","passing value");
            i.setAction("com.example.myapplication");
            i.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(i);

        });


    }


}