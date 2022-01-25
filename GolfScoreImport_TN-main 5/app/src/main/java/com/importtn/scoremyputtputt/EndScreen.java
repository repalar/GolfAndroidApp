package com.importtn.scoremyputtputt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndScreen extends AppCompatActivity {
    Game gameObject;

    RecyclerView mRecyclerView;
    BoardAdapter mAdapter;
    Button finishButton;
    TextView playerWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_end_screen);
        Intent i = getIntent();
        gameObject = (Game)i.getSerializableExtra("gameObject");

        finishButton = findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finishUp();
            }
        });

        playerWinner = (TextView) findViewById(R.id.player_winner);
        String winnerText = "✨ " + gameObject.getWinner().getName() + " wins the game! ✨";
        playerWinner.setText(winnerText);
        playerWinner.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        playerWinner.setTextColor(Color.BLACK);
        playerWinner.setGravity(Gravity.CENTER);
        playerWinner.setPadding(0, 64, 0, 128);

        mRecyclerView = (RecyclerView) findViewById(R.id.listPlayerObjects);
        mAdapter = new BoardAdapter(gameObject);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void finishUp(){
        Intent i = new Intent(this, Startup.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed(){
        finishUp();
    }
}
