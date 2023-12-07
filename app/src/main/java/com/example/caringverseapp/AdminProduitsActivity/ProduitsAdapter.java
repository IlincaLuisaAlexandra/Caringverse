package com.example.caringverseapp.AdminProduitsActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caringverseapp.Domain.Produit;
import com.example.caringverseapp.R;
import com.example.caringverseapp.ViewAnnouncesActivity.AnnonceDecorator;

import java.util.ArrayList;

public class ProduitsAdapter extends RecyclerView.Adapter<ProduitsAdapter.ProduitViewHolder> {
    ArrayList<Produit> produits;
    IAdminCommands mListener;
    public ProduitsAdapter(ArrayList<Produit> data, IAdminCommands mListener){
        this.produits = data;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_adm_produit, parent, false);
        ProduitViewHolder produitViewHolder = new ProduitViewHolder(view, mListener);
        return produitViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
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

    public static class ProduitViewHolder extends RecyclerView.ViewHolder{

        TextView editTextNom;
        TextView editTextDescription;

        TextView btnDelete;
        IAdminCommands mListener;

        int position;

        Produit produit;

        public ProduitViewHolder(@NonNull View itemView, IAdminCommands mListener) {
            super(itemView);
            this.mListener = mListener;
            editTextNom = itemView.findViewById(R.id.nomRecompense);
            editTextDescription = itemView.findViewById(R.id.descriptionRecompense);
            btnDelete = itemView.findViewById(R.id.btnDeleteRecompense);

            btnDelete.setOnClickListener(v -> mListener.deteleProduit(produit));

        }
    }

    interface IAdminCommands{
        void deteleProduit(Produit produit);
    }


}
