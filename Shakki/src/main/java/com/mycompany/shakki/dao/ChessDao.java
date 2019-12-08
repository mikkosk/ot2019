/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.dao;

import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Pawn;
import com.mycompany.shakki.domain.Piece;
import com.mycompany.shakki.domain.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * dao in charge of writing and reading the chess games
 * @author Mikko
 */
public class ChessDao {
    private String url;
    
    /**
     * creates a new chessDao with a connection to desired location
     * @param url the desired location
     */
    public ChessDao(String url) {
        this.url = url;
    }
    
    /**
     * gets a game of chess with desired id (no pieces)
     * @param id the id of the game
     * @return Chess with desired id. If there is no such game in database, returns empty chess
     * @throws SQLException if problem with the database
     */
    public Chess getChess(int id) throws SQLException {
        Chess chess = new Chess();
        chess.getBoard().clearBoard();
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Chess WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                chess.setWhitesTurn(results.getBoolean("whitesTurn"));
                chess.setId(results.getInt("id"));
                chess.setPlayers(results.getString("playerOne"), results.getString("playerTwo"));
                getPiecesInGame(chess);
            }
        }
        return chess;
    }
 
    /**
     * adds a game of chess to the database
     * @param chess the game being saved
     * @throws SQLException if problem with the database
     */
    public void addGame(Chess chess) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Chess WHERE id = ?");
            stmt.setInt(1, chess.getId());
            ResultSet rs = stmt.executeQuery();
            if (!rs.first()) {
                stmt = connection.prepareStatement("INSERT INTO Chess (id, whitesTurn, playerOne, playerTwo) VALUES (?, ?, ?, ?)");
                stmt.setInt(1, chess.getId());
                stmt.setBoolean(2, chess.isWhitesTurn());
                stmt.setString(3, chess.getPlayers().get(0).getName());
                stmt.setString(4, chess.getPlayers().get(1).getName());
                stmt.executeUpdate();
            } else {
                updateGame(chess);
            }
        }
    }
    
    /**
     * updates an already existing game
     * @param chess game being updated
     * @throws SQLException if problem with the database
     */
    public void updateGame(Chess chess) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Chess SET whitesTurn = ? WHERE id = ?");
            stmt.setBoolean(1, chess.isWhitesTurn());
            stmt.setInt(2, chess.getId());
            stmt.executeUpdate();
        }
    }
    
    /**
     * removes a game from the database
     * @param chess game being removed
     * @throws SQLException if problem with the database
     */
    public void removeGame(Chess chess) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Pieces WHERE chessId = ?");
            stmt.setInt(1, chess.getId());
            stmt.executeUpdate();
            stmt = connection.prepareStatement("DELETE FROM Chess WHERE id = ?");
            stmt.setInt(1, chess.getId());
            stmt.executeUpdate();
        }
    }
    
    private void getPiecesInGame(Chess chess) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Pieces JOIN Chess ON Pieces.chessId = Chess.id WHERE Pieces.chessId = ?");
            stmt.setInt(1, chess.getId());
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                Piece piece = new Piece(results.getString("type"), results.getBoolean("white"));
                piece.setHasMoved(results.getBoolean("hasMoved"));
                chess.getBoard().getTile(results.getInt("x"), results.getInt("y")).setPiece(piece);
            }
        }
    }
    
    /**
     * drops all tables from the database
     * @throws SQLException if problem with the database
     */
    public void dropTables() throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE Chess, Pieces, Players, LatestGame IF EXISTS");
            stmt.execute();
        }
    }
    
    private Connection connectH2() throws SQLException {
        Connection conn = DriverManager.getConnection(url, "sa", "");
        try {
            conn.prepareStatement("CREATE TABLE Chess (id INTEGER PRIMARY KEY, whitesTurn BOOLEAN, playerOne VARCHAR(255), playerTwo VARCHAR(255))").execute();
            conn.prepareStatement("CREATE TABLE Pieces (id INTEGER AUTO_INCREMENT PRIMARY KEY, x INTEGER, y INTEGER, type VARCHAR(255), white BOOLEAN, hasMoved BOOLEAN, chessId INTEGER, FOREIGN KEY(chessId) REFERENCES Chess(id))").execute();
        } catch (SQLException t) {
        }
 
        return conn;
    }
}
