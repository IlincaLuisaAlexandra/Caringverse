package com.example.caringverseapp.AdminUtilisateursActivity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.DetailsPersonnelles.EditerDetailsPersonnelles;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.R;

import java.util.ArrayList;


public class AdmUtilAdapter extends RecyclerView.Adapter<AdmUtilAdapter.AdmUtilViewHolder> {

    ArrayList<Utilisateur> utilisateurs;
    IAdminUtilisateurFunctionalities mListener;

    public AdmUtilAdapter(ArrayList<Utilisateur> data, IAdminUtilisateurFunctionalities mListener) {
        this.utilisateurs = data;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public AdmUtilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_adm_utilisateur, parent, false);

        return new AdmUtilViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmUtilViewHolder holder, int position) {
        Utilisateur utilisateur = utilisateurs.get(position);
        holder.textViewNom.setText(utilisateur.getNom());
        holder.textViewEmail.setText(utilisateur.getEmail());
        holder.textViewTelephone.setText(utilisateur.getTelephone());
        holder.textViewdateNaiss.setText(utilisateur.getDateNaissance());
        holder.textViewPoints.setText("Points:" + utilisateur.getPoints());
        holder.position = position;
        holder.utilisateur = utilisateur;
    }

    @Override
    public int getItemCount() {
        return this.utilisateurs.size();
    }

    public static class AdmUtilViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNom;
        TextView textViewEmail;
        TextView textViewTelephone;
        TextView textViewdateNaiss;
        TextView textViewPoints;
        TextView textViewValeur;

        Button creationAdmin;
        Button diminuePoints;


        IAdminUtilisateurFunctionalities mListener;

        int position;
        Utilisateur utilisateur;


        public AdmUtilViewHolder(@NonNull View itemView, IAdminUtilisateurFunctionalities mListener) {
            super(itemView);
            this.mListener = mListener;

            textViewNom = itemView.findViewById(R.id.nomUtl);
            textViewEmail = itemView.findViewById(R.id.emailUtl);
            textViewTelephone = itemView.findViewById(R.id.telephoneUtl);
            textViewdateNaiss = itemView.findViewById(R.id.dateNaissUtl);
            textViewPoints = itemView.findViewById(R.id.pointsUtl);

            creationAdmin = itemView.findViewById(R.id.faireAdmin);
            diminuePoints = itemView.findViewById(R.id.diminuer);


            creationAdmin.setOnClickListener(v->{
                mListener.makeAdmin(utilisateur);
            });

            diminuePoints.setOnClickListener(v-> {
                mListener.diminuePoints(utilisateur);
            });
        }
    }

    interface IAdminUtilisateurFunctionalities {
        void makeAdmin(Utilisateur util);
        void diminuePoints(Utilisateur util);
    }
}
