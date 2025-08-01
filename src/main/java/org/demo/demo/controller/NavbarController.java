package org.demo.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class NavbarController {


    @FXML
    private Button homeButton;

    @FXML
    private Button addFileButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addFileManuelButton;

    @FXML
    public void initialize() {
        FontIcon menuIcon = new FontIcon(BootstrapIcons.LIST);
        menuIcon.setIconSize(24);
        menuIcon.setIconColor(Color.WHITE);
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

        addFileManuelButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/addFileManuel.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) addFileManuelButton.getScene().getWindow(); // corrigé : c'était searchButton avant
                Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
                stage.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

}
