/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

import com.mycompany.shakki.dao.ChessDao;
import com.mycompany.shakki.dao.LatestGameDao;
import com.mycompany.shakki.dao.LeaderboardDao;
import com.mycompany.shakki.dao.PiecesDao;
import java.sql.SQLException;

/**
 * Service that connects the domain and the ui of the software
 * @author Mikko
 */
public class ChessService {
    private Chess chess;
    private Board board;
    private ChessDao chessDao;
    private PiecesDao piecesDao;
    private LeaderboardDao leaderboardDao;
    private LatestGameDao latestGameDao;
    private int latestGame;
    
    /**
     * creates new chessService which can be used to operate the game from the ui
     * 
     * @param chessDao interface for the games in the database
     * @param piecesDao interface for the pieces int the database
     * @param leaderboardDao interface for the players in the database
     * @param latestGameDao interface for keeping track of the latest game
     * @throws SQLException if problem with the database
     */
    public ChessService(ChessDao chessDao, PiecesDao piecesDao, LeaderboardDao leaderboardDao, LatestGameDao latestGameDao) throws SQLException {
        this.chessDao = chessDao;
        this.piecesDao = piecesDao;
        this.leaderboardDao = leaderboardDao;
        this.latestGameDao = latestGameDao;
        latestGame = latestGameDao.getLatest();
    };
    
    public Board getBoard() {
        return board;
    }
    
    /**
     * starts a new game of chess with initialized board and adds the players to the database
     * 
     * @param playerOne name of the first player
     * @param playerTwo name of the second player
     * @throws SQLException if problem with the database
     */
    public void newGame(String playerOne, String playerTwo) throws SQLException {
        playerOne = trimName(playerOne);
        playerTwo = trimName(playerTwo);
        chess = new Chess();
        board = chess.getBoard();
        chess.setPlayers(playerOne, playerTwo);
        leaderboardDao.addPlayer(new Player(playerOne, 0, 0));
        leaderboardDao.addPlayer(new Player(playerTwo, 0, 0));
    }
    
    /**
     * completes a turn in the game as described in the Chess-class, if the move is legal
     * 
     * @param clickedX the x-coordinate clicked previously
     * @param clickedY the y-coordinate clicked previously
     * @param x the x-coordinate of the end point of the move
     * @param y the y-coordinate of the end point of the move
     * 
     * @see com.mycompany.shakki.domain.Chess#turn(int, int, int, int) 
     * 
     * @return true if move is legal, else false
     */
    public boolean turn(int clickedX, int clickedY, int x, int y) {
        return chess.turn(clickedX, clickedY, x, y);
    }

    public Chess getChess() {
        return chess;
    }
    
    public boolean getCheckmate() {
        if (chess.getCheckmate()) {
            return true;
        }
        return false;
    }
    
    public boolean getStalemate() {
        if (chess.getStalemate()) {
            return true;
        }
        return false;
    }
    
    public boolean whitesTurn() {
        return chess.isWhitesTurn();
    }
    
    /**
     * ends the game by removing the game from the databases and adding it to the leaderboard
     * returns the result of the game as a String
     * 
     * @return the result of the game as a string
     * 
     * @throws SQLException if problem with the database
     */
    public String endGameAndReturnResult() throws SQLException {
        chessDao.removeGame(chess);
        latestGame = 0;
        latestGameDao.addLatest(0);
        addGameToLeaderboard();
        if (chess.getStalemate()) {
            return "It's a draw";
        } else if (chess.isWhitesTurn()) {
            return "Black wins";
        } else {
            return "White wins";
        }
    }

    public int getLatestGame() {
        return latestGame;
    }
    
    /**
     * saves the game to the database and changes it's id to the latest game
     * 
     * @throws SQLException if problem with the database
     */
    public void quitAndSave() throws SQLException {
        piecesDao.removePieces(chess.getId());
        chessDao.addGame(chess);
        latestGame = chess.getId();
        latestGameDao.addLatest(latestGame);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piecesDao.addPiece(board.getTile(i, j), chess.getId());
            }
        }
    }
    
    /**
     * continues the latest game by setting the chess and the board from the database
     * 
     * @return true, if there is a latest game. false, if no games have been started or latest game was finished
     * @throws SQLException if problem with the database
     */
    public boolean continueGame() throws SQLException {
        chess = chessDao.getChess(latestGame);
        board = chess.getBoard();
        board.fixPieceTypesAfterContinue();
        if (!board.boardIsEmpty()) {
            return true;
        }
        return false;
    }
    
    private void addGameToLeaderboard() throws SQLException {
        if (chess.getStalemate()) {
            return;
        }
        if (!chess.isWhitesTurn()) {
            leaderboardDao.updatePlayerWin(chess.getPlayers().get(0).getName());
            leaderboardDao.updatePlayerLose(chess.getPlayers().get(1).getName());
        } else {
            leaderboardDao.updatePlayerWin(chess.getPlayers().get(1).getName());
            leaderboardDao.updatePlayerLose(chess.getPlayers().get(0).getName());
        }
    }
    
    private String trimName(String name) {
        if (name.length() > 100) {
            name = name.substring(0, 100);
        }
        return name;
    }
}
