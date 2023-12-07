package com.example.caringverseapp.MenuActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.caringverseapp.DetailsPersonnelles.DetailsPersonnelles;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.LogInActivity.LogIn;
import com.example.caringverseapp.PosterAnnounceActivity.PosterAnnounce;
import com.example.caringverseapp.ProduitsUserAcivity.ProduitsUserActivity;
import com.example.caringverseapp.R;
import com.example.caringverseapp.ViewAnnouncesActivity.ViewAnnounces;

public class Menu extends AppCompatActivity {

    CardView btnVisualiserAnnonces;
    CardView btnPosterUnAnnonce;
    Button btnDeconnexion;
    CardView btnDetailsPersonalles;

    CardView btnProduits;

    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuu);

        btnVisualiserAnnonces = findViewById(R.id.cardVisualiserAnnonces);
        btnPosterUnAnnonce = findViewById(R.id.cardPosterAnnonces);
        btnDeconnexion = findViewById(R.id.btnDeconnexionMenu);
        btnDetailsPersonalles = findViewById(R.id.cardDetailsPersonnelles);
        btnProduits = findViewById(R.id.cardRecompenses);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        btnVisualiserAnnonces.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ViewAnnounces.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });

        btnDetailsPersonalles.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DetailsPersonnelles.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });

        btnPosterUnAnnonce.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PosterAnnounce.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });

        btnProduits.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProduitsUserActivity.class);
            if (utilisateur != null) {
                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                startActivity(intent);
                finish();
            }
        });

        btnDeconnexion.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
            finish();

        });
    }
}
