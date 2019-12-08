/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

/**
 * Represents pawns in a chess game
 * @author Mikko
 */
public class Pawn extends Piece {
    
    /**
    * creates a new Bishop piece, which can move straight or diagonally depending on situation
    * 
    * @param type the type of the piece
    * @param white whether or not the piece is white
    */
    public Pawn(String type, boolean white) {
        super(type, white);
    }
    
    /**
    * Tests out if the move from first tile to the other is legal.
    * Returns true, if move is valid and false if it is illegal.
    *
    * @param board The board that the game is being played on
    * @param oldX The starting x-coordinate of the piece
    * @param oldY The starting y-coordinate of the piece
    * @param newX The desired ending x-coordinate of the piece
    * @param newY The desired ending y-coordinate of the piece
    *
    * @return returns whether or not the move is legal
    */
    @Override
    public boolean validMove(Board board, int oldX, int oldY, int newX, int newY) {
        int negative = -1;
        if (!this.isWhite()) {
            negative = 1;
        }
        boolean goodMove = false;
        if (newY - oldY == negative * 1) {
            if (newX == oldX && board.tileOnBoardIsEmpty(newX, newY)) {
                goodMove = true;
            } else if (Math.abs(newX - oldX) == 1 && !board.getTile(newX, newY).empty() && board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) {
                goodMove = true;
            }
        } else if (newY - oldY == negative * 2 && !this.isHasMoved()) {
            if (newX == oldX && board.tileOnBoardIsEmpty(newX, newY) && board.tileOnBoardIsEmpty(newX, newY - (negative * 1))) {
                goodMove = true;
            }
        }
        return goodMove;
    }
}
