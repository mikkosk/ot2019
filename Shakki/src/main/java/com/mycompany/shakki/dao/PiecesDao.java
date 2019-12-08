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
import com.mycompany.shakki.domain.Tile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * dao in charge of reading and writing pieces in database
 * @author Mikko
 */
public class PiecesDao {
    private String url;
    
    /**
     * creates a new piecesDao with a connection to desired location
     * @param url the desired location
     */
    public PiecesDao(String url) {
        this.url = url;
    }
 
    /**
     * adds a piece to the database
     * @param tile tile that the piece is located to
     * @param id id of the game the piece exists in
     * @throws SQLException if problem with the database
     */
    public void addPiece(Tile tile, int id) throws SQLException {
        Piece piece = tile.getPiece();
        if (piece == null) {
            return;
        }
        
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Pieces (x, y, type, white, hasMoved, chessId) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, tile.getX());
            stmt.setInt(2, tile.getY());
            stmt.setString(3, piece.getType());
            stmt.setBoolean(4, piece.isWhite());
            stmt.setBoolean(5, piece.isHasMoved());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        }
    } 
    
    /**
     * removes a piece from the database
     * @param id id of the piece being removed
     * @throws SQLException if problem with the database
     */
    public void removePieces(int id) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Pieces WHERE chessId = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
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
