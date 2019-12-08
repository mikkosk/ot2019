/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.domain;

/**
 * Represents a tile on a chess board
 * @author Mikko
 */
public class Tile {
    private Piece piece;
    private int x;
    private int y;
    private boolean white;
    
    /**
     * creates a tile with color and location
     * @param x x-coordinate of the tile
     * @param y y-coordinate of the tile
     * @param white whether or not the tile is white
     */
    public Tile(int x, int y, boolean white) {
        this.piece = null;
        this.x = x;
        this.y = y;
        this.white = white;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }
    
    /**
     * tells if the tile has a piece or nott
     * @return true, if the tile is empty, else false
     */
    public boolean empty() {
        return piece == null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
