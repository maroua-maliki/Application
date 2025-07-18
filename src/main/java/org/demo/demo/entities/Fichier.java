package org.demo.demo.entities;

public class Fichier {
    private int id ;
    private String nom_fichier;
    private String type_fichier;

    public Fichier(){

    }

    public Fichier(int id, String nom_fichier, String type_fichier) {
        this.id = id;
        this.nom_fichier = nom_fichier;
        this.type_fichier = type_fichier;
    }

    public Fichier(String nom_fichier, String type_fichier) {
        this.nom_fichier = nom_fichier;
        this.type_fichier = type_fichier;
    }

    public int getId() {
        return id;
    }

    public String getNom_fichier() {
        return nom_fichier;
    }

    public String getType_fichier() {
        return type_fichier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom_fichier(String nom_fichier) {
        this.nom_fichier = nom_fichier;
    }

    public void setType_fichier(String type_fichier) {
        this.type_fichier = type_fichier;
    }
}
