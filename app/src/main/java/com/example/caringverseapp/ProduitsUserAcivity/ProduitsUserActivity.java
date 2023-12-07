package com.example.caringverseapp.ProduitsUserAcivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.AdminMenuActivity.AdminMenu;
import com.example.caringverseapp.AdminProduitsActivity.AdminProduits;
import com.example.caringverseapp.AdminProduitsActivity.ProduitsAdapter;
import com.example.caringverseapp.DetailsPersonnelles.DetailsPersonnelles;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Produit;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.LogInActivity.LogIn;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.PosterAnnounceActivity.PosterAnnounce;
import com.example.caringverseapp.R;
import com.example.caringverseapp.Utils.Mapper.IObjectMapper;
import com.example.caringverseapp.Utils.Mapper.ProduitMapper;
import com.example.caringverseapp.ViewAnnouncesActivity.ViewAnnounces;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ProduitsUserActivity extends AppCompatActivity{


    ArrayList<Produit> produits = new ArrayList<>();
    LinearLayoutManager layoutManager;
    ProduitsUserAdapter adapter;
    RecyclerView recyclerView;
    private RequestQueue requestQueue;

    Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recompenses);

        Button btnMenu = findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        requestQueue = Volley.newRequestQueue(ProduitsUserActivity.this);

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
                        layoutManager = new LinearLayoutManager(ProduitsUserActivity.this.getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new ProduitsUserAdapter(produits);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        });

        requestQueue.add(request);
    }
}
