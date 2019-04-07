package com.example.photorenamersnef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhotosBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "photos.db";

    private static final String TABLE_PHOTOS = "photos";
    private static final String COL_ID = "id";
    private static final int NUM_COL_ID = 0;
    private static final String COL_TITRE = "titre";
    private static final int NUM_COL_TITRE = 1;
    private static final String COL_NOM = "nom";
    private static final int NUM_COL_NOM = 2;
    private static final String COL_PRISE = "prise";
    private static final int NUM_COL_PRISE = 3;

    private SQLiteDatabase bdd;

    private MaBDD maBaseSQLite;

    public PhotosBDD(Context context){
        //On crée la BDD et sa table
        maBaseSQLite = new MaBDD(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertPhoto(Photos photos){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_TITRE, photos.getTitre());
        values.put(COL_NOM, photos.getNom());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_PHOTOS, null, values);
    }

    public int updatePhoto(int id, Photos photo){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_TITRE, photo.getTitre());
        values.put(COL_NOM, photo.getNom());
        values.put(COL_PRISE, photo.getPrise());
        return bdd.update(TABLE_PHOTOS, values, COL_ID + " = " +id, null);
    }

    public int removePhotoWithID(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE_PHOTOS, COL_ID + " = " +id, null);
    }

    public Photos getPhotoWithTitre(String titre){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE_PHOTOS, new String[] {COL_ID, COL_TITRE , COL_NOM, COL_PRISE}, COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);
        return cursorToPhoto(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Photos cursorToPhoto(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();
        //On créé un livre
        Photos photo = new Photos();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        photo.setId(c.getInt(NUM_COL_ID));
        photo.setTitre(c.getString(NUM_COL_TITRE));
        photo.setNom(c.getString(NUM_COL_NOM));
        photo.setPrise(c.getInt(NUM_COL_PRISE));
        //On ferme le cursor
        c.close();

        //On retourne le livre
        return photo;
    }
}
