package com.example.caringverseapp.LogInActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caringverseapp.AdminMenuActivity.AdminMenu;
import com.example.caringverseapp.DataBaseActivity.PutData;
import com.example.caringverseapp.Domain.Builders.IUtilisateurBuilder;
import com.example.caringverseapp.Domain.Builders.UtilisateurBuilder;
import com.example.caringverseapp.Domain.KeyObjectFinder;
import com.example.caringverseapp.Domain.Utilisateur;
import com.example.caringverseapp.PHPConnector.PhpResources;
import com.example.caringverseapp.R;
import com.example.caringverseapp.SignUpActivity.SignUp;

import com.example.caringverseapp.MenuActivity.Menu;
import com.example.caringverseapp.Validators.CredentialsValidator;
import com.google.android.material.textfield.TextInputEditText;


public class LogIn extends AppCompatActivity {

    EditText textInputEditTextEmail, textInputEditTextMotPasse;
    Button btnLogIn;
    Button btnSignUp;

    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        textInputEditTextEmail = findViewById(R.id.emailLogIn);
        textInputEditTextMotPasse = findViewById(R.id.motpasseLogIn);

        btnLogIn = findViewById(R.id.btnConnexion);
        btnSignUp = findViewById(R.id.btnSignUpLogIn);

        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);

        btnLogIn.setOnClickListener(v -> {
            String email, motpasse;
            email = String.valueOf(textInputEditTextEmail.getText());
            motpasse = String.valueOf(textInputEditTextMotPasse.getText());

            if (validateAndGenerateErrorMessage()) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[2];
                    field[0] = "email";
                    field[1] = "motpasse";

                    String[] data = new String[2];
                    data[0] = email;
                    data[1] = motpasse;

                    PutData putData = new PutData(PhpResources.LoginInURL, "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {

                            String result = putData.getResult();
                            String[] splittedResult = null;

                            Utilisateur utilisateur = null;

                            if (!result.isEmpty() && !result.equals("Username or Password wrong")) {
                                splittedResult = result.split(",");

                                utilisateur = new UtilisateurBuilder()
                                        .setId(splittedResult[0])
                                        .setNom(splittedResult[1])
                                        .setEmail(splittedResult[2])
                                        .setTelephone(splittedResult[3])
                                        .setDateNaissance(splittedResult[4])
                                        .setIsAdmin(splittedResult[6])
                                        .setPoints(splittedResult[7])
                                        .build();
                            }


                            if (utilisateur != null) {

                                Intent intent;

                                if (utilisateur.getIsAdmin().equals("0")) {
                                    intent = new Intent(getApplicationContext(), Menu.class);
                                } else {
                                    intent = new Intent(getApplicationContext(), AdminMenu.class);
                                }
                                intent.putExtra(KeyObjectFinder.Utilisateur.toString(), utilisateur);
                                startActivity(intent);
                                finish();

                            } else {
                                errorMessage.setText(result);
                                errorMessage.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            } else {
                errorMessage.setVisibility(View.VISIBLE);
            }
        });


        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        });
    }


    private boolean validateAndGenerateErrorMessage() {

        if (String.valueOf(textInputEditTextEmail.getText()).equals("")) {
            errorMessage.setText("Ecrire un email");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(String.valueOf(textInputEditTextEmail.getText())).matches()) {
            errorMessage.setText("Email n'a pas la forme correcte");
            return false;
        }
        if(String.valueOf(textInputEditTextMotPasse.getText()).equals("")){
            errorMessage.setText("Ecrire un mot de passe");
            return false;
        }

        return true;
    }
}
