package org.demo.demo.entities;

public class Produit {
    private int id;
    private String nom;
    private String des;
    private double prixUnitaireProto;
    private double prixUnitaireSerie;
    private int idFichier;
    private String nomFichier; // 🆕 ajouté pour afficher dans TableView

    public Produit(int id, int idFichier, double prixUnitaireSerie, double prixUnitaireProto, String description, String nom) {
        this.id = id;
        this.idFichier = idFichier;
        this.prixUnitaireSerie = prixUnitaireSerie;
        this.prixUnitaireProto = prixUnitaireProto;
        this.des = description;
        this.nom = nom;
    }

    public Produit(String nom, String des, double prixUnitaireProto, double prixUnitaireSerie, int idFichier) {
        this.nom = nom;
        this.des = des;
        this.prixUnitaireProto = prixUnitaireProto;
        this.prixUnitaireSerie = prixUnitaireSerie;
        this.idFichier = idFichier;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return des;
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

    public String getNomFichier() {
        return nomFichier;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.des = description;
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

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
}
