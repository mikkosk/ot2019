/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mikko
 */
public class TileUI extends Rectangle {
    public TileUI(boolean colorWhite, int x, int y) {
        setWidth(50);
        setHeight(50);
        
        relocate(x * 50, y * 50);
        
        setFill(colorWhite ? Color.WHITE : Color.BLACK); 
    }
}
