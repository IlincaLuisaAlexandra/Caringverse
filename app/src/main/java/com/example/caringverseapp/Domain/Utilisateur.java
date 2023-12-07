package com.example.caringverseapp.Domain;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    private String id;
    private String nom;
    private String email;
    private String telephone;
    private String dateNaissance;
    private String points;
    private String isAdmin;

    public Utilisateur(String id, String nom, String email, String telephone, String dateNaissance, String points, String isAdmin) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.points = points;
        this.isAdmin = isAdmin;
    }

    public Utilisateur() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }



}
