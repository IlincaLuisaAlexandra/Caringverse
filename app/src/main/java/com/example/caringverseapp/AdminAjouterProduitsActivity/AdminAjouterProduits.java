package com.example.caringverseapp.AdminAjouterProduitsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.AdminMenuActivity.AdminMenu;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.PosterAnnounceActivity.PosterAnnounce;
import com.example.caringverseapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminAjouterProduits extends AppCompatActivity {

    TextView errorMessage;
    Utilisateur utilisateur;

    EditText editTextNom;
    EditText editTextDescription;

    RequestQueue requestQueue;

    Button btnAbandoner;
    Button btnPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ajouter_produits);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        btnAbandoner = findViewById(R.id.idMenu);
        btnPoster = findViewById(R.id.idAjouterProduitPoster);

        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);

        editTextNom = findViewById(R.id.idNomAdminPosterProduit);
        editTextDescription = findViewById(R.id.idAdminDescriptionProduit);

        requestQueue = Volley.newRequestQueue(AdminAjouterProduits.this);

        btnAbandoner.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminMenu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        btnPoster.setOnClickListener(v -> {
            if (validateAndGenerateErrorMessage()) {
                StringRequest request = new StringRequest(Request.Method.POST, PhpResources.PosterAnnonceAdminProduitURL,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getBoolean("success")) {

                                    Intent intent = new Intent(getApplicationContext(), AdminMenu.class);
                                    intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    errorMessage.setText("Error");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                    errorMessage.setText("Server error");
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        params.put("nomAnnonce", String.valueOf(editTextNom.getText()));
                        params.put("description", String.valueOf(editTextDescription.getText()));

                        return params;
                    }
                };

                requestQueue.add(request);

            } else {
                errorMessage.setVisibility(View.VISIBLE);
            }

        });

    }


    private boolean validateAndGenerateErrorMessage() {
        if (String.valueOf(editTextNom.getText()).equals("")) {
            errorMessage.setText("Ecrire un nom");
            return false;
        }
        if (String.valueOf(editTextDescription.getText()).equals("")) {
            errorMessage.setText("Ecrire une description");
            return false;
        }
        if (String.valueOf(editTextDescription.getText()).length() > 300) {
            errorMessage.setText("Description doit avoir moins que 300 characters");
            return false;
        }

        return true;
    }

}