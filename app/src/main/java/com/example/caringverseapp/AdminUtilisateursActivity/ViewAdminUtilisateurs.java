package com.example.caringverseapp.AdminUtilisateursActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.AdminMenuActivity.AdminMenu;
import com.example.caringverseapp.DetailsPersonnelles.DetailsPersonnelles;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.R;
import com.example.caringverseapp.Utils.Mapper.AnnounceMapper;
import com.example.caringverseapp.Utils.Mapper.IObjectMapper;
import com.example.caringverseapp.Utils.Mapper.UtilisateurMapper;
import com.example.caringverseapp.ViewAnnouncesActivity.Announce;
import com.example.caringverseapp.ViewAnnouncesActivity.AnnounceAdapter;
import com.example.caringverseapp.ViewAnnouncesActivity.ViewAnnounces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewAdminUtilisateurs extends AppCompatActivity implements AdmUtilAdapter.IAdminUtilisateurFunctionalities {

    ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
    LinearLayoutManager layoutManager;
    AdmUtilAdapter adapter;
    RecyclerView recyclerView;

    Utilisateur utilisateur;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_utilisateurs);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminMenu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        requestQueue = Volley.newRequestQueue(ViewAdminUtilisateurs.this);

        StringRequest request = new StringRequest(Request.Method.GET, PhpResources.VisualiserUtilisateursURL,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        IObjectMapper<Utilisateur> mapper = new UtilisateurMapper();

                        for (int i = 0; i < jsonArray.length(); ++i) {
                            utilisateurs.add(mapper.map(jsonArray.getJSONObject(i)));
                        }

                        utilisateurs = utilisateurs.stream()
                                .filter(i -> i.getIsAdmin().equals("0"))
                                .collect(Collectors.toCollection(ArrayList::new));

                        recyclerView = findViewById(R.id.recyclerView);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(this);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new AdmUtilAdapter(utilisateurs, this);
                        recyclerView.setAdapter(adapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });

        requestQueue.add(request);


    }


    @Override
    public void makeAdmin(Utilisateur util) {
        StringRequest request = new StringRequest(Request.Method.POST, PhpResources.UpdateUtilisateurToAdminURL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("success")) {

                            Intent intent = new Intent(getApplicationContext(), ViewAdminUtilisateurs.class);
                            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", util.getId());
                return params;
            }
        };

        requestQueue.add(request);
    }

    @Override
    public void diminuePoints(Utilisateur util) {

        if(Integer.parseInt(util.getPoints()) < 50){
            return;
        }
        StringRequest request = new StringRequest(Request.Method.POST, PhpResources.UpdateUtilisateurDiminuePointsURL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("success")) {

                            Intent intent = new Intent(getApplicationContext(), ViewAdminUtilisateurs.class);
                            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", util.getId());
                return params;
            }
        };

        requestQueue.add(request);

    }
}