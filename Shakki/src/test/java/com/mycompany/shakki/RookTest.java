/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Chess;
import com.mycompany.shakki.domain.Rook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mikko
 */
public class RookTest {
    
    private Rook rook;
    private Chess chess;
    
    public RookTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        chess = new Chess();
        rook = new Rook("Rook", true);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void cantMoveThroughPieces() {
        assertFalse(rook.validMove(chess.getBoard(), 0, 7, 0, 5));
    }
    
    @Test 
    public void cantMoveDiagonal() {
        assertFalse(rook.validMove(chess.getBoard(), 0, 5, 2, 3));
    }
    
    @Test
    public void canMoveStraight() {
        assertTrue(rook.validMove(chess.getBoard(), 2, 3, 2, 5));
    }
    
    @Test
    public void canEatOtherColorPiece() {
        assertTrue(rook.validMove(chess.getBoard(), 2, 2, 2, 1));
    }
    
    @Test
    public void cantEatSameColorPiece() {
        assertFalse(rook.validMove(chess.getBoard(), 2, 2, 2, 6));
    }
}
