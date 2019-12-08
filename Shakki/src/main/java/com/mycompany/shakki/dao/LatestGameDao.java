/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * dao in charge of the id of the latest game
 * @author Mikko
 */
public class LatestGameDao {
    private String url;
    
    /**
     * creates a new latestGameDao with a connection to desired location
     * @param url the desired location
     */
    public LatestGameDao(String url) {
        this.url = url;
    }
    
    /**
     * returns the id of the latest game
     * @return id (int) of the latest game, 0 if there is no games in the database
     * @throws SQLException if problem with the database
     */
    public int getLatest() throws SQLException {
        List<Integer> id = new ArrayList<>();
        try (Connection connection = connectH2();
                ResultSet results = connection.prepareStatement("SELECT * FROM LatestGame").executeQuery()) {
            while (results.next()) {
                id.add(results.getInt("latestGame"));
            }
        }
        return id.get(id.size() - 1);
    }
 
    /**
     * adds the id of the latest game
     * @param id id of the game
     * @throws SQLException if problem with the database
     */
    public void addLatest(int id) throws SQLException {
        System.out.println("lisätty");
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO LatestGame (latestGame) VALUES (?)");
            stmt.setInt(1, id);
            stmt.execute();
            System.out.println("toimi");
        }
    }
    
    
    private Connection connectH2() throws SQLException {
        Connection conn = DriverManager.getConnection(url, "sa", "");
        try {
            conn.prepareStatement("CREATE TABLE LatestGame (id INTEGER AUTO_INCREMENT PRIMARY KEY, latestGame INTEGER)").execute();
        } catch (SQLException t) {
        }
 
        return conn;
    }
}
