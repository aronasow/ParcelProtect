package com.example.asow.parcelprotect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;

public class InterfaceChoix extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interfacechoix);

        // Boutton de l'interface choix
        Button btenvoye = (Button) findViewById(R.id.buttenvoye);
        Button btrecu = (Button) findViewById(R.id.buttrecu);
        Button btenvoie = (Button) findViewById(R.id.buttenvoie);

        btenvoye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterfaceChoix.this, ColisEnvoye.class);
                startActivity(intent);
            }
        });
        btrecu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterfaceChoix.this, ColisRecu.class);
                startActivity(intent);
            }
        });
        btenvoie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterfaceChoix.this, MapDeverrouiller.class);
                startActivity(intent);
            }
        });
    }

}
