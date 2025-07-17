package org.demo.demo.services;

import javafx.collections.ObservableList;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.demo.demo.dao.FichierDAO;
import org.demo.demo.dao.ProduitExcelDAO;
import org.demo.demo.entities.Fichier;
import org.demo.demo.entities.ProduitExcel;
import org.demo.demo.controller.AddFileController.RowData;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class AddFileService {

    public List<RowData> readExcelFile(String filePath) throws IOException {
        List<RowData> tableData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;

            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IOException("Format de fichier non pris en charge.");
            }

            Sheet sheet = workbook.getSheetAt(2); // Troisième feuille
            int currentRow = 0;

            for (Row row : sheet) {
                if (currentRow >= 5) {
                    Cell col1 = row.getCell(0);
                    Cell col2 = row.getCell(1);
                    Cell col3 = row.getCell(148);
                    Cell col4 = row.getCell(149);

                    if (col2 != null && !col2.toString().trim().isEmpty()) {
                        String value1 = (col1 != null) ? col1.toString() : "";
                        String value2 = col2.toString();
                        String value3 = (col3 != null) ? col3.toString() : "";
                        String value4 = (col4 != null) ? col4.toString() : "";
                        tableData.add(new RowData(value1, value2, value3, value4));
                    }
                }
                currentRow++;
            }

            workbook.close();
        }

        return tableData;
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

        // ✅ Vérifier si le fichier a déjà été enregistré
        if (fichierDAO.existsByFilename(nomFichier)) {
            throw new Exception("Le fichier '" + nomFichier + "' a déjà été ajouté.");
        }

        Fichier fichier = new Fichier(nomFichier, typeFichier);
        int idFichier = fichierDAO.save(fichier);

        if (idFichier == -1) {
            throw new Exception("Échec de l'enregistrement du fichier.");
        }

        ProduitExcelDAO produitDAO = new ProduitExcelDAO();
        int compteur = 0;

        for (RowData row : tableData) {
            ProduitExcel produit = new ProduitExcel(
                    row.getCol1(),
                    row.getCol2(),
                    parseDoubleSafe(row.getCol3()),
                    parseDoubleSafe(row.getCol4()),
                    idFichier
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
