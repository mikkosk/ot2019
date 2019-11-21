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
public class Queen extends Piece{
    
    public Queen(String type, boolean white) {
        super(type, white);
    }
    
    @Override
    public boolean validMove(Board board, int oldX, int oldY, int newX, int newY) {
        if(!((oldX != newX) && (oldY != newY)) && !((oldX == newX) && (oldY == newY))) {
            if(checkTilesBeforeStraight(board, oldX, oldY, newX, newY)) {
                if(board.tileOnBoardIsEmpty(newX, newY) || board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) {
                    return true;
                };
            }
        }
        
        if(Math.abs(newY - oldY) == Math.abs(newX - oldX)) {
            if(checkTilesBeforeDiagonal(board, oldX, oldY, newX, newY)) {
                if(board.tileOnBoardIsEmpty(newX, newY)) return true;
                if(board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) return true;
            }
        }
        return false;
    }
    
    private boolean checkTilesBeforeStraight(Board board, int oldX, int oldY, int newX, int newY) {
        boolean yAxis = oldX == newX;
        boolean grows = yAxis ? oldY < newY : oldX < newX;
        int start = yAxis ? oldY : oldX;
        int end = yAxis ? newY : newX;
        int iHigh = Math.abs(end-start);
        for(int i = 1; i < iHigh; i++) {
            if(yAxis && grows) {
                if(!board.tileOnBoardIsEmpty(oldX, oldY + i)) return false;
            } else if(yAxis && !grows) {
                if(!board.tileOnBoardIsEmpty(oldX, oldY - i)) return false;
            } else if(!yAxis && grows) {
                if(!board.tileOnBoardIsEmpty(oldX + i, oldY)) return false;
            } else {
                if(!board.tileOnBoardIsEmpty(oldX - i, oldY)) return false;
            }
        }
        return true;
    }
    
        private boolean checkTilesBeforeDiagonal(Board board, int oldX, int oldY, int newX, int newY) {
        boolean upY = newY < oldY;
        boolean rightX = oldX < newX;
        
        for(int i = 1; i <= Math.abs(newX - oldX) - 1; i++) {
            if(upY && rightX) {
                if(!board.tileOnBoardIsEmpty(oldX+i, oldY-i)) return false;
            } else if(upY && !rightX) {
                if(!board.tileOnBoardIsEmpty(oldX-1, oldY-i)) return false;
            } else if(!upY && rightX) {
                if(!board.tileOnBoardIsEmpty(oldX+i, oldY+i)) return false;
            } else{
                if(!board.tileOnBoardIsEmpty(oldX-i, oldY+i)) return false;
            }
        }
        return true;
    }
}
