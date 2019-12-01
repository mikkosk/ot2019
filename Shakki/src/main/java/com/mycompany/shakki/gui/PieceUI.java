/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki.gui;

import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mikko
 */
public class PieceUI extends Pane {
    
    public PieceUI(int x, int y, String type, boolean white) throws FileNotFoundException {
        String resource = "";
        String color = white == true ? "white" : "black";
        resource += color + type + ".png";
        Image image = new Image("/images/" + resource);
        ImageView piece = new ImageView(image);

        piece.setTranslateX(x * 50);
        piece.setTranslateY(y * 50);
        
        getChildren().add(piece);
    }
}
