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
    
    public Chess() {
        board = new Board();
    }
    
    private boolean checkCheck() {
        return false;
    }
    
    public boolean movePiece(int oldX, int oldY, int newX, int newY) {
        if(oldX == newX && oldY == newY) return false;
        
        boolean moveMade = false;
        Piece piece = board.getTile(oldX, oldY).getPiece();
        if(piece != null && piece.validMove(board, oldX, oldY, newX, newY)) {
            board.getTile(newX, newY).setPiece(piece);
            board.getTile(oldX, oldY).setPiece(null);
            moveMade = true;
            piece.setHasMoved(true);
        }
        return moveMade;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
