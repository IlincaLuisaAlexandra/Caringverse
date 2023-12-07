package com.example.caringverseapp.AdminProduitsActivity;

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
import com.example.caringverseapp.AdminUtilisateursActivity.ViewAdminUtilisateurs;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Produit;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.R;
import com.example.caringverseapp.Utils.Mapper.AnnounceMapper;
import com.example.caringverseapp.Utils.Mapper.IObjectMapper;
import com.example.caringverseapp.Utils.Mapper.ProduitMapper;
import com.example.caringverseapp.ViewAnnouncesActivity.AnnonceDecorator;
import com.example.caringverseapp.ViewAnnouncesActivity.Announce;
import com.example.caringverseapp.ViewAnnouncesActivity.AnnounceAdapter;
import com.example.caringverseapp.ViewAnnouncesActivity.ViewAnnounces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminProduits extends AppCompatActivity implements ProduitsAdapter.IAdminCommands {

    ArrayList<Produit> produits = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ProduitsAdapter adapter;
    RecyclerView recyclerView;
    private RequestQueue requestQueue;

    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_produits);

        Button btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AdminMenu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        requestQueue = Volley.newRequestQueue(AdminProduits.this);

        StringRequest request = new StringRequest(Request.Method.GET, PhpResources.GetAllAdminProduitsURL,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        IObjectMapper<Produit> mapper = new ProduitMapper();

                        for (int i = 0; i < jsonArray.length(); ++i) {
                            produits.add(mapper.map(jsonArray.getJSONObject(i)));
                        }

                        recyclerView = findViewById(R.id.recyclerViewRecompence);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(AdminProduits.this.getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ProduitsAdapter(produits, AdminProduits.this);
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
    public void deteleProduit(Produit produit) {
        StringRequest request = new StringRequest(Request.Method.POST, PhpResources.DeleteProduitByIdURL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.getBoolean("success")) {

                            Intent intent = new Intent(getApplicationContext(), AdminProduits.class);
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
                params.put("id", produit.getId());
                return params;
            }
        };

        requestQueue.add(request);
    }
}