package com.importtn.scoremyputtputt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Serializable {
    private List<Player> players;
    private int currentHole;


    public Game() {
        this.players = new ArrayList<>();
        this.currentHole = 1;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String name){
        for(Player player: players){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentHole() {
        return currentHole;
    }

    public Player getWinner() {
        return Collections.min(players);
    }

    public void setCurrentHole(int currentHole) {
        this.currentHole = currentHole;
    }

    public void nextHole() {
        this.currentHole++;
    }

    public void addPlayer(Player player){
        players.add(player);
    }
}
