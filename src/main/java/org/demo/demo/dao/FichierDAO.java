package org.demo.demo.dao;

import org.demo.demo.config.DatabaseUtil;
import org.demo.demo.entities.Fichier;

import java.sql.*;

public class FichierDAO {

    public int save(Fichier fichier) throws SQLException {
        String sql = "INSERT INTO fichier_produit (nom_fichier, type_fichier, path) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fichier.getNom_fichier());
            stmt.setString(2, fichier.getType_fichier());
            stmt.setString(3, fichier.getPath()); // nouveau paramètre
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // retourne l'id généré
            } else {
                throw new SQLException("Aucun ID généré");
            }
        }
    }

    public boolean existsByFilename(String nomFichier) {
        String sql = "SELECT COUNT(*) FROM fichier_produit WHERE nom_fichier = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomFichier);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Fichier findByNom(String nomFichier) {
        String sql = "SELECT * FROM fichier_produit WHERE nom_fichier = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomFichier);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Fichier(
                        rs.getInt("id"),
                        rs.getString("nom_fichier"),
                        rs.getString("type_fichier"),
                        rs.getString("path")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
