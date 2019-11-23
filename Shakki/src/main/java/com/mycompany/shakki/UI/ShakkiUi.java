/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.UI;

/**
 *
 * @author Mikko
 */

import com.mycompany.shakki.domain.Board;
import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Piece;
import com.mycompany.shakki.domain.Tile;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShakkiUi extends Application {
    private Chess chess = new Chess();
    private Group tiles = new Group();
    private Group pieces = new Group();
    private Board board = chess.getBoard();
    private boolean pieceClicked = false;
    private int clickedY = -1;
    private int clickedX = -1;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Shakki");
        
        //menu
        
        Pane placementMenu = new Pane();
        placementMenu.setPrefSize(500, 500);
        
        HBox players = new HBox();
        players.getChildren().add(player("Pelaaja1", "WHITE"));
        players.getChildren().add(player("Pelaaja2", "BLACK"));
       
        Label title = new Label("SHAKKI");
        
        Button startGame = new Button("Let's play!");
        
        placementMenu.getChildren().add(title);
        title.relocate(100, 10);
        placementMenu.getChildren().add(players);
        players.relocate(100, 100);
        placementMenu.getChildren().add(startGame);
        startGame.relocate(100, 400);
        
        Scene sceneMenu = new Scene(placementMenu);
        
        //game
        
        BorderPane placementGame = new BorderPane();
        placementGame.setPrefSize(500, 500);
        
        //adding names and the board
        
        Pane tilesAndPieces = new Pane();
        tilesAndPieces.getChildren().add(tiles);
        tilesAndPieces.getChildren().add(pieces);
        
        //adding the tiles to the board
        drawBoard();   
        
        placementGame.setCenter(tilesAndPieces);
        
        Scene sceneGame = new Scene(placementGame);
        
        //buttons for changing scene
        
        startGame.setOnAction((event) -> {
            stage.setScene(sceneGame);
        });
        
        tilesAndPieces.onMouseClickedProperty().set(e -> {
           int x = (int)e.getX();
           int y = (int)e.getY();
           x = ((x-(x%50))/50);
           y = ((y-(y%50))/50);
           movePiece(x,y);
           if(chess.getCheckmate()) {
               stage.setScene(sceneMenu);
           }
           drawBoard();
        });
        
        //set initial scene
        
        stage.setScene(sceneMenu);
        stage.show();
        
        
    }
    
    private VBox player(String player, String pieces) {
        VBox playerVBox  = new VBox();
        playerVBox.getChildren().add(new Label("Choose your name..."));
        TextField chooseName = new TextField(player);
        chooseName.setPrefColumnCount(10);
        playerVBox.getChildren().add(chooseName);
        playerVBox.getChildren().add(new Label(pieces + " PIECES"));
        return playerVBox;
    }
    
    private void movePiece(int x, int y) {
        if(x > 7 || y > 7) {
            pieceClicked = false;
        }
        else if(pieceClicked) {
            chess.movePiece(clickedX, clickedY, x, y);
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
        for(int y=0; y<8; y++) {
            for(int x=0; x<8; x++) {
                Tile boardTile = board.getTile(x, y);
                Piece piece = boardTile.getPiece();
                TileUI tile = new TileUI(boardTile.isWhite(), x, y);
                tiles.getChildren().add(tile);
                if(piece != null) { 
                    PieceUI pieceUI;
                    try {
                        pieceUI = new PieceUI(x,y, piece.getType(), piece.isWhite());
                    } catch (FileNotFoundException ex) {
                        pieceUI = null;
                    }
                    pieces.getChildren().add(pieceUI);
                }
            }
        } 
    }
    
}
