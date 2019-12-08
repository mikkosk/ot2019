/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.dao;

/**
 * dao in charge of writing and reding the players from the database
 * @author Mikko
 */

import com.mycompany.shakki.domain.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardDao {
    private String url;
    
    /**
     * creates a new leaderboardDao with a connection to desired location
     * @param url the desired location
     */
    public LeaderboardDao(String url) {
        this.url = url;
    }
 
    /**
     * gets all the players from the database
     * @return list of players in order of wins (most to least)
     * @throws SQLException if problem with the database
     */
    public List<Player> getPlayers() throws SQLException {
        List<Player> players = new ArrayList<>();
        try (Connection connection = connectH2();
                ResultSet results = connection.prepareStatement("SELECT * FROM Players").executeQuery()) {
            while (results.next()) {
                players.add(new Player(results.getString("name"), results.getInt("wins"), results.getInt("losses")));
            }
        }
        Collections.sort(players);
        return players;
    }
 
    /**
     * adds a player to the database
     * @param player player being added
     * @throws SQLException if problem with the database
     */
    public void addPlayer(Player player) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Players WHERE name = ?");
            stmt.setString(1, player.getName());
            ResultSet rs = stmt.executeQuery();
            if (!rs.first()) {
                stmt = connection.prepareStatement("INSERT INTO Players (name, wins, losses) VALUES (?, ?, ?)");
                stmt.setString(1, player.getName());
                stmt.setInt(2, 0);
                stmt.setInt(3, 0);
                stmt.executeUpdate();
            }
        }
    }
    
    /**
     * grows the wins of the player by one
     * @param name updated player
     * @throws SQLException if problem with the database
     */
    public void updatePlayerWin(String name) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Players SET wins = wins + 1 WHERE name = ?");
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
 
    /**
     * grows the losses of the player by one
     * @param name updated player
     * @throws SQLException if problem with the database
     */
    public void updatePlayerLose(String name) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Players SET losses = losses + 1 WHERE name = ?");
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
    
    private Connection connectH2() throws SQLException {
        Connection conn = DriverManager.getConnection(url, "sa", "");
        try {
            conn.prepareStatement("new)").execute();
        } catch (SQLException t) {
        }
 
        return conn;
    }
} 

