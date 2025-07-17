package org.demo.demo.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.demo.demo.entities.ProduitExcel;
import org.demo.demo.entities.ProduitPdf;
import org.demo.demo.services.RechercheService;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

public class RechercheController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<ProduitExcel> resultTable;

    @FXML
    private TableColumn<ProduitExcel, String> nomColumn;

    @FXML
    private TableColumn<ProduitExcel, String> descriptionColumn;

    @FXML
    private TableColumn<ProduitExcel, String> nomFichierColumn;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<ProduitExcel, Double> protoColumn;

    @FXML
    private TableColumn<ProduitExcel, Double> serieColumn;

    // Table PDF
    @FXML
    private TableView<ProduitPdf> pdfResultTable;

    @FXML
    private TableColumn<ProduitPdf, String> pdfRefColumn;

    @FXML
    private TableColumn<ProduitPdf, String> pdfNomFichierColumn;

    @FXML
    private TableColumn<ProduitPdf, Void> pdfActionColumn;

    @FXML
    private Label loadingLabel;


    private RechercheService recherchService = new RechercheService();

    @FXML
    public void initialize() {
        // Table Excel
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomFichierColumn.setCellValueFactory(new PropertyValueFactory<>("nomFichier"));
        protoColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireProto"));
        serieColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireSerie"));
        resultTable.setVisible(false);

        // Table PDF
        pdfRefColumn.setCellValueFactory(new PropertyValueFactory<>("ref"));
        pdfNomFichierColumn.setCellValueFactory(new PropertyValueFactory<>("nomFichier"));
        addDownloadButtonToTable();
        pdfResultTable.setVisible(false);

        typeComboBox.setValue("Tout");

        resultTable.widthProperty().addListener((obs, oldVal, newVal) -> ajusterLargeurColonnes());
        pdfResultTable.widthProperty().addListener((obs, oldVal, newVal) -> ajusterLargeurColonnesPdf());
    }

    private void ajusterLargeurColonnes() {
        long colonnesVisibles = resultTable.getColumns().stream().filter(TableColumn::isVisible).count();
        if (colonnesVisibles == 0) return;
        double largeurColonne = resultTable.getWidth() / colonnesVisibles;
        for (TableColumn<?, ?> col : resultTable.getColumns()) {
            if (col.isVisible()) col.setPrefWidth(largeurColonne);
        }
    }

    private void ajusterLargeurColonnesPdf() {
        long colonnesVisibles = pdfResultTable.getColumns().stream().filter(TableColumn::isVisible).count();
        if (colonnesVisibles == 0) return;
        double largeurColonne = pdfResultTable.getWidth() / colonnesVisibles;
        for (TableColumn<?, ?> col : pdfResultTable.getColumns()) {
            if (col.isVisible()) col.setPrefWidth(largeurColonne);
        }
    }

    private void addDownloadButtonToTable() {
        pdfActionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<ProduitPdf, Void> call(final TableColumn<ProduitPdf, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Télécharger");

                    {
                        btn.setOnAction(event -> {
                            ProduitPdf produit = getTableView().getItems().get(getIndex());
                            String filename = produit.getNomFichier(); // doit exister dans ProduitPdf
                            File file = new File("src/main/resources/pdf/" + filename);
                            if (file.exists()) {
                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    showAlert("Erreur", "Impossible d'ouvrir le fichier PDF.");
                                }
                            } else {
                                showAlert("Fichier introuvable", "Le fichier " + filename + " est introuvable.");
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : btn);
                    }
                };
            }
        });
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onSearchClicked() {
        String keyword = searchField.getText();
        String type = typeComboBox.getValue();

        // Afficher le message de chargement
        loadingLabel.setVisible(true);

        // Exécuter en arrière-plan pour ne pas bloquer l'UI
        Task<Void> task = new Task<>() {
            List<ProduitExcel> results;
            List<ProduitPdf> pdfResults;

            @Override
            protected Void call() {
                results = recherchService.rechercherProduitsParDescription(keyword);
                pdfResults = recherchService.rechercherDansPDF(keyword);
                return null;
            }

            @Override
            protected void succeeded() {
                resultTable.setItems(FXCollections.observableArrayList(results));
                pdfResultTable.setItems(FXCollections.observableArrayList(pdfResults));

                if (!results.isEmpty()) {
                    resultTable.setVisible(true);
                    pdfResultTable.setVisible(false);

                    switch (type) {
                        case "Proto" -> {
                            protoColumn.setVisible(true);
                            serieColumn.setVisible(false);
                        }
                        case "Série" -> {
                            protoColumn.setVisible(false);
                            serieColumn.setVisible(true);
                        }
                        default -> {
                            protoColumn.setVisible(true);
                            serieColumn.setVisible(true);
                        }
                    }

                    ajusterLargeurColonnes();
                } else if (!pdfResults.isEmpty()) {
                    resultTable.setVisible(false);
                    pdfResultTable.setVisible(true);
                    ajusterLargeurColonnesPdf();
                } else {
                    resultTable.setVisible(false);
                    pdfResultTable.setVisible(false);
                }

                // Masquer le message
                loadingLabel.setVisible(false);
            }

            @Override
            protected void failed() {
                loadingLabel.setVisible(false);
                showAlert("Erreur", "Une erreur est survenue lors de la recherche.");
            }
        };

        // Lancer la tâche
        new Thread(task).start();
    }

}
