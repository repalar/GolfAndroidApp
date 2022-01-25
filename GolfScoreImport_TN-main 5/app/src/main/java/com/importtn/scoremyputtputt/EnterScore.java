package com.importtn.scoremyputtputt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class EnterScore extends AppCompatActivity {
    Game gameObject;
    Player currentPlayer;
    List<Player> players;
    ListIterator<Player> playerIterator;
    Stack<ActionEnum> actionHistory = new Stack<>();
    Stack<ListIterator<Player>> iteratorHistory = new Stack<>();
    Stack<Player> currPlayerHistory = new Stack<>();
    // Components
    TextView totalScore;
    TextSwitcher holeSwitcher;
    TextSwitcher nameSwitcher;
    ImageButton nextHoleButton;
    ImageButton nextPlayerButton;
    Button exitGameButton;
    Button incrementScore;
    Button decrementScore;
    Button overviewButton;
    EditText strokesDisplay;
    String p1_hole_ind;
    String p2_hole_ind;
    String finish_game_txt;
    String exit_game_txt;

    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_enter_score);

        Intent i = getIntent();
        gameObject = (Game) i.getSerializableExtra("gameObject");
        currentPlayer = (Player) i.getSerializableExtra("currentPlayer");

        Context context = getApplicationContext();

        holeSwitcher = findViewById(R.id.holeSwitcher);
        holeSwitcher.setInAnimation(context, android.R.anim.slide_in_left);
        holeSwitcher.setOutAnimation(context, android.R.anim.slide_out_right);
        nameSwitcher = findViewById(R.id.nameSwitcher);
        nameSwitcher.setInAnimation(context, android.R.anim.slide_in_left);
        nameSwitcher.setOutAnimation(context, android.R.anim.slide_out_right);
        nextHoleButton = findViewById(R.id.nextHoleButton);
        nextPlayerButton = findViewById(R.id.nextPlayerButton);
        exitGameButton = findViewById(R.id.exitGameButton);
        incrementScore = findViewById(R.id.tempIncreasePlayers);
        decrementScore = findViewById(R.id.tempReducePlayers);
        strokesDisplay = findViewById(R.id.tempNumberPlayers);
        overviewButton = findViewById(R.id.overviewButton);
        ImageButton prevHoleButton = findViewById(R.id.prevHoleButton);
        ImageButton prevPlayerButton = findViewById(R.id.prevPlayerButton);
        totalScore = findViewById(R.id.totalscore);


        prevHoleButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                prevHole();
            }
        });
        prevPlayerButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                prevPlayer();
            }
        });


        nextHoleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextHole();
            }
        });

        nextPlayerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nextPlayer();
            }
        });

        exitGameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                exitGame();
            }
        });

        incrementScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                incrementStroke();
            }
        });

        decrementScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                decrementStroke();
            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goOverview();
            }
        });

        p1_hole_ind = getResources().getString(R.string.p1_hole_ind);
        p2_hole_ind = getResources().getString(R.string.p2_hole_ind);
        finish_game_txt = getResources().getString(R.string.finish_gameTxt);
        exit_game_txt = getResources().getString(R.string.exit_gameTxt);

        players = gameObject.getPlayers();
        playerIterator = players.listIterator();
        if (currentPlayer == null) {
            currentPlayer = playerIterator.next();
        } else {
            boolean t = true;
            while (t) {
                Player tmp = playerIterator.next();
                if (tmp.getName().equals(currentPlayer.getName())) {
                    t = false;
                }
            }
        }

        // hide next/prev player buttons if just one player
        if (players.size() < 2) {
            nextPlayerButton.setVisibility(View.INVISIBLE);
            prevPlayerButton.setVisibility(View.INVISIBLE);
        }

        holeSwitcher.setText(p1_hole_ind + " " + gameObject.getCurrentHole() + " " + p2_hole_ind);
        nameSwitcher.setText(currentPlayer.getName());
        totalScore.setText("Total: "+ currentPlayer.getTotalScore());
        strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
    }

    @SuppressLint("SetTextI18n")
    private void incrementStroke() {
        int[] scores = currentPlayer.getScores();
        scores[gameObject.getCurrentHole() - 1]++;
        currentPlayer.setScores(scores);
        totalScore.setText("Total: "+ currentPlayer.getTotalScore());
        strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
    }

    @SuppressLint("SetTextI18n")
    private void decrementStroke() {
        int[] scores = currentPlayer.getScores();
        if (scores[gameObject.getCurrentHole() - 1] > 0) {
            scores[gameObject.getCurrentHole() - 1]--;
            currentPlayer.setScores(scores);
            totalScore.setText("Total: "+ currentPlayer.getTotalScore());
            strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
        }
    }

    @SuppressLint("SetTextI18n")
    private void prevHole() {
        if (gameObject.getCurrentHole() >1) {
            gameObject.setCurrentHole(gameObject.getCurrentHole() -1);
            holeSwitcher.setText(p1_hole_ind + " " + gameObject.getCurrentHole() + " " + p2_hole_ind);
            iteratorHistory.push(playerIterator);
            currPlayerHistory.push(currentPlayer);
            playerIterator = players.listIterator();
            currentPlayer = playerIterator.next();
            if (players.size() > 1) {
                nameSwitcher.setText(currentPlayer.getName());
            }
            totalScore.setText("Total: "+ currentPlayer.getTotalScore());
            strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));

            if (gameObject.getCurrentHole() == 17) {
                exitGameButton.setText(exit_game_txt);
                nextHoleButton.setVisibility(View.VISIBLE);
            }
        }

        actionHistory.push(ActionEnum.hole);

    }

    @SuppressLint("SetTextI18n")
    private void nextHole() {
        if (gameObject.getCurrentHole() < 17) {
            gameObject.setCurrentHole(gameObject.getCurrentHole() + 1);
            holeSwitcher.setText(p1_hole_ind + " " + gameObject.getCurrentHole() + " " + p2_hole_ind);
            iteratorHistory.push(playerIterator);
            currPlayerHistory.push(currentPlayer);
            playerIterator = players.listIterator();
            currentPlayer = playerIterator.next();
            if (players.size() > 1) {
                nameSwitcher.setText(currentPlayer.getName());
            }
            totalScore.setText("Total: "+ currentPlayer.getTotalScore());
            strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
        } else {
            gameObject.setCurrentHole(gameObject.getCurrentHole() + 1);
            holeSwitcher.setText(p1_hole_ind + " " + gameObject.getCurrentHole() + " " + p2_hole_ind);
            iteratorHistory.push(playerIterator);
            currPlayerHistory.push(currentPlayer);
            playerIterator = players.listIterator();
            currentPlayer = playerIterator.next();
            if (players.size() > 1) {
                nameSwitcher.setText(currentPlayer.getName());
            }
            totalScore.setText("Total: "+ currentPlayer.getTotalScore());
            strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
            exitGameButton.setText(finish_game_txt);
            nextHoleButton.setVisibility(View.INVISIBLE);
        }
        actionHistory.push(ActionEnum.hole);
    }


    @SuppressLint("SetTextI18n")
    private void nextPlayer() {
        if (!playerIterator.hasNext()) {
            playerIterator = players.listIterator();
        }
        currentPlayer = playerIterator.next();
        nameSwitcher.setText(currentPlayer.getName());
        totalScore.setText("Total: "+ currentPlayer.getTotalScore());
        strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
        actionHistory.push(ActionEnum.player);
    }

    @SuppressLint("SetTextI18n")
    private void prevPlayer() {
        playerIterator.previous();
        if (!playerIterator.hasPrevious()) {
            playerIterator = players.listIterator(players.size() - 1);
            currentPlayer = playerIterator.next();
        } else {
            currentPlayer = playerIterator.previous();
            playerIterator.next();
        }
        nameSwitcher.setText(currentPlayer.getName());
        totalScore.setText("Total: "+ currentPlayer.getTotalScore());
        strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
    }

    private void exitGame() {
        Intent i = new Intent(this, EndScreen.class);
        i.putExtra("gameObject", gameObject);
        startActivity(i);
    }

    private void goOverview() {
        Intent i = new Intent(this, Overview.class);
        i.putExtra("gameObject", gameObject);
        i.putExtra("currentPlayer", currentPlayer);
        startActivity(i);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBackPressed() {
        if (actionHistory.empty()) {
            Game newGame = new Game();
            Intent i = new Intent(this, PlayerDetails.class);
            i.putExtra("gameObject", newGame);
            startActivity(i);
            return;
        } else {
            ActionEnum lastAction = actionHistory.pop();
            switch (lastAction) {
                case hole:
                    if (gameObject.getCurrentHole() < 18) {
                        prevHole();
                    } else {
                        gameObject.setCurrentHole(gameObject.getCurrentHole() - 1);
                        holeSwitcher.setText(p1_hole_ind + " " + gameObject.getCurrentHole() + " " + p2_hole_ind);
                        playerIterator = iteratorHistory.pop();
                        currentPlayer = currPlayerHistory.pop();
                        nameSwitcher.setText(currentPlayer.getName());
                        totalScore.setText("Total: "+ currentPlayer.getTotalScore());
                        strokesDisplay.setText(Integer.toString(currentPlayer.getScores()[gameObject.getCurrentHole() - 1]));
                        exitGameButton.setText(exit_game_txt);
                        nextHoleButton.setVisibility(View.VISIBLE);
                    }
                    break;
                case player:
                    prevPlayer();
                    break;
            }
        }
    }

    private enum ActionEnum {
        hole,
        player
    }

}