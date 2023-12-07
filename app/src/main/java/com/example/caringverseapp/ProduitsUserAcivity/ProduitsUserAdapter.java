package com.example.caringverseapp.ProduitsUserAcivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caringverseapp.Domain.Produit;
import com.example.caringverseapp.R;

import java.util.ArrayList;

public class ProduitsUserAdapter extends RecyclerView.Adapter<ProduitsUserAdapter.ProduitUserViewHolder> {
    ArrayList<Produit> produits;
    public ProduitsUserAdapter(ArrayList<Produit> data){
        this.produits = data;
    }


    @NonNull
    @Override
    public ProduitUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recompense, parent, false);
        ProduitUserViewHolder produitViewHolder = new ProduitUserViewHolder(view);
        return produitViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitUserViewHolder holder, int position) {
        Produit produit = produits.get(position);
        holder.editTextDescription.setText(produit.getDescription());
        holder.editTextNom.setText(produit.getNom());
        holder.position = position;
        holder.produit = produit;
    }

    @Override
    public int getItemCount() {
        return this.produits.size();
    }

    public static class ProduitUserViewHolder extends RecyclerView.ViewHolder{

        TextView editTextNom;
        TextView editTextDescription;
        int position;
        Produit produit;

        public ProduitUserViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextNom = itemView.findViewById(R.id.nomRecompense);
            editTextDescription = itemView.findViewById(R.id.descriptionRecompense);
        }
    }
}
