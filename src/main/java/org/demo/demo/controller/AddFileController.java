package org.demo.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AddFileController {

    @FXML
    private Label Text;

    @FXML
    private TextField filePathField;

    @FXML
    private TableView<RowData> tableView;

    @FXML
    private TableColumn<RowData, String> column1;

    @FXML
    private TableColumn<RowData, String> column2;

    private final ObservableList<RowData> tableData = FXCollections.observableArrayList();

    // Classe représentant une ligne du tableau
    public static class RowData {
        private final String col1;
        private final String col2;

        public RowData(String col1, String col2) {
            this.col1 = col1;
            this.col2 = col2;
        }

        public String getCol1() {
            return col1;
        }

        public String getCol2() {
            return col2;
        }
    }

    // Initialisation des colonnes du tableau
    @FXML
    public void initialize() {
        column1.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCol1()));
        column2.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCol2()));
        tableView.setItems(tableData);
    }

    @FXML
    protected void onDownloadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            filePathField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    protected void onAddButtonClick() {
        String filePath = filePathField.getText();

        if (filePath != null && !filePath.isEmpty()) {
            try {
                // Lire le fichier et remplir le tableau
                readExcelFile(filePath);

                // Déplacer le fichier dans le dossier resources
                Path resourcesDir = Path.of("src/main/resources");
                File sourceFile = new File(filePath);
                Path destinationFile = resourcesDir.resolve(sourceFile.getName());
                Files.copy(sourceFile.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

                Text.setText("Fichier ajouté et données extraites.");

            } catch (IOException e) {
                e.printStackTrace();
                Text.setText("Erreur lors de l'ajout du fichier.");
            }
        } else {
            Text.setText("Aucun fichier sélectionné.");
        }
    }

    private void readExcelFile(String filePath) throws IOException {
        tableData.clear();

        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;

            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IOException("Format de fichier non pris en charge. Veuillez sélectionner un fichier Excel (.xls ou .xlsx).");
            }

            Sheet sheet = workbook.getSheetAt(2); // Troisième feuille

            int currentRow = 0;
            for (Row row : sheet) {
                if (currentRow >= 5) { // À partir de la ligne 6
                    Cell col1 = row.getCell(0);
                    Cell col2 = row.getCell(1);

                    if (col2 != null && !col2.toString().trim().isEmpty()) {
                        String value1 = (col1 != null) ? col1.toString() : "";
                        String value2 = col2.toString();
                        tableData.add(new RowData(value1, value2));
                    }
                }
                currentRow++;
            }

            workbook.close();
        }
    }
}
