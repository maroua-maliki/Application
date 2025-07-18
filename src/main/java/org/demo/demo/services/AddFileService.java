package org.demo.demo.services;

import org.demo.demo.controller.AddFileController.RowData;
import org.demo.demo.dao.FichierDAO;
import org.demo.demo.dao.ProduitExcelDAO;
import org.demo.demo.entities.Fichier;
import org.demo.demo.entities.ProduitExcel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class AddFileService {

    private final ExcelReaderService excelReaderService = new ExcelReaderService();

    public List<RowData> lireExcel(String filePath) throws IOException {
        return excelReaderService.readExcelFile(filePath);
    }

    public void copyFileToResources(String filePath) throws IOException {
        Path resourcesDir = Path.of("src/main/resources/excel");
        File sourceFile = new File(filePath);
        Path destinationFile = resourcesDir.resolve(sourceFile.getName());
        Files.copy(sourceFile.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public int saveToDatabase(String filePath, List<RowData> tableData) throws Exception {
        String nomFichier = new File(filePath).getName();
        String typeFichier = nomFichier.substring(nomFichier.lastIndexOf(".") + 1);

        FichierDAO fichierDAO = new FichierDAO();

        if (fichierDAO.existsByFilename(nomFichier)) {
            throw new Exception("Le fichier a déjà été ajouté.");
        }

        Fichier fichier = new Fichier(nomFichier, typeFichier);
        int idFichier = fichierDAO.save(fichier);
        fichier.setId(idFichier);  // Mise à jour avec l’ID généré

        ProduitExcelDAO produitDAO = new ProduitExcelDAO();
        int compteur = 0;

        for (RowData row : tableData) {
            ProduitExcel produit = new ProduitExcel(
                    row.getCol1(),
                    row.getCol2(),
                    parseDoubleSafe(row.getCol3()),
                    parseDoubleSafe(row.getCol4()),
                    fichier
            );
            produitDAO.save(produit);
            compteur++;
        }

        return compteur;
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
