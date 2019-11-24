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
public class Rook extends Piece {
    
    public Rook(String type, boolean white) {
        super(type, white);
    }
    
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
