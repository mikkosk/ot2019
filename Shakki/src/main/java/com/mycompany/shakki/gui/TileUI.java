/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * graphical implementations of a tile on a chess board
 * @author Mikko
 */
public class TileUI extends Rectangle {
    /**
     * creates a new tile to the gui
     * @param colorWhite whether or not the tile is white
     * @param x location of the tile (x-coordinate)
     * @param y locations of the tile (y-coordinate)
     */
    public TileUI(boolean colorWhite, int x, int y) {
        setWidth(50);
        setHeight(50);
        
        relocate(x * 50, y * 50);
        
        setFill(colorWhite ? Color.WHITE : Color.BLACK); 
    }
}
