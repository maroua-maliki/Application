package org.demo.demo.services;

import org.demo.demo.dao.ProduitExcelDAO;
import org.demo.demo.dao.PdfExtraitDAO;
import org.demo.demo.entities.ProduitExcel;
import org.demo.demo.entities.PdfExtrait;

import java.util.List;

public class RechercheService {

    private final ProduitExcelDAO produitExcelDAO = new ProduitExcelDAO();
    private final PdfExtraitDAO pdfExtraitDAO = new PdfExtraitDAO();

    public List<ProduitExcel> rechercherProduitsParDescription(String description) {
        return produitExcelDAO.rechercherParDescription(description);
    }

    public List<PdfExtrait> rechercherDansExtraitsPDF(String motCle) {
        try {
            return pdfExtraitDAO.rechercherParContenu(motCle);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // liste vide en cas d’erreur
        }
    }
}
