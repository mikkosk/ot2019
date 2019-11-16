/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

/**
 *
 * @author Mikko
 */
public class Board {
    private Tile[][] tiles;
    
    public Board() {
        tiles = new Tile[8][8];
        initializeBoard();
    }
    
    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }
    
    public void setTile(int x, int y, Tile tile) {
        tiles[y][x] = tile;
    }
    
    private void initializeBoard() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                tiles[y][x] = new Tile(x, y, (y+x)%2 == 0);
                pieceToTileStandard(x,y);
            }
        }
    }
    
    private void pieceToTileStandard(int x, int y) {
        if(y < 6 && y > 1) {
            return;
        }

        boolean white = false;
        String type = "";
        
        if(y > 5) {
            white = true;
        }
        
        if(x == 0 || x == 7) {
            type = "Rook";
        }
        if(x == 1 || x == 6) {
            type = "Knight";
        }
        
        if(x == 2 || x == 5) {
            type = "Bishop";
        }
        
        if(x == 3) {
            type = "Queen";
        }
        
        if(x == 4) {
            type = "King";
        }
        
        if(y == 1 || y == 6) {
            type = "Pawn";
        }
        
        tiles[y][x].setPiece(new Piece(type, white));
    }
}
