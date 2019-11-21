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
public class Pawn extends Piece {
    
    public Pawn(String type, boolean white) {
        super(type, white);
    }
    
    @Override
    public boolean validMove(Board board, int oldX, int oldY, int newX, int newY) {
        int negative = -1;
        if(!this.isWhite()) negative = 1;
        boolean goodMove = false;
        if(oldX == newX && oldY == newY) {
            return goodMove;
        }
        
        if(newY - oldY == negative * 1) {
            if(newX == oldX && board.tileOnBoardIsEmpty(newX, newY)) {
                goodMove = true;
            } else if (Math.abs(newX - oldX) == 1 && board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) {
                goodMove = true;
            }
        } else if(newY - oldY == negative * 2) {
            if(!this.isHasMoved()) {
                if(newX == oldX && board.tileOnBoardIsEmpty(newX, newY) && board.tileOnBoardIsEmpty(newX, newY -(negative*1))) {
                goodMove = true;
                }
            }
        }
        return goodMove;
    }
}
