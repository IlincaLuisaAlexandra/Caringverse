package com.example.caringverseapp.SignUpActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caringverseapp.DataBaseActivity.PutData;
import com.example.caringverseapp.LogInActivity.LogIn;
import com.example.caringverseapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText textInputEditTexttnom, textInputEditTextemailSignUp, textInputEditTexttelephone, textInputEditTextdate, textInputEditTextmotPasseSignUp;
    Button btnSignUp;

    TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        textInputEditTexttnom = findViewById(R.id.nomSignUp);
        textInputEditTextemailSignUp = findViewById(R.id.emailSignUp);
        textInputEditTextdate = findViewById(R.id.dateSignUp);
        textInputEditTexttelephone = findViewById(R.id.telephoneSignUp);
        textInputEditTextmotPasseSignUp = findViewById(R.id.motpasseSignUp);

        btnSignUp = findViewById(R.id.btnSignUp);

        errorMessage = findViewById(R.id.errorMessage);
        errorMessage.setVisibility(View.GONE);

        btnSignUp.setOnClickListener(v -> {

            String nom, email, telephone, datenaissance, motpasse;
            nom = String.valueOf(textInputEditTexttnom.getText());
            email = String.valueOf(textInputEditTextemailSignUp.getText());
            telephone = String.valueOf(textInputEditTexttelephone.getText());
            datenaissance = String.valueOf(textInputEditTextdate.getText());
            motpasse = String.valueOf(textInputEditTextmotPasseSignUp.getText());

            if (validateAndGenerateErrorMessage()) {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[5];

                    field[0] = "nom";
                    field[1] = "email";
                    field[2] = "telephone";
                    field[3] = "datenaissance";
                    field[4] = "motpasse";

                    String[] data = new String[5];
                    data[0] = nom;
                    data[1] = email;
                    data[2] = telephone;
                    data[3] = datenaissance;
                    data[4] = motpasse;

                    PutData putData = new PutData("http://192.168.0.115/LoginRegister/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Sign Up Success")) {
                                Intent intent = new Intent(getApplicationContext(), LogIn.class);
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
    }

    private boolean validateAndGenerateErrorMessage() {

        if (String.valueOf(textInputEditTexttnom.getText()).equals("")) {
            errorMessage.setText("Ecrire un nom");
            return false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(String.valueOf(textInputEditTextemailSignUp.getText())).matches()) {
            errorMessage.setText("Email n'a pas la forme correcte");
            return false;
        }
        if(String.valueOf(textInputEditTexttelephone.getText()).equals("") &&
                String.valueOf(textInputEditTexttelephone.getText()).length() == 10 &&
                !Patterns.PHONE.matcher(String.valueOf(textInputEditTexttelephone.getText())).matches()){

            errorMessage.setText("Telephone n'est pas correct");
            return false;
        }
        if(String.valueOf(textInputEditTextdate.getText()).equals("") &&
                !Pattern.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$",
                String.valueOf(textInputEditTextdate.getText()))){

            errorMessage.setText("La date doit avoir la forme YYYY-MM-DD");
            return false;
        }

        if (String.valueOf(textInputEditTextmotPasseSignUp.getText()).equals("")) {
            errorMessage.setText("Ecrire un mot de passe");
            return false;
        }

        return true;
    }
}