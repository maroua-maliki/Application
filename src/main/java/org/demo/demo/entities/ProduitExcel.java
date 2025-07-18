package org.demo.demo.entities;

public class ProduitExcel {
    private int id;
    private String nom;
    private String description;
    private double prixUnitaireProto;
    private double prixUnitaireSerie;
    private Fichier fichier;

    public ProduitExcel(int id, String nom, String description, double prixUnitaireProto, double prixUnitaireSerie, Fichier fichier) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prixUnitaireProto = prixUnitaireProto;
        this.prixUnitaireSerie = prixUnitaireSerie;
        this.fichier = fichier;
    }

    public ProduitExcel(String nom, String description, double prixUnitaireProto, double prixUnitaireSerie, Fichier fichier) {
        this.nom = nom;
        this.description = description;
        this.prixUnitaireProto = prixUnitaireProto;
        this.prixUnitaireSerie = prixUnitaireSerie;
        this.fichier = fichier;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
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

    public Fichier getFichier() {
        return fichier;
    }

    // Setters
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

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public String getNomFichier() {
        if (fichier != null) {
            return fichier.getNom_fichier();  // adapte selon le getter exact de ta classe Fichier
        }
        return "";
    }
}

