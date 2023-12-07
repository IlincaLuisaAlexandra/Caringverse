package com.example.caringverseapp.Domain;

import java.io.Serializable;

public class Produit implements Serializable {
    private String id;
    private String nom;
    private String description;

    public Produit(String id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Produit() {}

    public String getId() {return id;}
    public String getNom() {return nom;}
    public String getDescription() {return description;}

    public void setId(String id) {this.id = id;}
    public void setNom(String nom) {this.nom = nom;}
    public void setDescription(String description) {this.description = description;}
}
