package org.demo.demo.entities;

import java.io.File;

public class ProduitPdf {
    private String ref;
    private String nomFichier;
    private File fichierSource; // Utilisé pour le bouton Télécharger

    public ProduitPdf(String ref, File fichierSource) {
        this.ref = ref;
        this.fichierSource = fichierSource;
        this.nomFichier = fichierSource.getName();
    }

    public String getRef() {
        return ref;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public File getFichierSource() {
        return fichierSource;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public void setFichierSource(File fichierSource) {
        this.fichierSource = fichierSource;
    }
}
