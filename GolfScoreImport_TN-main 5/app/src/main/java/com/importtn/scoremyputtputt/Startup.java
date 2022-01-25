package com.importtn.scoremyputtputt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Startup extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_startup);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initializeGame();
            }
        });
    }
    public void initializeGame() {
        Game newGame = new Game();
        Intent i = new Intent(this, PlayerDetails.class);
        i.putExtra("gameObject", newGame);
        startActivity(i);
    }
    @Override
    public void onBackPressed(){
        finishAffinity();
    }

}
