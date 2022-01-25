package com.importtn.scoremyputtputt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Overview extends AppCompatActivity {
    Game gameObject;
    Player currentPlayer;

    Button saveButton;
    Button closeButton;
    LinearLayout display;
    List<View> items = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_overview_screen);
        Intent i = getIntent();
        gameObject = (Game)i.getSerializableExtra("gameObject");
        currentPlayer = (Player) i.getSerializableExtra("currentPlayer");

        closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finishUp();
            }
        });
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveChanges();
            }
        });

        display = findViewById(R.id.listOverViewObjects);
        for(Player player: gameObject.getPlayers()){
            View tmp = getLayoutInflater().inflate(R.layout.overview_list_item, display, false);
            TextView nameHolder = tmp.findViewById(R.id.nameHolder);
            nameHolder.setText(player.getName());

            for(int x = 1; x <= 18; x ++){
                EditText stroke = tmp.findViewById(tmp.getResources().getIdentifier("strokeHolder" + x, "id", "com.importtn.scoremyputtputt"));
                stroke.setText(Integer.toString(player.getScores()[x-1]));
            }
            display.addView(tmp);
            items.add(tmp);
        }


    }

    private void finishUp(){
        Intent i = new Intent(this, EnterScore.class);
        i.putExtra("gameObject", gameObject);
        i.putExtra("currentPlayer", currentPlayer);
        startActivity(i);
    }

    private void saveChanges(){
        for(View view: items){
            TextView nameHolder = view.findViewById(R.id.nameHolder);
            Player player = gameObject.getPlayer(nameHolder.getText().toString());
            int[] scores = player.getScores();

            for(int x = 1; x <= 18; x ++){
                EditText stroke = view.findViewById(view.getResources().getIdentifier("strokeHolder" + x, "id", "com.importtn.scoremyputtputt"));
                int newScore = Integer.parseInt(stroke.getText().toString());
                scores[x-1] = newScore;
            }
        }
    }
    @Override
    public void onBackPressed(){
        finishUp();
    }

}
