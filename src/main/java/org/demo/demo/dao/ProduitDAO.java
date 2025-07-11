package org.demo.demo.dao;

import org.demo.demo.DatabaseUtil;
import org.demo.demo.entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProduitDAO {

    public void save(Produit produit) throws SQLException {
        String sql = "INSERT INTO produit (nom, des, prix_unitaire_proto, prix_unitaire_serie, id_fichier) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produit.getNom());
            stmt.setString(2, produit.getDescription());
            stmt.setDouble(3, produit.getPrixUnitaireProto());
            stmt.setDouble(4, produit.getPrixUnitaireSerie());
            stmt.setInt(5, produit.getIdFichier());

            stmt.executeUpdate();
        }
    }
}
