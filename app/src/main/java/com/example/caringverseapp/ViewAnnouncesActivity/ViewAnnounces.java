package com.example.caringverseapp.ViewAnnouncesActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.MissionActivity.Mission;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.R;
import com.example.caringverseapp.Utils.Mapper.IObjectMapper;
import com.example.caringverseapp.Utils.Mapper.AnnounceMapper;
import com.example.caringverseapp.MissionActivity.Mission;
import com.example.caringverseapp.Utils.Mapper.UtilisateurMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ViewAnnounces extends AppCompatActivity implements AnnounceAdapter.IAnnounce {

    ArrayList<AnnonceDecorator> announces = new ArrayList<>();
    LinearLayoutManager layoutManager;
    AnnounceAdapter adapter;
    RecyclerView recyclerView;
    private RequestQueue requestQueue;

    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.announces);

        Button btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        requestQueue = Volley.newRequestQueue(ViewAnnounces.this);

        StringRequest request = new StringRequest(Request.Method.GET, PhpResources.AnnouncesURL,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        IObjectMapper<Announce> mapper = new AnnounceMapper();

                        for (int i = 0; i < jsonArray.length(); ++i) {
                            Announce tempAnnonce = mapper.map(jsonArray.getJSONObject(i));
                            AnnonceDecorator annonceDecorator = new AnnonceDecorator(tempAnnonce,
                                    jsonArray.getJSONObject(i).getString("nom"),
                                    jsonArray.getJSONObject(i).getString("telephone"));
                            announces.add(annonceDecorator);
                        }

                        recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(ViewAnnounces.this.getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new AnnounceAdapter(announces, ViewAnnounces.this);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            //De adaugat Error Handler
        });

        requestQueue.add(request);
    }

    @Override
    public void gotoAnnounceDetails(AnnonceDecorator announce) {

        Intent intent = new Intent(this, Mission.class);

        intent.putExtra(KeyObjectFinder.utilisateurAnnouncerDecorator.toString(), announce);
        intent.putExtra(KeyObjectFinder.Annonce.toString(), announce.getAnnounce());
        intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
        startActivity(intent);
    }

}