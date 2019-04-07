package com.example.photorenamersnef;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBDD extends SQLiteOpenHelper {
    private static final String TABLE_PHOTOS = "photos";
    private static final String COL_ID = "id";
    private static final String COL_TITRE = "titre";
    private static final String COL_NOM = "nom";
    private static final String COL_PRISE = "prise";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_PHOTOS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITRE + " TEXT NOT NULL, "
            + COL_NOM + " TEXT NOT NULL, " + COL_PRISE + " INTEGER NOT NULL);";

    public MaBDD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_PHOTOS + ";");
        onCreate(db);


    }
}
