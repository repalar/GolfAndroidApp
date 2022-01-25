package com.importtn.scoremyputtputt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerDetails extends AppCompatActivity {
    Game gameObject;
    ImageButton finalizeButton;
    List<View> items = new ArrayList<>();
    Button addPlayer;
    LinearLayout listPlayerItems;
    int playersAdded = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player_details);

        finalizeButton = findViewById(R.id.finalizePlayers);
        addPlayer = findViewById(R.id.addPlayerButton);
        listPlayerItems = findViewById(R.id.listPlayerDetailRows);

        Intent i = getIntent();
        gameObject = (Game) i.getSerializableExtra("gameObject");

        finalizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finalizePlayers();
            }
        });

        addPlayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addPlayerItem();
            }
        });

        for (int x = 0; x < 4; x++) {
            addPlayerItem();
        }

    }


    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void addPlayerItem() {
        View tmp = getLayoutInflater().inflate(R.layout.player_detail_row, listPlayerItems, false);

        EditText playerText = tmp.findViewById(R.id.newNameHolder);

        playerText.setText("Player " + (playersAdded + 1));

        playerText.setOnTouchListener((v, event) -> {
            if (MotionEvent.ACTION_UP == event.getAction()) {
                if (playerText.getText().toString().startsWith("Player")) {
                    playerText.setText("");
                }
            }
            return false;
        });

        Button buttonRemove = (Button) tmp.findViewById(R.id.removeRowButton);

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPlayerItems.removeView((View) v.getParent());
                items.remove((View) v.getParent());
            }
        });
        listPlayerItems.addView(tmp);
        items.add(tmp);
        playersAdded++;
    }


    private void finalizePlayers() {

        if(items.size() < 1){
            Toast toast = Toast.makeText(this, "Need at least one player!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        for (View item : items) {
            EditText nameH = item.findViewById(R.id.newNameHolder);
            String name = nameH.getText().toString();
            if (name.length() < 1) {
                Toast toast = Toast.makeText(this, "Names cannot be empty!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            int samecount = 0;
            for (View item2 : items) {
                EditText nameH2 = item2.findViewById(R.id.newNameHolder);
                String name2 = nameH2.getText().toString();
                if (name2.equals(name)) {
                    samecount++;
                    System.out.println("Found dupe for " + name);
                }
            }
            if (samecount > 1) {
                Toast toast = Toast.makeText(this, "Cannot have duplicate names!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
        }

        for (View item : items) {
            EditText nameH = item.findViewById(R.id.newNameHolder);
            String name = nameH.getText().toString();
            if (name.length() < 1) {
                Toast toast = Toast.makeText(this, "Names cannot be empty!", Toast.LENGTH_SHORT);
                return;
            }
            gameObject.addPlayer(new Player(name, ""));
        }


        Intent i = new Intent(this, EnterScore.class);
        i.putExtra("gameObject", gameObject);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Startup.class);
        startActivity(i);
    }

}