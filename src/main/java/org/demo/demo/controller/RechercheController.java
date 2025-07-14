package org.demo.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import org.demo.demo.entities.Produit;
import org.demo.demo.services.RechercheService;

import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class RechercheController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Produit> resultTable;

    @FXML
    private TableColumn<Produit, String> nomColumn;

    @FXML
    private TableColumn<Produit, String> descriptionColumn;

    @FXML
    private TableColumn<Produit, String> nomFichierColumn;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<Produit, Double> protoColumn;

    @FXML
    private TableColumn<Produit, Double> serieColumn;


    private RechercheService recherchService = new RechercheService();

    @FXML
    public void initialize() {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomFichierColumn.setCellValueFactory(new PropertyValueFactory<>("nomFichier"));
        protoColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireProto"));
        serieColumn.setCellValueFactory(new PropertyValueFactory<>("prixUnitaireSerie"));

        // Cacher au début
        resultTable.setVisible(false);

        // Valeurs ComboBox par défaut si nécessaire
        typeComboBox.setValue("Tout");

        resultTable.widthProperty().addListener((obs, oldVal, newVal) -> ajusterLargeurColonnes());

    }

    private void ajusterLargeurColonnes() {
        // Compter le nombre de colonnes visibles
        long colonnesVisibles = resultTable.getColumns().stream().filter(TableColumn::isVisible).count();

        if (colonnesVisibles == 0) return;

        double largeurColonne = resultTable.getWidth() / colonnesVisibles;

        for (TableColumn<?, ?> col : resultTable.getColumns()) {
            if (col.isVisible()) {
                col.setPrefWidth(largeurColonne);
            }
        }
    }



    @FXML
    private void onSearchClicked() {
        String keyword = searchField.getText();
        String type = typeComboBox.getValue();

        List<Produit> results = recherchService.rechercherProduitsParDescription(keyword);

        // Afficher ou masquer les colonnes selon le filtre
        switch (type) {
            case "Tout":
                protoColumn.setVisible(true);
                serieColumn.setVisible(true);
                break;
            case "Proto":
                protoColumn.setVisible(true);
                serieColumn.setVisible(false);
                break;
            case "Série":
                protoColumn.setVisible(false);
                serieColumn.setVisible(true);
                break;
            default:
                protoColumn.setVisible(false);
                serieColumn.setVisible(false);
        }

        resultTable.setItems(FXCollections.observableArrayList(results));
        resultTable.setVisible(true); // Affiche le tableau après recherche

        ajusterLargeurColonnes();
    }

}
