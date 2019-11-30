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
    
    public boolean tileOnBoardIsEmpty(int x, int y) {
        return tiles[y][x].empty();
    }
    
    private void initializeBoard() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                tiles[y][x] = new Tile(x, y, (y + x) % 2 == 0);
                pieceToTile(x, y);
            }
        }
    }
    
    private void pieceToTile(int x, int y) {
        if (y < 6 && y > 1) {
            return;
        }
        boolean white = false;
        Piece piece = new Piece("", false);    
        if (y > 5) {
            white = true;
        }    
        if (x == 0 || x == 7) {
            piece = new Rook("Rook", white);
        }
        if (x == 1 || x == 6) {
            piece = new Knight("Knight", white);
        }   
        if (x == 2 || x == 5) {
            piece = new Bishop("Bishop", white);
        }     
        if (x == 3) {
            piece = new Queen("Queen", white);;
        }
        if (x == 4) {
            piece = new King("King", white);;
        }
        if (y == 1 || y == 6) {
            piece = new Pawn("Pawn", white);;
        }
        tiles[y][x].setPiece(piece);
    }
    
    public void clearBoard() {
        tiles = new Tile[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                tiles[y][x] = new Tile(x, y, (y + x) % 2 == 0);
            }
        }
    }
    
    public void fixPieceTypesAfterContinue() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = tiles[y][x].getPiece();
                if (piece != null) {
                    Piece setPiece = returnRightClassPiece(piece.getType());
                    setPiece.setHasMoved(piece.isHasMoved());
                    setPiece.setWhite(piece.isWhite());
                    tiles[y][x].setPiece(setPiece);
                }
            }
        }
    }
    
    private Piece returnRightClassPiece(String type) {
        if (type.equals("Pawn")) {
            return new Pawn(type, true);
        } else if (type.equals("Rook")) {
            return new Rook(type, true);
        } else if (type.equals("Knight")) {
            return new Knight(type, true);
        } else if (type.equals("Bishop")) {
            return new Bishop(type, true);
        } else if (type.equals("Queen")) {
            return new Queen(type, true);
        } else {
            return new King(type, true);
        }
    }
    
    public boolean boardIsEmpty() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (!getTile(i, j).empty()) {
                    return false;
                }
            }
        }
        return true;
    }
}
