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
    public final static String EXTRA_MESSAGE =
            "com.example.photosnef.MESSAGE";
    ListView mListView;
    private ArrayList<String> Photos= new ArrayList<>();
    private ArrayList<String> nomPhotos= new ArrayList<>();
    private int idItemClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);


        Photos.add("Vue d'ensemble site A");
        Photos.add("Vue d'ensemble site B");
        Photos.add("Accessibilité ODU Site A");
        Photos.add("Accessibilité ODU Site B");
        nomPhotos.add("A2");
        nomPhotos.add("B2");
        nomPhotos.add("A3");
        nomPhotos.add("B3");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, Photos);
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
                    idItemClicked=(int)id;
                    Intent myIntent = new Intent(MainActivity.this, AppPhoto.class);
                    myIntent.putExtra(EXTRA_MESSAGE, nomPhotos.get(idItemClicked));
                    startActivityForResult(myIntent, 0);
                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( resultCode==0 ) {
            String s = data.getStringExtra(EXTRA_MESSAGE);
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
