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
        if (Math.abs(newY - oldY) == Math.abs(newX - oldX)) {
            if (checkTilesBeforeDiagonal(board, oldX, oldY, newX, newY)) {
                if (board.tileOnBoardIsEmpty(newX, newY)) {
                    return true;
                }
                if (board.getTile(newX, newY).getPiece().isWhite() != this.isWhite()) {
                    return true;
                }
            }
        }
        return false;
    }
}
