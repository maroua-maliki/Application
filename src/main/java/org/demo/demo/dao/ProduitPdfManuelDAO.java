package org.demo.demo.dao;

import org.demo.demo.config.DatabaseUtil;
import org.demo.demo.entities.Fichier;
import org.demo.demo.entities.ProduitPdfManuel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitPdfManuelDAO {

    private static final String INSERT_SQL = "INSERT INTO produit_pdf (ref, prix, designation, fichier_id) VALUES (?, ?, ?, ?)";

    public int save(ProduitPdfManuel produit) throws SQLException {
        int generatedId = -1;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produit.getRef());
            stmt.setDouble(2, produit.getPrix());
            stmt.setString(3, produit.getDesignation());

            // ⚠ Assure-toi que le fichier n'est pas null et qu'il a un ID
            if (produit.getFichier() == null || produit.getFichier().getId() <= 0) {
                throw new SQLException("Le fichier associé au produit est invalide.");
            }

            stmt.setInt(4, produit.getFichier().getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Échec de l'insertion, aucune ligne affectée.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                    produit.setId(generatedId);  // Met à jour l'objet avec l'ID généré
                } else {
                    throw new SQLException("Échec de l'insertion, aucun ID retourné.");
                }
            }
        }

        return generatedId;
    }

    public List<ProduitPdfManuel> rechercheManuel(String keyword) {
        List<ProduitPdfManuel> result = new ArrayList<>();

        String sql = """
        SELECT p.id, p.ref, p.prix, p.designation,
               f.id AS fichier_id, f.nom_fichier, f.type_fichier, f.path
        FROM produit_pdf p
        JOIN fichier_produit f ON p.fichier_id = f.id
        WHERE LOWER(p.ref) LIKE ? OR LOWER(p.designation) LIKE ?
        """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword.toLowerCase() + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Fichier fichier = new Fichier(
                        rs.getInt("fichier_id"),
                        rs.getString("nom_fichier"),
                        rs.getString("type_fichier"),
                        rs.getString("path")
                );

                ProduitPdfManuel produit = new ProduitPdfManuel(
                        rs.getInt("id"),
                        rs.getString("ref"),
                        rs.getDouble("prix"),
                        rs.getString("designation"),
                        fichier
                );

                result.add(produit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
