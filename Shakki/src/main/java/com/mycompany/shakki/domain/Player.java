/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

/**
 * Represents a player in a game of chess
 * @author Mikko
 */
public class Player implements Comparable<Player> {
    private String name;
    private int wins;
    private int losses;

    /**
     * sets up a new player with name, wins and losses
     * @param name name of the player
     * @param wins wins of the player
     * @param losses losses of the player
     */
    public Player(String name, int wins, int losses) {
        this.name = name;
        this.wins = wins;
        this.losses = losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * compares the wins of two players
     * @param player player being compared to this
     * @return negative integer if this has more wins, positive if player has more wins, 0 if equal amount of wins
     */
    @Override
    public int compareTo(Player player) {
        return player.getWins() - this.getWins();
    }
    
    
}
