package org.demo.demo.services;

import org.demo.demo.dao.ProduitDAO;
import org.demo.demo.entities.Produit;

import java.util.List;

public class RechercheService {

    private ProduitDAO produitDAO = new ProduitDAO();

    public List<Produit> rechercherProduitsParDescription(String description) {
        return produitDAO.rechercherParDescription(description);
    }
}
