package com.example.caringverseapp.DetailsPersonnelles;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.R;

public class DetailsPersonnelles extends AppCompatActivity {

    Button btnMenu;

    Button btnEditesLesDetailsPersonnelle;

    EditText nom;
    EditText email;
    EditText telephone;
    EditText datanaissance;
    TextView points;

    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_personelless);

        btnMenu = findViewById(R.id.btnMenu);

        nom = findViewById(R.id.nomDetailsPersonnele);
        email = findViewById(R.id.emailDetailsPersonnele);
        telephone = findViewById(R.id.telephoneDetailsPersonnele);
        datanaissance = findViewById(R.id.dateDetailsPersonnele);
        points = findViewById(R.id.pointsDetailsPersonnele);
        btnEditesLesDetailsPersonnelle = findViewById(R.id.btnEditterLesDetails);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        nom.setText(utilisateur.getNom());
        email.setText(utilisateur.getEmail());
        telephone.setText(utilisateur.getTelephone());
        datanaissance.setText( utilisateur.getDateNaissance());
        points.setText(utilisateur.getPoints());

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(),utilisateur);
            startActivity(intent);
            finish();
        });

        btnEditesLesDetailsPersonnelle.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditerDetailsPersonnelles.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(),utilisateur);
            startActivity(intent);
            finish();
        });
    }
}