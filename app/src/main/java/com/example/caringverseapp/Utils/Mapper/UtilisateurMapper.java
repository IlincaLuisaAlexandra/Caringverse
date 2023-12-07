package com.example.caringverseapp.Utils.Mapper;

import com.example.caringverseapp.Domain.Builders.IUtilisateurBuilder;
import com.example.caringverseapp.Domain.Builders.UtilisateurBuilder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.ViewAnnouncesActivity.Announce;

import org.json.JSONException;
import org.json.JSONObject;

public class UtilisateurMapper implements IObjectMapper<Utilisateur>{

    @Override
    public Utilisateur map(JSONObject obj) {
        Utilisateur utilisateur = null;
        try {
            utilisateur = new UtilisateurBuilder()
                    .setId(obj.getString("id"))
                    .setNom(obj.getString("nom"))
                    .setEmail(obj.getString("email"))
                    .setTelephone(obj.getString("telephone"))
                    .setDateNaissance(obj.getString("datenaissance"))
                    .setIsAdmin(obj.getString("admin"))
                    .setPoints(obj.getString("points"))
                    .build();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }
}
