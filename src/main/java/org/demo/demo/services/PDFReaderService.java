package org.demo.demo.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.demo.demo.entities.ProduitPdf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFReaderService {

    public List<ProduitPdf> rechercherProduitsDansPDF(String reference, File dossierPDF) {
        List<ProduitPdf> produitsTrouves = new ArrayList<>();

        File[] fichiers = dossierPDF.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));
        if (fichiers == null) return produitsTrouves;

        for (File fichier : fichiers) {
            try (PDDocument document = PDDocument.load(fichier)) {
                PDFTextStripper stripper = new PDFTextStripper();
                String texte = stripper.getText(document);

                if (texte.contains(reference)) {
                    produitsTrouves.add(new ProduitPdf(reference, fichier));
                }

            } catch (Exception e) {
                System.err.println("Erreur lecture PDF : " + fichier.getName());
                e.printStackTrace();
            }
        }

        return produitsTrouves;
    }
}
