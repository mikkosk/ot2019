/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.gui;

/**
 *
 * @author Mikko
 */

import com.mycompany.shakki.domain.Board;
import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Piece;
import com.mycompany.shakki.domain.Player;
import com.mycompany.shakki.domain.Tile;
import dao.ChessDao;
import dao.LeaderboardDao;
import dao.PiecesDao;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShakkiUI extends Application {
    private Chess chess = new Chess();
    private Group tiles = new Group();
    private Group pieces = new Group();
    private Board board = chess.getBoard();
    private boolean pieceClicked = false;
    private int clickedY = -1;
    private int clickedX = -1;
    private LeaderboardDao leaderboardDao;
    private ChessDao chessDao;
    private PiecesDao piecesDao;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception{
        leaderboardDao = new LeaderboardDao();
        leaderboardDao.getPlayers();
        chessDao = new ChessDao();
        piecesDao = new PiecesDao();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Shakki");
        
        //menu
        
        Pane placementMenu = new Pane();
        placementMenu.setPrefSize(500, 500);
        
        HBox players = new HBox();
        
        VBox playerOneVBox  = new VBox();
        playerOneVBox.getChildren().add(new Label("Choose your name..."));
        TextField chooseNameOne = new TextField("PlayerOne");
        chooseNameOne.setPrefColumnCount(10);
        playerOneVBox.getChildren().add(chooseNameOne);
        playerOneVBox.getChildren().add(new Label("WHITE" + " PIECES"));
        
        VBox playerTwoVBox  = new VBox();
        playerTwoVBox.getChildren().add(new Label("Choose your name..."));
        TextField chooseNameTwo = new TextField("PlayerTwo");
        chooseNameTwo.setPrefColumnCount(10);
        playerTwoVBox.getChildren().add(chooseNameTwo);
        playerTwoVBox.getChildren().add(new Label("BLACK" + " PIECES"));
        
        players.getChildren().add(playerOneVBox);
        players.getChildren().add(playerTwoVBox);
       
        Label title = new Label("SHAKKI");
        
        Button startGame = new Button("Let's play!");
        
        Button toLeaderboard = new Button("Leaderboards");
        
        Button continueGame = new Button("Continue");
        
        placementMenu.getChildren().add(title);
        title.relocate(100, 10);
        placementMenu.getChildren().add(players);
        players.relocate(100, 100);
        placementMenu.getChildren().add(startGame);
        startGame.relocate(100, 400);
        placementMenu.getChildren().add(toLeaderboard);
        toLeaderboard.relocate(400, 400);
        placementMenu.getChildren().add(continueGame);
        continueGame.relocate(300,400);
        
        Scene sceneMenu = new Scene(placementMenu);
        
        //game
        
        BorderPane placementGame = new BorderPane();
        placementGame.setPrefSize(500, 500);
        
        //adding names and the board
        
        Pane tilesAndPieces = new Pane();
        tilesAndPieces.getChildren().add(tiles);
        tilesAndPieces.getChildren().add(pieces);
        Label playerOneName = new Label("Player One");
        Label playerTwoName = new Label("Player Two");
        Label playerTurn = new Label("Whites turn");
        Button forfeit = new Button("Forfeit");
        Button quit = new Button("Quit");
        VBox gameButtons = new VBox();
        gameButtons.getChildren().add(forfeit);
        gameButtons.getChildren().add(quit);
        gameButtons.getChildren().add(playerTurn);
        
        //adding the tiles to the board
        drawBoard();   
        
        placementGame.setCenter(tilesAndPieces);
        placementGame.setTop(playerOneName);
        placementGame.setBottom(playerTwoName);
        placementGame.setRight(gameButtons);
        
        Scene sceneGame = new Scene(placementGame);
        
        
        //Endscreen
        
        BorderPane placementEnd = new BorderPane();
        placementEnd.setPrefSize(500, 500);
        Label winner = new Label();
        Button continueToMenu = new Button("Back to menu");
        placementEnd.setCenter(winner);
        placementEnd.setBottom(continueToMenu);
        Scene sceneEnd = new Scene(placementEnd);
        
        // Leaderboards
        
        BorderPane placementLeader = new BorderPane();
        placementLeader.setPrefSize(500, 500);
        Label leaderboard = new Label("LEADERBOARD");
        VBox leaderboardVbox = new VBox();
        Button leaderboardToMenu = new Button("Back to menu");
        getLeaderboard(leaderboardVbox);
        placementLeader.setTop(leaderboard);
        placementLeader.setCenter(leaderboardVbox);
        placementLeader.setBottom(leaderboardToMenu);
        Scene sceneLeader = new Scene(placementLeader);
        
        //buttons for changing scene
        
        startGame.setOnAction((event) -> {
            chess = new Chess();
            board = chess.getBoard();
            chess.setPlayers(chooseNameOne.getText(), chooseNameTwo.getText());
            playerOneName.setText(chooseNameTwo.getText());
            playerTwoName.setText(chooseNameOne.getText());
            drawBoard();
            try {
                leaderboardDao.addPlayer(new Player(chooseNameOne.getText(), 0, 0));
            } catch (SQLException ex) {
                Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                leaderboardDao.addPlayer(new Player(chooseNameTwo.getText(), 0, 0));
            } catch (SQLException ex) {
                Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.setScene(sceneGame);
        });
        
        tilesAndPieces.onMouseClickedProperty().set(e -> {
            int x = (int) e.getX();
            int y = (int) e.getY();
            x = ((x - (x % 50)) / 50);
            y = ((y - (y % 50)) / 50);
            movePiece(x, y);
            if (chess.isWhitesTurn()) {
                playerTurn.setText("Whites turn");
            } else {
                playerTurn.setText("Blacks turn");
            }
            if (chess.getCheckmate() || chess.getStalemate()) {
                try {
                    chessDao.removeGame(chess);
                } catch (SQLException ex) {
                    Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (chess.getStalemate()) {
                    winner.setText("It's a draw");
                } else if (chess.isWhitesTurn()) {
                    winner.setText("Black wins");
                } else {
                    winner.setText("White wins");
                }
                stage.setScene(sceneEnd); 
            }
            drawBoard();
        });
        
        forfeit.setOnAction((event) ->  {
            try {
                chessDao.removeGame(chess);
            } catch (SQLException ex) {
                Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (chess.isWhitesTurn()) {
                winner.setText("Black wins");
            } else {
                winner.setText("White wins");
            }
            stage.setScene(sceneEnd);
        });
        
        continueToMenu.setOnAction((event) -> {
            try {
                addGameToLeaderboard();
                getLeaderboard(leaderboardVbox);
            } catch (SQLException ex) {
                Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            chess = new Chess();
            board = chess.getBoard();
            drawBoard();
            stage.setScene(sceneMenu);
        });
        
        leaderboardToMenu.setOnAction((event) -> {
            stage.setScene(sceneMenu);
        });
        
        toLeaderboard.setOnAction((event) -> {
            stage.setScene(sceneLeader);
        });
        
        continueGame.setOnAction((event) -> {
            try {
                chess = chessDao.getChess();
            } catch (SQLException ex) {
                Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            board = chess.getBoard();
            board.fixPieceTypesAfterContinue();
            if(!board.boardIsEmpty()) {
                drawBoard();
                stage.setScene(sceneGame);
            }
        });
        
        quit.setOnAction((event) -> {
            try {
                chessDao.addGame(chess);
                for(int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {
                        piecesDao.addPiece(board.getTile(i, j), chess.getId());
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ShakkiUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.setScene(sceneMenu);
        });
        
        //set initial scene
        
        stage.setScene(sceneMenu);
        stage.show();
        
        
    }
    
    private void movePiece(int x, int y) {
        if (x > 7 || y > 7) {
            pieceClicked = false;
        } else if (pieceClicked) {
            chess.turn(clickedX, clickedY, x, y);
            pieceClicked = false;
        } else {
            pieceClicked = true;
            clickedX = x;
            clickedY = y;
        }
    }

    private void drawBoard() {
        tiles.getChildren().clear();
        pieces.getChildren().clear();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile boardTile = board.getTile(x, y);
                Piece piece = boardTile.getPiece();
                TileUI tile = new TileUI(boardTile.isWhite(), x, y);
                tiles.getChildren().add(tile);
                if (piece != null) { 
                    PieceUI pieceUI;
                    try {
                        pieceUI = new PieceUI(x, y, piece.getType(), piece.isWhite());
                    } catch (FileNotFoundException ex) {
                        pieceUI = null;
                    }
                    pieces.getChildren().add(pieceUI);
                }
            }
        } 
    }
    
    private void getLeaderboard(VBox vbox) throws SQLException {
        vbox.getChildren().clear();
        HBox explanation = new HBox();
        explanation.getChildren().add(new Label("Player"));
        explanation.getChildren().add(new Label("Wins"));
        explanation.getChildren().add(new Label("Losses"));
        vbox.getChildren().add(explanation);
        for(int i = 0; i < 10; i++) {
            HBox playerStats = new HBox();
            if(leaderboardDao.getPlayers().size() > i) {
                Player player = leaderboardDao.getPlayers().get(i);
                playerStats.getChildren().add(new Label(player.getName()));
                playerStats.getChildren().add(new Label(String.valueOf(player.getWins())));
                playerStats.getChildren().add(new Label(String.valueOf(player.getLosses())));
                vbox.getChildren().add(playerStats);
            }
        }
    }
    
    private void addGameToLeaderboard() throws SQLException {
        if(chess.getStalemate()) {
            return;
        }
        if(!chess.isWhitesTurn()) {
            leaderboardDao.updatePlayerWin(chess.getPlayers().get(0).getName());
            leaderboardDao.updatePlayerLose(chess.getPlayers().get(1).getName());
        } else {
            leaderboardDao.updatePlayerWin(chess.getPlayers().get(1).getName());
            leaderboardDao.updatePlayerLose(chess.getPlayers().get(0).getName());
        }
    }
    
}
