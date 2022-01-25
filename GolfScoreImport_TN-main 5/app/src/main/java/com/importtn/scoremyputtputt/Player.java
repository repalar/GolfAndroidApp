package com.importtn.scoremyputtputt;

import java.io.Serializable;

public class Player implements Serializable, Comparable<Player> {
    private String name;
    private String icon; // path to where icon is saved
    private int[] scores;

    public Player(String name, String icon) {
        this.name = name;
        this.icon = icon;
        this.scores = new int[18];
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    private void setIcon(String icon) {
        this.icon = icon;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

    public int getTotalScore(){
        int sum = 0;
        for(int score: scores){
            sum += score;
        }
        return sum;
    }

    @Override
    public int compareTo(Player otherPlayer) {
        if(otherPlayer.getTotalScore() > this.getTotalScore()){
            return -1;
        } else if (otherPlayer.getTotalScore() < this.getTotalScore()){
            return 1;
        } else {
            return 0;
        }
    }
}
