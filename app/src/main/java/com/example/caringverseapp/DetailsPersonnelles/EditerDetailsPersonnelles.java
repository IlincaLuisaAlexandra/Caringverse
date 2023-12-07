package com.example.caringverseapp.DetailsPersonnelles;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.caringverseapp.Domain.Builders.UtilisateurBuilder;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.R;
import com.example.caringverseapp.Utils.Mapper.AnnounceMapper;
import com.example.caringverseapp.Utils.Mapper.IObjectMapper;
import com.example.caringverseapp.ViewAnnouncesActivity.Announce;
import com.example.caringverseapp.ViewAnnouncesActivity.AnnounceAdapter;
import com.example.caringverseapp.ViewAnnouncesActivity.ViewAnnounces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class EditerDetailsPersonnelles extends AppCompatActivity {

    Button btnMenu;
    Button btnFinishEdit;

    TextView errorMessage;

    EditText nom;
    EditText email;
    EditText telephone;
    EditText datanaissance;

    Utilisateur utilisateur;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editer_details_personnelles);

        btnMenu = findViewById(R.id.btnMenu);
        btnFinishEdit = findViewById(R.id.btnFinishEdit);

        nom = findViewById(R.id.nomEditDetailsPersonnele);
        email = findViewById(R.id.emailEditDetailsPersonnele);
        telephone = findViewById(R.id.telephoneEditDetailsPersonnele);
        datanaissance = findViewById(R.id.dateEditDetailsPersonnele);

        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);

        utilisateur = getIntent().getSerializableExtra(KeyObjectFinder.Utilisateur.toString(), Utilisateur.class);

        requestQueue = Volley.newRequestQueue(EditerDetailsPersonnelles.this);

        nom.setText(utilisateur.getNom());
        email.setText(utilisateur.getEmail());
        telephone.setText(utilisateur.getTelephone());
        datanaissance.setText(utilisateur.getDateNaissance());

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
            startActivity(intent);
            finish();
        });

        btnFinishEdit.setOnClickListener(v -> {

            if (validateAndGenerateErrorMessage()) {
                StringRequest request = new StringRequest(Request.Method.POST, PhpResources.UpdateUtilisateurDetailsURL,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.getBoolean("success")) {

                                    utilisateur.setNom(String.valueOf(nom.getText()));
                                    utilisateur.setTelephone(String.valueOf(telephone.getText()));
                                    utilisateur.setEmail(String.valueOf(email.getText()));
                                    utilisateur.setDateNaissance(String.valueOf(datanaissance.getText()));

                                    Intent intent = new Intent(getApplicationContext(), DetailsPersonnelles.class);
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

                        params.put("id", utilisateur.getId());
                        params.put("nom", String.valueOf(nom.getText()));
                        params.put("email", String.valueOf(email.getText()));
                        params.put("telephone", String.valueOf(telephone.getText()));
                        params.put("datenaissance", String.valueOf(datanaissance.getText()));

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

        if (String.valueOf(nom.getText()).equals("")) {
            errorMessage.setText("Nom shoudn't be empty");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(String.valueOf(email.getText())).matches()) {
            errorMessage.setText("Email it's not respecting the format");
            return false;
        }
        if (String.valueOf(telephone.getText()).equals("") &&
                String.valueOf(telephone.getText()).length() == 10 &&
                !Patterns.PHONE.matcher(String.valueOf(telephone.getText())).matches()) {

            errorMessage.setText("Telephone it's incorrect");
            return false;
        }
        if (String.valueOf(datanaissance.getText()).equals("") &&
                !Pattern.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$",
                        String.valueOf(datanaissance.getText()))) {

            errorMessage.setText("Date is incorect format should be YYYY-MM-DD");
            return false;
        }
        return true;
    }
}