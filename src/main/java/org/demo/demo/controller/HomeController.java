package org.demo.demo.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HomeController {


    @FXML
    private Button importButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button analyzeButton;



    @FXML
    public void initialize() {

    }


    private void createFloatingAnimation(Circle circle, double duration, double distance) {
        if (circle != null) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), circle);
            translateTransition.setByY(distance);
            translateTransition.setAutoReverse(true);
            translateTransition.setCycleCount(Timeline.INDEFINITE);
            translateTransition.setInterpolator(Interpolator.EASE_BOTH);
            translateTransition.play();
        }
    }

    private void createRotationAnimation(javafx.scene.Node node, double duration) {
        if (node != null) {
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(duration), node);
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(Timeline.INDEFINITE);
            rotateTransition.setInterpolator(Interpolator.LINEAR);
            rotateTransition.play();
        }
    }

    @FXML
    private void onStartButtonClick() {
        navigateToAddFile();
    }

    @FXML
    private void onLearnMoreButtonClick() {
        // Could open a help dialog or documentation
        System.out.println("Learn more clicked - could open help documentation");
    }

    @FXML
    private void onImportButtonClick() {
        navigateToAddFile();
    }

    @FXML
    private void onSearchButtonClick() {
        navigateToSearch();
    }

    @FXML
    private void onAnalyzeButtonClick() {
        navigateToAddFileManuel();
    }

    private void navigateToAddFile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/addFile.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) importButton.getScene().getWindow();
            Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/Recherche.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) searchButton.getScene().getWindow();
            Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void navigateToAddFileManuel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/addFileManuel.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) analyzeButton.getScene().getWindow();
            Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}