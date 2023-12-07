package com.example.caringverseapp.AdminMenuActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.caringverseapp.AdminAjouterProduitsActivity.AdminAjouterProduits;
import com.example.caringverseapp.AdminProduitsActivity.AdminProduits;
import com.example.caringverseapp.AdminUtilisateursActivity.ViewAdminUtilisateurs;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.LogInActivity.LogIn;
import com.example.caringverseapp.R;

public class AdminMenu extends AppCompatActivity {

    CardView btnProduits;
    CardView btnUtilisateurs;
    CardView btnAjouterProduits;
    Button btnDeconnexion;

    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);

        btnProduits = findViewById(R.id.cardProduitsAdminMenu);
        btnUtilisateurs = findViewById(R.id.idUtilisateursAdminMenu);
        btnAjouterProduits = findViewById(R.id.cardAjouterProduitsMenu);

        btnDeconnexion = findViewById(R.id.btnDeconnexionMenu);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        btnProduits.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminProduits.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });


        btnUtilisateurs.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewAdminUtilisateurs.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });


        btnAjouterProduits.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminAjouterProduits.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });


        btnDeconnexion.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });


    }
}
