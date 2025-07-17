package org.demo.demo.services;

import org.demo.demo.dao.ProduitExcelDAO;
import org.demo.demo.entities.ProduitExcel;
import org.demo.demo.entities.ProduitPdf;

import java.io.File;
import java.util.List;

public class RechercheService {

    private ProduitExcelDAO produitExcelDAO = new ProduitExcelDAO();

    public List<ProduitExcel> rechercherProduitsParDescription(String description) {
        return produitExcelDAO.rechercherParDescription(description);
    }

    public List<ProduitPdf> rechercherDansPDF(String reference) {
        File dossierPDF = new File("src\\main\\resources\\pdf"); // ou Path dynamique
        PDFReaderService pdfService = new PDFReaderService();
        return pdfService.rechercherProduitsDansPDF(reference, dossierPDF);
    }

}
