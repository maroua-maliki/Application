package org.demo.demo.dao;

import org.demo.demo.config.DatabaseUtil;
import org.demo.demo.entities.Fichier;
import org.demo.demo.entities.ProduitExcel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitExcelDAO {

    public void save(ProduitExcel produitExcel) throws SQLException {
        String sql = "INSERT INTO produit_excel (nom, decoupage, prix_unitaire_proto, prix_unitaire_serie, id_fichier) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produitExcel.getNom());
            stmt.setString(2, produitExcel.getDecoupage());
            stmt.setDouble(3, produitExcel.getPrixUnitaireProto());
            stmt.setDouble(4, produitExcel.getPrixUnitaireSerie());
            stmt.setInt(5, produitExcel.getFichier().getId());

            stmt.executeUpdate();
        }
    }

    public List<ProduitExcel> rechercherParDecoupage(String description) {
        List<ProduitExcel> produitExcels = new ArrayList<>();
        String sql = """
        SELECT p.id, p.nom, p.decoupage, p.prix_unitaire_proto, p.prix_unitaire_serie,
               f.id AS f_id, f.nom_fichier, f.type_fichier, f.path
        FROM produit_excel p
        JOIN fichier_produit f ON p.id_fichier = f.id
        WHERE p.decoupage LIKE ?
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + description + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Fichier fichier = new Fichier(
                        rs.getInt("f_id"),
                        rs.getString("nom_fichier"),
                        rs.getString("type_fichier"),
                        rs.getString("path") // <-- nouveau champ
                );

                ProduitExcel produitExcel = new ProduitExcel(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("decoupage"),
                        rs.getDouble("prix_unitaire_proto"),
                        rs.getDouble("prix_unitaire_serie"),
                        fichier
                );

                produitExcels.add(produitExcel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produitExcels;
    }
}
