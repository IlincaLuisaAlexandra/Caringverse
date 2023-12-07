package com.example.caringverseapp.ViewAnnouncesActivity;

import java.io.Serializable;

public class Announce implements Serializable {

    private Integer announceId;
    private String categorie;
    private String description;
    private String proprietaire;
    private String statusAnnounce;
    private String idVolontaire;
    private String ville;
    private String pays;

    public Announce(Integer announceId, String categorie, String description,
                    String proprietaire, String statusAnnounce, String idVolontaire, String ville, String pays) {
        this.announceId = announceId;
        this.categorie = categorie;
        this.description = description;
        this.proprietaire = proprietaire;
        this.statusAnnounce = statusAnnounce;
        this.idVolontaire = idVolontaire;
        this.ville = ville;
        this.pays = pays;
    }

    public Announce() {}

    public Integer getAnnounceId() {return announceId;}
    public String getCategorie() {return categorie;}
    public String getDescription() {return description;}
    public String getProprietaire() {return proprietaire;}
    public String getStatusAnnounce() {return statusAnnounce;}
    public String getIdVolontaire() {return idVolontaire;}
    public String getVille() {return ville;}
    public String getPays() {return pays;}

    public void setAnnounceId(Integer announceId) {this.announceId = announceId;}
    public void setCategorie(String categorie) {this.categorie = categorie;}
    public void setDescription(String description) {this.description = description;}
    public void setProprietaire(String proprietaire) {this.proprietaire = proprietaire;}
    public void setStatusAnnounce(String statusAnnounce) {this.statusAnnounce = statusAnnounce;}
    public void setIdVolontaire(String idVolontaire) {this.idVolontaire = idVolontaire;}
    public void setVille(String ville) {this.ville = ville;}
    public void setPays(String pays) {this.pays = pays;}
}
