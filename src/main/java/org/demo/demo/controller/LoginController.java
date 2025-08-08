package org.demo.demo.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.demo.demo.config.DatabaseUtil;
import org.demo.demo.dao.UtilisateurDAO;
import org.demo.demo.entities.Utilisateur;
import org.demo.demo.services.AuthService;


import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private AuthService authService;

    public void initialize() {
        try {
            Connection conn = DatabaseUtil.getConnection();
            UtilisateurDAO userDAO = new UtilisateurDAO(conn);
            authService = new AuthService(userDAO);
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de se connecter à la base de données.");
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (!username.matches("^[A-Za-z0-9._%+-]+@capgemini\\.com$")) {
            showAlert("Erreur", "L'adresse e-mail doit se terminer par @cepgemini.com.");
            return;
        }

        Optional<Utilisateur> userOpt = authService.login(username, password);

        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();

            // Charger la page d'accueil après connexion réussie
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/demo/demo/home.fxml"));
                Parent homeView = loader.load();

                // Si tu veux transmettre des infos à HomeController :
                // HomeController controller = loader.getController();
                // controller.setUtilisateur(user); // exemple

                Stage stage = (Stage) usernameField.getScene().getWindow();
                Scene scene = new Scene(homeView);
                stage.setScene(scene);
                stage.setTitle("Page d'accueil");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Impossible de charger la page d'accueil.");
            }

        } else {
            showAlert("Erreur", "Nom d'utilisateur ou mot de passe incorrect.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

