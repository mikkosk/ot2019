/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.shakki;

import com.mycompany.shakki.domain.Knight;
import com.mycompany.shakki.domain.Chess;
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
public class KnightTest {
    
    private Knight knight;
    private Chess chess;
    
    public KnightTest() {
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
        knight = new Knight("Knight", true);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test 
    public void canMoveThroughPieces() {
        assertTrue(knight.validMove(chess.getBoard(), 1, 7, 0, 5));
    }
    
    @Test 
    public void canMoveInLShape() {
        assertTrue(knight.validMove(chess.getBoard(), 2, 5, 4, 4));
    }
    
    @Test
    public void cantMoveStraight() {
        chess.getBoard().getTile(2, 6).setPiece(null);
        assertFalse(knight.validMove(chess.getBoard(), 2, 3, 2, 5));
    }
    
    @Test
    public void canEatOtherColorPiece() {
        assertTrue(knight.validMove(chess.getBoard(), 2, 2, 0, 1));
    }
    
    @Test
    public void cantEatSameColorPiece() {
        assertFalse(knight.validMove(chess.getBoard(), 1, 7, 3, 6));
    }
}
