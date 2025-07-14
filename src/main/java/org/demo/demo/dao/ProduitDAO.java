package org.demo.demo.dao;

import org.demo.demo.DatabaseUtil;
import org.demo.demo.entities.Produit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Produit> rechercherParDescription(String description) {
        List<Produit> produits = new ArrayList<>();
        String sql = """
        SELECT p.id, p.nom, p.des, p.prix_unitaire_proto, p.prix_unitaire_serie,
               p.id_fichier, f.nom_fichier
        FROM produit p
        JOIN fichier_produit f ON p.id_fichier = f.id
        WHERE p.des LIKE ?
    """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + description + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produit produit = new Produit(
                        rs.getInt("id"),
                        rs.getInt("id_fichier"),
                        rs.getDouble("prix_unitaire_serie"),
                        rs.getDouble("prix_unitaire_proto"),
                        rs.getString("des"),
                        rs.getString("nom")
                );
                produit.setNomFichier(rs.getString("nom_fichier")); // récupère le nom du fichier
                produits.add(produit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produits;
    }

}
