package org.demo.demo.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.demo.demo.services.AddFileService;

import java.io.File;
import java.util.List;

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

    @FXML
    private TableColumn<RowData, String> column3;

    @FXML
    private TableColumn<RowData, String> column4;

    @FXML
    private Button saveButton;


    private final ObservableList<RowData> tableData = FXCollections.observableArrayList();

    private final AddFileService addFileService = new AddFileService();

    public static class RowData {
        private final String col1;
        private final String col2;
        private final String col3;
        private final String col4;

        public RowData(String col1, String col2, String col3, String col4) {
            this.col1 = col1;
            this.col2 = col2;
            this.col3 = col3;
            this.col4 = col4;
        }

        public String getCol1() { return col1; }
        public String getCol2() { return col2; }
        public String getCol3() { return col3; }
        public String getCol4() { return col4; }
    }

    @FXML
    public void initialize() {
        column1.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCol1()));
        column2.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCol2()));
        column3.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCol3()));
        column4.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCol4()));
        tableView.setItems(tableData);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.setVisible(false);
        tableView.setManaged(false);

        saveButton.setVisible(false);
        saveButton.setManaged(false);
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
                tableData.clear();
                List<RowData> dataFromFile = addFileService.readExcelFile(filePath);
                tableData.addAll(dataFromFile);

                tableView.setVisible(true);
                tableView.setManaged(true);

                saveButton.setVisible(true);
                saveButton.setManaged(true);

                addFileService.copyFileToResources(filePath);

                Text.setText("Fichier ajouté et données extraites.");
                Text.setStyle("-fx-text-fill: green;");
            } catch (Exception e) {
                e.printStackTrace();
                Text.setText("Erreur lors de l'ajout du fichier.");
                Text.setStyle("-fx-text-fill: red;");
            }
        } else {
            Text.setText("Aucun fichier sélectionné.");
            Text.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    protected void onSaveToDatabaseClick() {
        try {
            String filePath = filePathField.getText();
            if (filePath == null || filePath.isEmpty()) {
                Text.setText("Veuillez d'abord sélectionner un fichier.");
                Text.setStyle("-fx-text-fill: red;");
                return;
            }

            int nbProduits = addFileService.saveToDatabase(filePath, tableData);
            Text.setText("✅ " + nbProduits + " produit(s) et le fichier ont été enregistrés avec succès !");
            Text.setStyle("-fx-text-fill: green;");
        } catch (Exception e) {
            e.printStackTrace();
            Text.setText("❌ Erreur lors de l'enregistrement des données.");
            Text.setStyle("-fx-text-fill: red;");
        }
    }
}