package com.example.photosnef;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    private ArrayList<String> nomPhotos= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);


        nomPhotos.add("Photo1");
        nomPhotos.add("Photo2");
        nomPhotos.add("Photo3");
        nomPhotos.add("Photo4");
        nomPhotos.add("Photo5");
        nomPhotos.add("Photo6");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, nomPhotos);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                            Manifest.permission.CAMERA
                    }, 200);
                }
                else{
                    Toast.makeText(MainActivity.this, "On lance l'appareil photo", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(MainActivity.this, AppPhoto.class);
                    startActivity(myIntent);
                }

            }
        });


    }
}
