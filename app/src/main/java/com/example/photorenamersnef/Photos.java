package com.example.photorenamersnef;

public class Photos {

    private int id;
    private String titre;
    private String nom;
    private int prise;

    public Photos(){}

    public Photos(String titre, String nom, int prise) {
        this.titre = titre;
        this.nom = nom;
        this.prise = prise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
        this.prise = prise;
    }

    public String toString(){
        return "ID : "+id+"\nTitre : "+titre+"\nNom de la photo : "+nom+"\nPhoto prise : "+prise;
    }
}
