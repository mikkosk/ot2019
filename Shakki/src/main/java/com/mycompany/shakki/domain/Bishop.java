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
public class Bishop extends Piece {
    
    public Bishop(String type, boolean white) {
        super(type, white);
    }
    
    @Override
    public boolean validMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(Math.abs(newY - oldY) == Math.abs(newX - oldX)) {
            if(checkTilesBefore(board, oldX, oldY, newX, newY)) {
                if(board.tileOnBoardIsEmpty(newX, newY)) return true;
                if(board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) return true;
            }
        }
        return false;
    }
    
    private boolean checkTilesBefore(Board board, int oldX, int oldY, int newX, int newY) {
        boolean upY = newY < oldY;
        boolean rightX = oldX < newX;
        
        for(int i = 1; i <= Math.abs(newX - oldX) - 1; i++) {
            if(upY && rightX) {
                if(!board.tileOnBoardIsEmpty(oldX+i, oldY-i)) return false;
            } else if(upY && !rightX) {
                if(!board.tileOnBoardIsEmpty(oldX-i, oldY-i)) return false;
            } else if(!upY && rightX) {
                if(!board.tileOnBoardIsEmpty(oldX+i, oldY+i)) return false;
            } else{
                if(!board.tileOnBoardIsEmpty(oldX-i, oldY+i)) return false;
            }
        }
        System.out.println("true tiles");
        return true;
    }
}
