package com.example.photorenamersnef;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
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

        /*Photos.add("Vue d'ensemble site A");
        Photos.add("Vue d'ensemble site B");
        Photos.add("Localisation des aériens Site A");
        Photos.add("Localisation des aériens Site B");
        Photos.add("Accessibilité ODU Site A");
        Photos.add("Accessibilité ODU Site B");
        Photos.add("Vue d'ensemble IDU site A");
        Photos.add("Vue d'ensemble IDU site B");
        Photos.add("Etiquette IDU site A");
        Photos.add("Etiquette IDU site B");
        Photos.add("Disjoncteur site A");
        Photos.add("Disjoncteur site B");
        Photos.add("Terre raccordement ODU site A");
        Photos.add("Terre raccordement ODU site B");
        Photos.add("Kit de Terre coax Site A");
        Photos.add("Kit de Terre coax Site B");
        Photos.add("Terre IDU Site A");
        Photos.add("Terre IDU Site B");
        Photos.add("Etiquette Haute Site A");
        Photos.add("Etiquette Haute Site B");
        Photos.add("Connecteur + bretelle BASSE Site A");
        Photos.add("Connecteur + bretelle BASSE Site B");
        Photos.add("Terre ODU Site A");
        Photos.add("Terre ODU Site B");
        Photos.add("Guide d'onde Site A");
        Photos.add("Guide d'onde Site B");
        Photos.add("Préssurisateur Site A");
        Photos.add("Préssurisateur Site B");*/

        /*nomPhotos.add("1A");
        nomPhotos.add("1B");
        nomPhotos.add("2A");
        nomPhotos.add("2B");
        nomPhotos.add("3A");
        nomPhotos.add("3B");
        nomPhotos.add("4A");
        nomPhotos.add("4B");
        nomPhotos.add("5A");
        nomPhotos.add("5B");
        nomPhotos.add("6A");
        nomPhotos.add("6B");
        nomPhotos.add("12A");
        nomPhotos.add("12B");
        nomPhotos.add("7A");
        nomPhotos.add("7B");
        nomPhotos.add("8A");
        nomPhotos.add("8B");
        nomPhotos.add("9A");
        nomPhotos.add("9B");
        nomPhotos.add("10A");
        nomPhotos.add("10B");
        nomPhotos.add("11A");
        nomPhotos.add("11B");
        nomPhotos.add("13A");
        nomPhotos.add("13B");
        nomPhotos.add("14A");
        nomPhotos.add("14B");*/

        PhotosBDD photosBDD = new PhotosBDD(this);

        photosBDD.open();

        initialValuedBDD(photosBDD);

        //Remplir les listes photos et nomPhotos avec les valeurs dans la base de données.

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
                    Intent myIntent = new Intent(MainActivity.this, TakePictures.class);
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

            //Ouvrir la base de données pour changer le champ prise en 1 pour pouvoir changer dynamiquement la couleur de la liste si une photo est prise

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initialValuedBDD(PhotosBDD photoBDD){
        Photos photo1 = new Photos("Vue d'ensemble site A", "1A", 0);
        Photos photo2 = new Photos("Vue d'ensemble site B", "1B", 0);
        Photos photo3 = new Photos("Localisation des aériens Site A", "2A", 0);
        Photos photo4 = new Photos("Localisation des aériens Site B", "2B", 0);

        photoBDD.insertPhoto(photo1);
        photoBDD.insertPhoto(photo2);
        photoBDD.insertPhoto(photo3);
        photoBDD.insertPhoto(photo4);

    }
}
