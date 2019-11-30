/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
 *
 * @author Mikko
 */
public class ChessDao {
    public Chess getChess() throws SQLException {
        Chess chess = new Chess();
        chess.getBoard().clearBoard();
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Chess");
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
    
    public void updateGame(Chess chess) throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Chess SET whitesTurn = ? WHERE id = ?");
            stmt.setBoolean(1, chess.isWhitesTurn());
            stmt.setInt(2, chess.getId());
            stmt.executeUpdate();
        }
    }
    
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
    
    public void dropTables() throws SQLException {
        try (Connection connection = connectH2()) {
            PreparedStatement stmt = connection.prepareStatement("DROP TABLE Chess, Pieces, Players IF EXISTS");
            stmt.execute();
        }
    }
    
    private Connection connectH2() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        try {
            conn.prepareStatement("CREATE TABLE Chess (id INTEGER PRIMARY KEY, whitesTurn BOOLEAN, playerOne VARCHAR(255), playerTwo VARCHAR(255))").execute();
            conn.prepareStatement("CREATE TABLE Pieces (id INTEGER AUTO_INCREMENT PRIMARY KEY, x INTEGER, y INTEGER, type VARCHAR(255), white BOOLEAN, hasMoved BOOLEAN, chessId INTEGER, FOREIGN KEY(chessId) REFERENCES Chess(id))").execute();
        } catch (SQLException t) {
        }
 
        return conn;
    }
}
