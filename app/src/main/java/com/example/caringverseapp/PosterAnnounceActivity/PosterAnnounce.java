package com.example.caringverseapp.PosterAnnounceActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.DetailsPersonnelles.DetailsPersonnelles;
import com.example.caringverseapp.DetailsPersonnelles.EditerDetailsPersonnelles;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PosterAnnounce extends AppCompatActivity {

    Button btnAbandoner;
    Button btnPoster;
    Spinner spinner;

    TextView errorMessage;
    Utilisateur utilisateur;

    EditText editTextVille;
    EditText editTextPays;
    EditText editTextDescription;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poster_announcee);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        btnAbandoner = findViewById(R.id.btnAbandonner);
        btnPoster = findViewById(R.id.btnPoster);

        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);

        editTextVille = findViewById(R.id.villePosterAnnonce);
        editTextPays = findViewById(R.id.paysPosterAnnonce);
        editTextDescription = findViewById(R.id.DescriptionAnnoncePoster);

        spinner = findViewById(R.id.categoriePosterAnnonce);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_arrays,
                android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(PosterAnnounce.this);

        btnAbandoner.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        btnPoster.setOnClickListener(v -> {
            if (validateAndGenerateErrorMessage()) {
                StringRequest request = new StringRequest(Request.Method.POST, PhpResources.AjouterAnnonceURL,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getBoolean("success")) {

                                    Intent intent = new Intent(getApplicationContext(), Menu.class);
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

                        params.put("description", String.valueOf(editTextDescription.getText()));
                        params.put("ville", String.valueOf(editTextVille.getText()));
                        params.put("pays", String.valueOf(editTextPays.getText()));
                        params.put("categorie", String.valueOf(spinner.getSelectedItem()));
                        params.put("proprietaire", utilisateur.getId());

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
        if (String.valueOf(spinner.getSelectedItem()).equals("Selecter Categorie")) {
            errorMessage.setText("Selecter une categorie");
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
        if (String.valueOf(editTextVille.getText()).equals("")) {
            errorMessage.setText("Ecrire une ville");
            return false;
        }
        if (String.valueOf(editTextPays.getText()).equals("")) {
            errorMessage.setText("Ecrire un pays");
            return false;
        }
        return true;
    }

}
