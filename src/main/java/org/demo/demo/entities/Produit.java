package org.demo.demo.entities;

public class Produit {
    private int id;
    private String nom;
    private String description;
    private double prixUnitaireProto;
    private double prixUnitaireSerie;
    private int idFichier;

    public Produit(int id, int idFichier, double prixUnitaireSerie, double prixUnitaireProto, String description, String nom) {
        this.id = id;
        this.idFichier = idFichier;
        this.prixUnitaireSerie = prixUnitaireSerie;
        this.prixUnitaireProto = prixUnitaireProto;
        this.description = description;
        this.nom = nom;
    }

    public Produit(String nom, String description, double prixUnitaireProto, double prixUnitaireSerie, int idFichier) {
        this.nom = nom;
        this.description = description;
        this.prixUnitaireProto = prixUnitaireProto;
        this.prixUnitaireSerie = prixUnitaireSerie;
        this.idFichier = idFichier;
    }


    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrixUnitaireProto() {
        return prixUnitaireProto;
    }

    public double getPrixUnitaireSerie() {
        return prixUnitaireSerie;
    }

    public int getIdFichier() {
        return idFichier;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrixUnitaireProto(double prixUnitaireProto) {
        this.prixUnitaireProto = prixUnitaireProto;
    }

    public void setPrixUnitaireSerie(double prixUnitaireSerie) {
        this.prixUnitaireSerie = prixUnitaireSerie;
    }

    public void setIdFichier(int idFichier) {
        this.idFichier = idFichier;
    }
}
