package com.example.caringverseapp.Utils.Mapper;

import com.example.caringverseapp.ViewAnnouncesActivity.Announce;

import org.json.JSONException;
import org.json.JSONObject;

public class AnnounceMapper implements IObjectMapper<Announce> {

    @Override
    public Announce map(JSONObject obj) {
        Announce announce = null;
        try {
            announce = new Announce();

            announce.setAnnounceId(obj.getInt("id_annonce"));
            announce.setCategorie(obj.getString("categorie"));
            announce.setDescription(obj.getString("description"));
            announce.setProprietaire(obj.getString("proprietaire"));
            announce.setStatusAnnounce(obj.getString("status"));
            announce.setIdVolontaire(obj.getString("volontaire"));
            announce.setVille(obj.getString("ville"));
            announce.setPays(obj.getString("pays"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return announce;
    }
}
