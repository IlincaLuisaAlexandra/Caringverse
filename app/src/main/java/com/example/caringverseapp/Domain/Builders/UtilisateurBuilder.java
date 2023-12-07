package com.example.caringverseapp.Domain.Builders;

import com.example.caringverseapp.Domain.Utilisateur;

public class UtilisateurBuilder implements IUtilisateurBuilder {

   private Utilisateur utilisateur;

    public UtilisateurBuilder() {
        utilisateur = new Utilisateur();
    }

    public UtilisateurBuilder setNom(String nom) {
        utilisateur.setNom(nom);
        return this;
    }

    public UtilisateurBuilder setEmail(String email) {
        utilisateur.setEmail(email);
        return this;
    }

    public UtilisateurBuilder setTelephone(String telephone) {
        utilisateur.setTelephone(telephone);
        return this;
    }

    public UtilisateurBuilder setDateNaissance(String dateNaissance) {
        utilisateur.setDateNaissance(dateNaissance);
        return this;
    }

    public UtilisateurBuilder setPoints(String points) {
        utilisateur.setPoints(points);
        return this;
    }

    public UtilisateurBuilder setId(String id){
        utilisateur.setId(id);
        return this;
    }

    public UtilisateurBuilder setIsAdmin(String isAdmin){
        utilisateur.setIsAdmin(isAdmin);
        return this;
    }

    @Override
    public Utilisateur build() {

        //Adaugat validari

        return utilisateur;
    }
}
