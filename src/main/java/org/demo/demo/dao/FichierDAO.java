package org.demo.demo.dao;

import org.demo.demo.DatabaseUtil;
import org.demo.demo.entities.Fichier;

import java.sql.*;

public class FichierDAO {

    public int save(Fichier fichier) throws SQLException {
        String sql = "INSERT INTO fichier_produit (nom_fichier, type_fichier) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, fichier.getNom_fichier());
            stmt.setString(2, fichier.getType_fichier());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // retourne l'id généré
            } else {
                throw new SQLException("Aucun ID généré");
            }
        }
    }
}
