/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

/**
 * Represents rooks in a chess game
 * @author Mikko
 */
public class Rook extends Piece {
    
        /**
    * creates a new Rook piece, which can move straight
    * 
    * @param type the type of the piece
    * @param white whether or not the piece is white
    */
    public Rook(String type, boolean white) {
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
        if (!((oldX != newX) && (oldY != newY)) && !((oldX == newX) && (oldY == newY))) {
            if (checkTilesBeforeStraight(board, oldX, oldY, newX, newY)) {
                if (board.tileOnBoardIsEmpty(newX, newY) || board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) {
                    return true;
                }
            }
        }
        return false;
    }   
}
