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
public class Chess {
    private Board board;
    private boolean whitesTurn;
    private boolean checkmate;
    private boolean stalemate;
    
    public Chess() {
        board = new Board();
        whitesTurn = true;
        checkmate = false;
        stalemate = false;
    }
    
    private void checkmate(Board copy, boolean whiteTurn) {
        if (checkCheck(copy, !whiteTurn) && mate(copy, whiteTurn)) {
            checkmate = true;
        }
    }
    
    private void stalemate(Board copy, boolean whiteTurn) {
        if (!checkCheck(copy, !whiteTurn) && mate(copy, whiteTurn)) {
            stalemate = true;
        }
    }
    
    private boolean mate(Board copy, boolean whiteTurn) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = copy.getTile(i, j).getPiece();
                if (piece != null && piece.isWhite() != whiteTurn) {
                    if (checkSolvingMate(copy, piece, i, j, whiteTurn)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
        
    private boolean checkCheck(Board copy, boolean whiteTurn) {
        Tile tile = findKing(whiteTurn);
        int x = tile.getX();
        int y = tile.getY();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = copy.getTile(i, j).getPiece();
                if (piece != null && piece.isWhite() != whiteTurn) {
                    if (piece.validMove(copy, i, j, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean checkSolvingMate(Board copy, Piece piece, int i, int j, boolean whiteTurn) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (piece.validMove(copy, i, j, x, y)) {
                    Piece eaten = copy.getTile(x, y).getPiece();
                    Tile startTile = copy.getTile(i, j);
                    Tile endTile = copy.getTile(x, y);
                    movePiece(startTile, endTile, null, piece);
                    if (!checkCheck(copy, !whiteTurn)) {
                        movePiece(startTile, endTile, piece, eaten);
                        return true;
                    }
                    movePiece(startTile, endTile, piece, eaten);
                }
            }
        }
        return false;
    }
    
    public boolean turn(int oldX, int oldY, int newX, int newY) {  
        boolean moveMade = false;
        Piece piece = board.getTile(oldX, oldY).getPiece();
        if (piece != null && piece.isWhite() == whitesTurn && piece.validMove(board, oldX, oldY, newX, newY) && !sameCoordinates(oldX, oldY, newX, newY)) {
            Piece eaten = board.getTile(newX, newY).getPiece();
            Tile startTile = board.getTile(oldX, oldY);
            Tile endTile = board.getTile(newX, newY); 
            movePiece(startTile, endTile, null, piece);
            if (!checkCheck(board, whitesTurn)) {
                moveMade = true;
                piece.setHasMoved(true);
                stalemate(board, whitesTurn);
                checkmate(board, whitesTurn);
                whitesTurn = !whitesTurn;
            } else {
                movePiece(startTile, endTile, piece, eaten);
            }           
        }
        return moveMade;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public boolean isStalemate() {
        return stalemate;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    
    public boolean getCheckmate() {
        return this.checkmate;
    }
    
    private Tile findKing(boolean white) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getTile(i, j).getPiece();
                if (piece != null && piece.isWhite() == white && piece.getType().equals("King")) {
                    return board.getTile(i, j);
                }
            }
        }
        return null;
    }
    
    private void movePiece(Tile startTile, Tile endTile, Piece pieceStartTile, Piece pieceEndTile) {
        endTile.setPiece(pieceEndTile);
        startTile.setPiece(pieceStartTile);
    }
    
    private boolean sameCoordinates(int oldX, int oldY, int newX, int newY) {
        if (oldX == newX && oldY == newY) {
            return true;
        }
        return false;
    }
}
