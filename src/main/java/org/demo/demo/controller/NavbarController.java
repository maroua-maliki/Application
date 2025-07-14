package org.demo.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class NavbarController {

    @FXML
    private Button menuButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button addFileButton;

    @FXML
    private Button searchButton;


    @FXML
    public void initialize() {
        // Créer une icône bootstrap "menu" (list)
        FontIcon menuIcon = new FontIcon("bi-list");
        menuIcon.setIconSize(24);
        menuIcon.setIconColor(Color.WHITE);
        menuButton.setGraphic(menuIcon);

        // Actions simples sur Menu et Accueil
        menuButton.setOnAction(e -> System.out.println("Menu clicked"));
        homeButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/home.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) homeButton.getScene().getWindow();
                Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Au clic sur Ajouter un fichier, charger addFile.fxml
        addFileButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/addFile.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) addFileButton.getScene().getWindow();
                Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        searchButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/Recherche.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) searchButton.getScene().getWindow();
                Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }


}
