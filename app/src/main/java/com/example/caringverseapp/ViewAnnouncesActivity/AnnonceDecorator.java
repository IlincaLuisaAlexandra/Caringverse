package com.example.caringverseapp.ViewAnnouncesActivity;

import java.io.Serializable;

public class AnnonceDecorator implements Serializable {
    Announce announce;
    String nom;

    String telephone;

    public AnnonceDecorator(Announce announce, String nom, String telephone) {
        this.announce = announce;
        this.nom = nom;
        this.telephone = telephone;
    }



    public AnnonceDecorator(Announce announce, String nom) {
        this.announce = announce;
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Announce getAnnounce() {
        return announce;
    }

    public void setAnnounce(Announce announce) {
        this.announce = announce;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
