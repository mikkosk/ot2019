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
public class King extends Piece {
    
    public King(String type, boolean white) {
        super(type, white);
    }
    
    @Override
    public boolean validMove(Board board, int oldX, int oldY, int newX, int newY) {
        if ((Math.abs(newY - oldY) <= 1) && (Math.abs(newX - oldX) <= 1)) {
            if (board.tileOnBoardIsEmpty(newX, newY)) {
                return true;
            }
            if (board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) {
                return true;
            }
        }
        return false;
    }
}
