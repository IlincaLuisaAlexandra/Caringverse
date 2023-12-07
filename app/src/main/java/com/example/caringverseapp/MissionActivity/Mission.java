package com.example.caringverseapp.MissionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.example.caringverseapp.ViewAnnouncesActivity.AnnonceDecorator;
import com.example.caringverseapp.ViewAnnouncesActivity.Announce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Mission extends AppCompatActivity {
    Utilisateur utilisateur;
    Button finiMission;
    EditText nomProprietarie;
    EditText telephoneProrietarie;
    EditText categorieAnnonce;
    EditText paysAnnonce;
    EditText villeAnnonce;
    EditText descriptionAnnonce;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission);

         nomProprietarie = findViewById(R.id.proprietaireAnn);
         telephoneProrietarie = findViewById(R.id.numeroAnn);
         categorieAnnonce = findViewById(R.id.categorieAnn);
         paysAnnonce = findViewById(R.id.paysAnn);
         villeAnnonce = findViewById(R.id.villeAnn);
         descriptionAnnonce = findViewById(R.id.descriptionAnn);
         finiMission = findViewById(R.id.finMission);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);
        AnnonceDecorator utilisateurAnnouncer = getIntent().getSerializableExtra(KeyObjectFinder.utilisateurAnnouncerDecorator.toString(),AnnonceDecorator.class);

        nomProprietarie.setText(utilisateurAnnouncer.getNom());
        telephoneProrietarie.setText(utilisateurAnnouncer.getTelephone());
        categorieAnnonce.setText(utilisateurAnnouncer.getAnnounce().getCategorie());
        paysAnnonce.setText(utilisateurAnnouncer.getAnnounce().getPays());
        villeAnnonce.setText(utilisateurAnnouncer.getAnnounce().getVille());
        descriptionAnnonce.setText(utilisateurAnnouncer.getAnnounce().getDescription());

        requestQueue = Volley.newRequestQueue(Mission.this);

        finiMission.setOnClickListener(v -> {
            StringRequest request = new StringRequest(Request.Method.POST, PhpResources.finiAnnonceURL,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("success_delete") &&jsonObject.getBoolean("success_update") ) {

                                Intent intent = new Intent(getApplicationContext(), Menu.class);
                                utilisateur.setPoints(String.valueOf(Integer.parseInt(utilisateur.getPoints()) + 1));
                                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idAnnonce",utilisateurAnnouncer.getAnnounce().getAnnounceId().toString());
                    params.put("idUser", utilisateur.getId());
                    params.put("points",utilisateur.getPoints());
                    return params;
                }
            };

            requestQueue.add(request);



        });
    }
}