/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

import java.util.Objects;

/**
 *
 * @author Mikko
 */
public class Piece {
    private String type;
    private boolean white;
    private boolean hasMoved;

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    
    public Piece(String pieceType, boolean isWhite) {
        type = pieceType;
        white = isWhite;
        hasMoved = false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + (this.white ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Piece other = (Piece) obj;
        if (this.white != other.white) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }
    
    public boolean validMove(Board board, int oldX, int oldY, int newX, int newY) {
        return false;
    }
    
    public boolean checkTilesBeforeStraight(Board board, int oldX, int oldY, int newX, int newY) {
        boolean yAxis = oldX == newX;
        boolean grows = yAxis ? oldY < newY : oldX < newX;
        int start = yAxis ? oldY : oldX;
        int end = yAxis ? newY : newX;
        int iHigh = Math.abs(end - start);
        for (int i = 1; i < iHigh; i++) {
            if (!checkPossibleDirectionsStraight(board, i, oldX, oldY, yAxis, grows)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean checkTilesBeforeDiagonal(Board board, int oldX, int oldY, int newX, int newY) {
        boolean growsY = oldY < newY;
        boolean rightX = oldX < newX;

        for (int i = 1; i <= Math.abs(newX - oldX) - 1; i++) {
            if (!checkPossibleDirectionsDiagonal(board, i, oldX, oldY, growsY, rightX)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean checkPossibleDirectionsStraight(Board board, int i, int x, int y, boolean axisOne, boolean axisTwo) {
        if (axisOne && axisTwo) {
            if (!board.tileOnBoardIsEmpty(x, y + i)) {
                return false;
            }
        } else if (axisOne && !axisTwo) {
            if (!board.tileOnBoardIsEmpty(x, y - i)) {
                return false;
            }
        } else if (!axisOne && axisTwo) {
            if (!board.tileOnBoardIsEmpty(x + i, y)) {
                return false;
            }
        } else {
            if (!board.tileOnBoardIsEmpty(x - i, y)) {
                return false;
            }
        }
        return true;        
    }
    
    private boolean checkPossibleDirectionsDiagonal(Board board, int i, int x, int y, boolean axisOne, boolean axisTwo) {
        if (axisOne && axisTwo) {
            if (!board.tileOnBoardIsEmpty(x + i, y + i)) {
                return false;
            }
        } else if (axisOne && !axisTwo) {
            if (!board.tileOnBoardIsEmpty(x - i, y + i)) {
                return false;
            }
        } else if (!axisOne && axisTwo) {
            if (!board.tileOnBoardIsEmpty(x + i, y - i)) {
                return false;
            }
        } else {
            if (!board.tileOnBoardIsEmpty(x - i, y - i)) {
                return false;
            }
        }
        return true;        
    }
}
