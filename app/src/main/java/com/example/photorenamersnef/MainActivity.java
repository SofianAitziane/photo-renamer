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
    public final static String ID_ITEM_CLICKED =
            "com.example.photosnef.MESSAGE";
    ListView mListView;
    private ArrayList<String> Photos= new ArrayList<>();
    private ArrayList<String> nomPhotos= new ArrayList<>();
    private int idItemClicked;
    private PhotosBDD photosBDD;
    private Photos capturePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);

        photosBDD = new PhotosBDD(this);

        photosBDD.open();

        //Remplir les listes photos et nomPhotos avec les valeurs dans la base de données.
        Photos = photosBDD.titrePhotos();
        nomPhotos = photosBDD.nomPhotos();

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
                    myIntent.putExtra(ID_ITEM_CLICKED, nomPhotos.get(idItemClicked));
                    startActivityForResult(myIntent, 0);
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( resultCode==0 ) {
            String s = data.getStringExtra(EXTRA_MESSAGE);
            //Ouvrir la base de données pour changer le champ prise en 1 pour pouvoir changer dynamiquement la couleur de la liste si une photo est prise

            capturePhoto = photosBDD.getPhotoWithNom(s);

            capturePhoto.setPrise(1);
            int ID_Item = capturePhoto.getId();

            Toast.makeText(this, capturePhoto.toString(), Toast.LENGTH_SHORT).show();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initialValuedBDD(PhotosBDD photoBDD){
        Photos photo1 = new Photos("Vue d'ensemble site A", "1A", 0);
        Photos photo2 = new Photos("Vue d'ensemble site B", "1B", 0);
        Photos photo3 = new Photos("Localisation des aériens Site A", "2A", 0);
        Photos photo4 = new Photos("Localisation des aériens Site B", "2B", 0);
        Photos photo5 = new Photos("Accessibilité ODU Site A", "3A", 0);
        Photos photo6 = new Photos("Accessibilité ODU Site B", "3B", 0);
        Photos photo7 = new Photos("Vue d'ensemble IDU site A", "4A", 0);
        Photos photo8 = new Photos("Vue d'ensemble IDU site B", "4B", 0);
        Photos photo9 = new Photos("Etiquette IDU site A", "5A", 0);
        Photos photo10 = new Photos("Etiquette IDU site B", "5B", 0);
        Photos photo11 = new Photos("Disjoncteur site A", "6A", 0);
        Photos photo12 = new Photos("Disjoncteur site B", "6B", 0);
        Photos photo13 = new Photos("Terre raccordement ODU site A", "12A", 0);
        Photos photo14 = new Photos("Terre raccordement ODU site B", "12B", 0);
        Photos photo15 = new Photos("Kit de Terre coax Site A", "7A", 0);
        Photos photo16 = new Photos("Kit de Terre coax Site B", "7B", 0);
        Photos photo17 = new Photos("Terre IDU Site A", "8A", 0);
        Photos photo18 = new Photos("Terre IDU Site B", "8B", 0);
        Photos photo19 = new Photos("Etiquette Haute Site A", "9A", 0);
        Photos photo20 = new Photos("Etiquette Haute Site B", "9B", 0);
        Photos photo21 = new Photos("Connecteur + bretelle BASSE Site A", "10A", 0);
        Photos photo22 = new Photos("Connecteur + bretelle BASSE Site B", "10B", 0);
        Photos photo23 = new Photos("Terre ODU Site A", "11A", 0);
        Photos photo24 = new Photos("Terre ODU Site B", "11B", 0);
        Photos photo25 = new Photos("Guide d'onde Site A", "13A", 0);
        Photos photo26 = new Photos("Guide d'onde Site B", "13B", 0);
        Photos photo27 = new Photos("Préssurisateur Site A", "14A", 0);
        Photos photo28 = new Photos("Préssurisateur Site B", "14B", 0);


        photoBDD.insertPhoto(photo1);
        photoBDD.insertPhoto(photo2);
        photoBDD.insertPhoto(photo3);
        photoBDD.insertPhoto(photo4);
        photoBDD.insertPhoto(photo5);
        photoBDD.insertPhoto(photo6);
        photoBDD.insertPhoto(photo7);
        photoBDD.insertPhoto(photo8);
        photoBDD.insertPhoto(photo9);
        photoBDD.insertPhoto(photo10);
        photoBDD.insertPhoto(photo11);
        photoBDD.insertPhoto(photo12);
        photoBDD.insertPhoto(photo13);
        photoBDD.insertPhoto(photo14);
        photoBDD.insertPhoto(photo15);
        photoBDD.insertPhoto(photo16);
        photoBDD.insertPhoto(photo17);
        photoBDD.insertPhoto(photo18);
        photoBDD.insertPhoto(photo19);
        photoBDD.insertPhoto(photo20);
        photoBDD.insertPhoto(photo21);
        photoBDD.insertPhoto(photo22);
        photoBDD.insertPhoto(photo23);
        photoBDD.insertPhoto(photo24);
        photoBDD.insertPhoto(photo25);
        photoBDD.insertPhoto(photo26);
        photoBDD.insertPhoto(photo27);
        photoBDD.insertPhoto(photo28);


    }
}
