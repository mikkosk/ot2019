/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
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

    public LeaderboardDao() throws SQLException {
    }
 
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
    
    public void updatePlayerWin(String name) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Players SET wins = wins + 1 WHERE name = ?");
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
 
    public void updatePlayerLose(String name) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Players SET losses = losses + 1 WHERE name = ?");
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }
    
    private Connection connectH2() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        try {
            conn.prepareStatement("CREATE TABLE Players (name VARCHAR(255) PRIMARY KEY, wins INTEGER, losses INTEGER)").execute();
        } catch (SQLException t) {
        }
 
        return conn;
    }
} 

