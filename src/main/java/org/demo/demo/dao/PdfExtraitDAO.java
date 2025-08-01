package org.demo.demo.dao;

import org.demo.demo.config.DatabaseUtil;
import org.demo.demo.entities.Fichier;
import org.demo.demo.entities.PdfExtrait;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PdfExtraitDAO {

    public void save(PdfExtrait extrait) throws SQLException {
        String sql = "INSERT INTO pdf_extrait (contenu, id_fichier) VALUES (?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, extrait.getContenu());
            if (extrait.getFichier() != null) {
                stmt.setInt(2, extrait.getFichier().getId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();
        }
    }

    public List<PdfExtrait> rechercherParContenu(String motCle) throws SQLException {
        List<PdfExtrait> resultats = new ArrayList<>();
        String sql = """
        SELECT e.id AS extrait_id, e.contenu,
               f.id AS fichier_id, f.nom_fichier, f.type_fichier, f.path
        FROM pdf_extrait e
        JOIN fichier_produit f ON e.id_fichier = f.id
        WHERE e.contenu LIKE ?
    """;

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + motCle + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PdfExtrait extrait = new PdfExtrait();
                    extrait.setId(rs.getInt("extrait_id"));
                    extrait.setContenu(rs.getString("contenu"));

                    Fichier fichier = new Fichier(
                            rs.getInt("fichier_id"),
                            rs.getString("nom_fichier"),
                            rs.getString("type_fichier"),
                            rs.getString("path") // <-- Ajout du path
                    );

                    extrait.setFichier(fichier);
                    resultats.add(extrait);
                }
            }
        }

        return resultats;
    }

}
