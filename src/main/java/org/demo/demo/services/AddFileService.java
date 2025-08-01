package org.demo.demo.services;

import org.demo.demo.dao.FichierDAO;
import org.demo.demo.dao.ProduitExcelDAO;
import org.demo.demo.dao.PdfExtraitDAO;
import org.demo.demo.entities.Fichier;
import org.demo.demo.entities.ProduitExcel;
import org.demo.demo.entities.PdfExtrait;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class AddFileService {

    private final ExcelReaderService excelReaderService = new ExcelReaderService();
    private final PdfReaderService pdfReaderService = new PdfReaderService();
    private final FichierDAO fichierDAO = new FichierDAO();

    public Path copyFileToResources(String filePath) throws IOException {
        String extension = getExtension(filePath);

        String subFolder;
        switch (extension) {
            case "xlsx":
            case "xls":
            case "xlsm":
                subFolder = "excel";
                break;
            case "pdf":
                subFolder = "PDFs";
                break;
            default:
                throw new IOException("Extension non supportée pour la copie : " + extension);
        }


        Path resourcesDir = Path.of("src/main/resources", subFolder);
        Files.createDirectories(resourcesDir);

        File sourceFile = new File(filePath);
        Path destinationFile = resourcesDir.resolve(sourceFile.getName());

        Files.copy(sourceFile.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return destinationFile;
    }


    public String saveFichierVersBD(String filePath) throws Exception {
        String nomFichier = new File(filePath).getName();
        String extension = getExtension(filePath);

        if (fichierDAO.existsByFilename(nomFichier)) {
            throw new Exception("Le fichier a déjà été ajouté.");
        }

        Path copiedPath = copyFileToResources(filePath);

        Fichier fichier = new Fichier(nomFichier, extension, copiedPath.toString());
        int idFichier = fichierDAO.save(fichier);
        fichier.setId(idFichier);

        if (extension.equals("xlsx") || extension.equals("xls") || extension.equals("xlsm")) {
            List<ProduitExcel> produits = excelReaderService.readProduitExcel(filePath, fichier);
            ProduitExcelDAO produitDAO = new ProduitExcelDAO();
            for (ProduitExcel produit : produits) {
                produitDAO.save(produit);
            }
            return produits.size() + " produit(s) enregistrés depuis Excel.";
        } else if (extension.equals("pdf")) {
            // Traitement PDF
            String textePdf = pdfReaderService.extraireTexteCompletAvecOCR(new File(filePath));
            PdfExtrait extrait = new PdfExtrait();
            extrait.setFichier(fichier);
            extrait.setContenu(textePdf);
            new PdfExtraitDAO().save(extrait);
            return "PDF analysé et extrait enregistré avec succès.";
        } else {
            throw new Exception("Type de fichier non supporté : " + extension);
        }
    }

    private String getExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }
}
