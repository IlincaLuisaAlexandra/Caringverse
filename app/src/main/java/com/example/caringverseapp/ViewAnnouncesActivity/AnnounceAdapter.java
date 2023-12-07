package com.example.caringverseapp.ViewAnnouncesActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caringverseapp.R;

import java.util.ArrayList;

public class AnnounceAdapter extends RecyclerView.Adapter<AnnounceAdapter.AnnounceViewHolder> {
    ArrayList<AnnonceDecorator> announces;
    IAnnounce mListener;
    public AnnounceAdapter (ArrayList<AnnonceDecorator> data, IAnnounce mListener){
        this.announces = data;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public AnnounceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_announce, parent, false);
        AnnounceViewHolder announceViewHolder = new AnnounceViewHolder(view, mListener);
        return announceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnnounceViewHolder holder, int position) {
        AnnonceDecorator announce = announces.get(position);
        holder.textViewDescription.setText(announce.getAnnounce().getDescription());
        holder.textViewCategorie.setText(announce.getAnnounce().getCategorie());
        holder.textViewProprietaire.setText(announce.nom);
        holder.textViewVillePays.setText(announce.getAnnounce().getVille() + ", " + announce.getAnnounce().getPays());
        holder.position = position;
        holder.announce = announce;
    }

    @Override
    public int getItemCount() {
        return this.announces.size();
    }

    public static class AnnounceViewHolder extends RecyclerView.ViewHolder{

        TextView textViewProprietaire;
        TextView textViewDescription;
        TextView textViewCategorie;
        TextView textViewVillePays;
        IAnnounce mListener;

        int position;
        AnnonceDecorator announce;

        public AnnounceViewHolder(@NonNull View itemView, IAnnounce mListener) {
            super(itemView);
            this.mListener = mListener;
            textViewProprietaire = itemView.findViewById(R.id.proprietaireAnn);
            textViewDescription = itemView.findViewById(R.id.descriptionAnn);
            textViewCategorie = itemView.findViewById(R.id.categorieAnn);
            textViewVillePays = itemView.findViewById(R.id.ville_pays_Ann);


            itemView.findViewById(R.id.accepterAnn).setOnClickListener(v -> mListener.gotoAnnounceDetails(announce));

        }
    }

    interface IAnnounce{
        void gotoAnnounceDetails(AnnonceDecorator announce);
    }


}
