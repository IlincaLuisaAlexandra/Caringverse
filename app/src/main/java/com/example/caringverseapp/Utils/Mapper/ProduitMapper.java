package com.example.caringverseapp.Utils.Mapper;

import com.example.caringverseapp.Domain.Produit;
import com.example.caringverseapp.ViewAnnouncesActivity.Announce;

import org.json.JSONException;
import org.json.JSONObject;

public class ProduitMapper implements IObjectMapper<Produit>{
    @Override
    public Produit map(JSONObject obj) {
        Produit produit = null;
        try {
            produit = new Produit();

            produit.setId(obj.getString("id_produits"));
            produit.setNom(obj.getString("nom_produit"));
            produit.setDescription(obj.getString("description_produit"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return produit;
    }
}
