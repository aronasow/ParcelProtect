package com.example.asow.parcelprotect;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Amady on 16/09/2017.
 */

public class Enregistrement extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enregistrement);

        // Element de remplissage
        final EditText champNom = (EditText) findViewById(R.id.nomClient);
        final EditText champPrenom = (EditText) findViewById(R.id.prenomClient);
        final EditText champEmail = (EditText) findViewById(R.id.emailClient);
        final EditText champTelephone = (EditText) findViewById(R.id.telephoneClient);
        final EditText champPseudo = (EditText) findViewById(R.id.pseudoClient);
        final EditText champMp = (EditText) findViewById(R.id.mpClient);
        final EditText champCmp = (EditText) findViewById(R.id.cmpClient);
        // Button pour envoyer les élements dans la base
        Button signIn = (Button) findViewById(R.id.btenregistrer);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // récupération des longueurs de données dans les champs
                int nomsize = champNom.getText().length();
                int prenomsize = champPrenom.getText().length();
                int emailsize = champEmail.getText().length();
                int telephonezisize = champTelephone.getText().length();
                int pseudosize = champPseudo.getText().length();
                int mpsize = champMp.getText().length();
                int cmpsize = champCmp.getText().length();
                // Vérifications de la contenabilité des champs
                if (nomsize > 0 && prenomsize > 0 && emailsize > 0 && telephonezisize > 0 && pseudosize > 0 && mpsize > 0 && cmpsize > 0 )
                {
                    //String user = UserEditText.getText().toString();
                    //String pass = PassEditText.getText().toString();
                    // On appelle la fonction doLogin qui va communiquer avec le PHP et vérifier les logs
                    //doLogin(user, pass);
                    Intent intent = new Intent(Enregistrement.this, InterfaceChoix.class);
                    startActivity(intent);
                }
                else
                    createDialog("Attention", "Un des champs n'est pas renseigné!");
            }
        });
    }

    private void createDialog(String title, String text)
    {
        // Création d'une popup affichant un message
        AlertDialog ad = new AlertDialog.Builder(this)
                .setPositiveButton("Ok", null).setTitle(title).setMessage(text)
                .create();
        ad.show();

    }
}
